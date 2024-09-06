package application.amzn.controllers.sells;

import application.amzn.entities.Item;
import application.amzn.entities.Product;
import application.amzn.entities.Sell;
import application.amzn.services.SellsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sells")
public class SellsController {
    private final SellsService service;

    @Autowired
    public SellsController(SellsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<Sell>> getSales() {
        var sells = service.findAll();
        return ResponseEntity.ok(sells);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sell> getSale(@PathVariable Long id) {
        var sell = service.findById(id);
        if (sell == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sell);
    }

    @PostMapping
    public ResponseEntity<Sell> sell(@RequestBody @Valid PostSaleDTO dto) {
        try {
            return service.sell(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
