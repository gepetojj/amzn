package application.amzn.controllers.sells;

import jakarta.validation.Valid;

import java.util.List;

public record PostSaleDTO(@Valid List<ItemDTO> items) {
}
