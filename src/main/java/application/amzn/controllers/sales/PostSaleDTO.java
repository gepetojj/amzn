package application.amzn.controllers.sales;

import jakarta.validation.Valid;

import java.util.List;

public record PostSaleDTO(@Valid List<ItemDTO> items) {
}
