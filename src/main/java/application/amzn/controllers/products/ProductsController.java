package application.amzn.controllers.products;

import application.amzn.entities.Product;
import application.amzn.exceptions.ProductNotFoundException;
import application.amzn.services.ProductsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductsController {
    private final ProductsService service;

    @Autowired
    public ProductsController(ProductsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getProducts() {
        var products = service.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        var product = service.findById(id);
        if (product == null) throw new ProductNotFoundException(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid PostProductDTO dto) {
        Product product = new Product(dto.name(), dto.description(), dto.price(), dto.quantity());
        service.save(product);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody JsonPatch dto) throws JsonPatchException, JsonProcessingException {
        var product = service.findById(id);
        if (product == null) throw new ProductNotFoundException(id);
        service.patch(product, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        return service.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> unarchiveProduct(@PathVariable("id") Long id) {
        return service.unarchive(id);
    }
}
