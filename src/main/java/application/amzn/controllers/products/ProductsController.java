package application.amzn.controllers.products;

import application.amzn.entities.Product;
import application.amzn.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductsController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public ResponseEntity<?> getProducts() {
        var products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        var product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody @Valid PostProductDTO dto) {
        Product product = new Product(dto.name(), dto.description(), dto.price(), dto.quantity());
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }
}
