package application.amzn.controllers.sales;

import application.amzn.entities.Sale;
import application.amzn.exceptions.SaleNotFoundException;
import application.amzn.exceptions.primitives.BadRequestException;
import application.amzn.exceptions.primitives.InternalException;
import application.amzn.services.SalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sells")
public class SalesController {
    private final SalesService service;

    @Autowired
    public SalesController(SalesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<Sale>> getSales() {
        var sells = service.findAll();
        return ResponseEntity.ok(sells);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSale(@PathVariable Long id) {
        var sell = service.findById(id);
        if (sell == null) throw new SaleNotFoundException(id);
        return ResponseEntity.ok(sell);
    }

    @PostMapping
    public ResponseEntity<Sale> sell(@RequestBody @Valid PostSaleDTO dto) {
        if (dto.items().isEmpty()) throw new BadRequestException("A venda deve ter ao menos um item.");

        try {
            return service.sell(dto);
        } catch (RuntimeException e) {
            throw new InternalException("Não foi possível concluir a venda.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Long id, @RequestBody JsonPatch dto) throws JsonPatchException, JsonProcessingException {
        var sale = service.findById(id);
        if (sale == null) throw new SaleNotFoundException(id);
        service.patch(sale, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Long id) {
        var sale = service.findById(id);
        if (sale == null) throw new SaleNotFoundException(id);
        service.delete(sale);
        return ResponseEntity.ok().build();
    }
}
