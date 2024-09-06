package application.amzn.controllers.sells;

import application.amzn.entities.Sell;
import application.amzn.services.SellsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sells")
public class SellsController {
    private final SellsService service;

    @Autowired
    public SellsController(SellsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<Sell>> getProducts() {
        var sells = service.findAll();
        return ResponseEntity.ok(sells);
    }
}
