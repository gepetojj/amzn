package application.amzn.services;

import application.amzn.entities.Sell;
import application.amzn.repositories.SellRepository;
import org.springframework.stereotype.Service;

@Service
public class SellsService {
    final SellRepository repository;

    public SellsService(SellRepository repository) {
        this.repository = repository;
    }

    public Iterable<Sell> findAll() {
        return repository.findAll();
    }

    public Sell findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
