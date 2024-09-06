package application.amzn.controllers.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PatchProductDTO(
        @Size(min = 4, max = 255, message = "O nome deve ter entre 4 e 255 caracteres") String name,
        @Size(min = 10, max = 1500, message = "A descrição deve ter entre 10 e 1500 caracteres") String description,
        @Min(value = 0, message = "O valor não pode ser menor que zero") double price,
        @Min(value = 0, message = "A quantidade em estoque não pode ser menor que zero.") int quantity
) {
}
