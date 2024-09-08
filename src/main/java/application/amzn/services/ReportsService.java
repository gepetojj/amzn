package application.amzn.services;

import application.amzn.entities.Item;
import application.amzn.entities.Product;
import application.amzn.entities.Report;
import application.amzn.entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportsService {
    private final SalesService salesService;

    @Autowired
    public ReportsService(SalesService salesService) {
        this.salesService = salesService;
    }

    private List<Sale> iterableToList(Iterable<Sale> iterable) {
        List<Sale> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public List<Sale> findAll() {
        var sales = iterableToList(salesService.findAll());
        return sales;
    }

    public List<Sale> findByDate(Instant date) {
        var sales = iterableToList(salesService.findByDate(date));

        var dateAtZone = date.atZone(ZoneId.of("America/Maceio"));
        var day = dateAtZone.getDayOfMonth();
        var month = dateAtZone.getMonthValue();
        var year = dateAtZone.getYear();

        return sales.stream().filter(sale -> {
            var saleDateAtZone = sale.getCreatedAt().atZone(ZoneId.of("America/Maceio"));
            return saleDateAtZone.getDayOfMonth() == day && saleDateAtZone.getMonthValue() == month && saleDateAtZone.getYear() == year;
        }).toList();
    }

    public List<Sale> findByMonth(Instant date) {
        var sales = iterableToList(salesService.findAll());

        var dateAtZone = date.atZone(ZoneId.of("America/Maceio"));
        var month = dateAtZone.getMonthValue();
        var year = dateAtZone.getYear();

        return sales.stream().filter(sale -> {
            var saleDateAtZone = sale.getCreatedAt().atZone(ZoneId.of("America/Maceio"));
            return saleDateAtZone.getMonthValue() == month && saleDateAtZone.getYear() == year;
        }).toList();
    }

    public List<Sale> findByWeek(Instant date) {
        var dateAtZone = date.atZone(ZoneId.of("America/Maceio"));
        var dayOfWeek = dateAtZone.getDayOfWeek().getValue();
        var weekStart = dateAtZone.minusDays(dayOfWeek - 1);
        return iterableToList(salesService.findByWeek(weekStart.toInstant()));
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
