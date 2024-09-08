package application.amzn.services;

import application.amzn.entities.Item;
import application.amzn.entities.Product;
import application.amzn.entities.Report;
import application.amzn.entities.Sale;
import application.amzn.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportsService {
    private final SaleRepository saleRepository;

    @Autowired
    public ReportsService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> findAll() {
        var sales = saleRepository.findAll();
        List<Sale> salesList = new ArrayList<>();
        sales.forEach(salesList::add);
        return salesList;
    }

    public List<Sale> findByDate(Instant date) {
        var sales = saleRepository.findAllByCreatedAtBetween(date, date.plus(1, ChronoUnit.DAYS));

        var dateAtZone = date.atZone(ZoneId.of("America/Maceio"));
        var day = dateAtZone.getDayOfMonth() + 1;
        var month = dateAtZone.getMonthValue();
        var year = dateAtZone.getYear();

        return sales.stream().filter(sale -> {
            var saleDateAtZone = sale.getCreatedAt().atZone(ZoneId.of("America/Maceio"));
            return saleDateAtZone.getDayOfMonth() == day && saleDateAtZone.getMonthValue() == month && saleDateAtZone.getYear() == year;
        }).toList();
    }

    public Report generateReport(List<Sale> sales) {
        List<Product> products = new ArrayList<>();
        HashMap<Product, Integer> quantityByProduct = new HashMap<>();
        HashMap<Product, Double> soldByProduct = new HashMap<>();
        var totalSold = 0d;

        for (Sale sale : sales) {
            totalSold += sale.getTotal();

            for (Item item : sale.getItems()) {
                var product = item.getProduct();
                quantityByProduct.put(product, quantityByProduct.getOrDefault(product, 0) + 1);
                soldByProduct.put(product, soldByProduct.getOrDefault(product, 0d) + item.getQuantity() * item.getPrice());

                if (products.contains(product)) continue;
                products.add(product);
            }
        }

        return new Report(totalSold, soldByProduct, quantityByProduct, products);
    }
}
