package application.amzn.services;

import application.amzn.controllers.sales.ItemDTO;
import application.amzn.controllers.sales.PostSaleDTO;
import application.amzn.entities.Item;
import application.amzn.entities.Sale;
import application.amzn.repositories.ItemRepository;
import application.amzn.repositories.ProductRepository;
import application.amzn.repositories.SaleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SalesService {
    private final SaleRepository salesRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    public SalesService(SaleRepository salesRepository, ProductRepository productRepository, ItemRepository itemRepository, ObjectMapper objectMapper) {
        this.salesRepository = salesRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.objectMapper = objectMapper;
    }

    public Iterable<Sale> findAll() {
        return salesRepository.findAll();
    }

    public Sale findById(Long id) {
        return salesRepository.findById(id).orElse(null);
    }

    public void save(Sale sale) {
        salesRepository.save(sale);
    }

    @Transactional
    public ResponseEntity<Sale> sell(PostSaleDTO dto) {
        var sale = new Sale();

        for (ItemDTO itemDTO : dto.items()) {
            var product = productRepository.findById(itemDTO.productId()).orElse(null);
            if (product == null) {
                throw new RuntimeException("O produto não existe");
            }
            if (!product.isSellable(itemDTO.quantity())) {
                throw new RuntimeException("Não é possível vender esse produto");
            }

            var item = new Item(itemDTO.quantity(), product.getPrice(), product, sale);
            sale.addItem(item);
            sale.incrementTotal(itemDTO.quantity() * product.getPrice());
            itemRepository.save(item);
        }

        save(sale);
        return ResponseEntity.ok(sale);
    }

    public void patch(Sale sale, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(sale, JsonNode.class));
        save(objectMapper.treeToValue(patched, Sale.class));
    }

    public void delete(@org.jetbrains.annotations.NotNull Sale sale) {
        salesRepository.delete(sale);
    }
}
