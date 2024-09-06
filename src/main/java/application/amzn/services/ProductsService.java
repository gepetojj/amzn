package application.amzn.services;

import application.amzn.entities.Product;
import application.amzn.repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
    private final ProductRepository repository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductsService(ProductRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public Iterable<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Product product) {
        repository.save(product);
    }

    public void patch(Product product, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(product, JsonNode.class));
        save(objectMapper.treeToValue(patched, Product.class));
    }

    public ResponseEntity<?> delete(Long id) {
        var product = findById(id);
        if (product == null) return ResponseEntity.notFound().build();
        if (product.isArchived()) return ResponseEntity.badRequest().build();

        if (!product.getItems().isEmpty()) {
            product.archive();
            save(product);
            return ResponseEntity.ok().build();
        }

        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
