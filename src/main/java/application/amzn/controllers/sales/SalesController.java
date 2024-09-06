package application.amzn.controllers.sales;

import application.amzn.entities.Sale;
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
        if (sell == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sell);
    }

    @PostMapping
    public ResponseEntity<Sale> sell(@RequestBody @Valid PostSaleDTO dto) {
        if (dto.items().isEmpty()) return ResponseEntity.badRequest().build();

        try {
            return service.sell(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Long id, @RequestBody JsonPatch dto) throws JsonPatchException, JsonProcessingException {
        var sale = service.findById(id);
        if (sale == null) return ResponseEntity.notFound().build();
        service.patch(sale, dto);
        return ResponseEntity.ok().build();
    }
}
