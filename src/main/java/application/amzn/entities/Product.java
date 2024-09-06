package application.amzn.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres.")
    private String name;

    @NotBlank(message = "A descrição não pode ser vazia.")
    @Size(min = 10, max = 1500, message = "A descrição deve ter entre 10 e 1500 caracteres.")
    private String description;

    @Min(value = 0, message = "O valor não pode ser menor que zero.")
    private double price;

    @Min(value = 0, message = "A quantidade em estoque não pode ser menor que zero.")
    private int quantity;

    private boolean archived;

    @OneToMany
    private List<Item> items;

    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.archived = false;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", archived=" + archived +
                '}';
    }
}
