package application.amzn.services;

import application.amzn.controllers.sells.ItemDTO;
import application.amzn.controllers.sells.PostSaleDTO;
import application.amzn.entities.Item;
import application.amzn.entities.Sell;
import application.amzn.repositories.ItemRepository;
import application.amzn.repositories.ProductRepository;
import application.amzn.repositories.SellRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SellsService {
    final SellRepository salesRepository;
    final ProductRepository productRepository;
    final ItemRepository itemRepository;

    public SellsService(SellRepository salesRepository, ProductRepository productRepository, ItemRepository itemRepository) {
        this.salesRepository = salesRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    public Iterable<Sell> findAll() {
        return salesRepository.findAll();
    }

    public Sell findById(Long id) {
        return salesRepository.findById(id).orElse(null);
    }

    public void save(Sell sell) {
        salesRepository.save(sell);
    }

    @Transactional
    public ResponseEntity<Sell> sell(PostSaleDTO dto) {
        var sale = new Sell();

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

            product.decrementQuantity(itemDTO.quantity());
            product.addItem(item);
            productRepository.save(product);
        }

        save(sale);
        return ResponseEntity.ok(sale);
    }
}
