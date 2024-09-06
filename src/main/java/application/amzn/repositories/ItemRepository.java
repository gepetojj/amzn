package application.amzn.repositories;

import application.amzn.entities.Item;
import application.amzn.entities.Sell;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> { }
