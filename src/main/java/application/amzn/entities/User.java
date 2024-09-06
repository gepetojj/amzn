package application.amzn.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(min = 4, max = 255, message = "O nome deve ter entre 4 e 255 caracteres.")
    private String name;

    @NotBlank(message = "O email não pode ser vazio.")
    @Email(message = "O email precisa ter um formato válido.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "A senha não pode ser vazia.")
    private String password;

    private UserRole role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
