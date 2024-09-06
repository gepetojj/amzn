package application.amzn.controllers.sales;

import application.amzn.entities.Sale;
import application.amzn.services.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sells")
public class SalesController {
    private final SaleService service;

    @Autowired
    public SalesController(SaleService service) {
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
}
