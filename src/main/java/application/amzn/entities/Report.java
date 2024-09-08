package application.amzn.entities;

import java.util.HashMap;
import java.util.List;

public record Report(
        Double totalSold,
        Integer totalSales,
        HashMap<Product, Double> soldByProduct,
        HashMap<Product, Integer> quantityByProduct,
        List<Product> products
) {
}
