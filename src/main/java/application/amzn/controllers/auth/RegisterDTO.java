package application.amzn.controllers.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "O nome não deve estar em branco") @Size(min = 4, max = 255, message = "O nome deve ter entre 4 e 255 caracteres.") String name,
        @NotBlank(message = "O email não deve estar em branco") @Email(message = "O email deve ser válido") String email,
        @NotBlank(message = "A senha não deve estar em branco") @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres.") String password) {
}
