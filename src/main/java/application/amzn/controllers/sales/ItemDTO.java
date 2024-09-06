package application.amzn.controllers.sales;

import jakarta.validation.constraints.Min;

public record ItemDTO(
        @Min(value = 1, message = "O id do produto não pode ser menor que um") Long productId,
        @Min(value = 1, message = "A quantidade não pode ser menor que um") Integer quantity) {
}
