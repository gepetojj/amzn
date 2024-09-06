package application.amzn.controllers.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "O email não deve estar em branco") @Email(message = "O email deve ser válido") String email,
        @NotBlank(message = "A senha não deve estar em branco") @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres.") String password) {
}
