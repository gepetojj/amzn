package application.amzn.repositories;

import application.amzn.entities.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {
    List<Sale> findAllByCreatedAtBetween(Instant from, Instant to);
}
