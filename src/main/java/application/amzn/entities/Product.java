package application.amzn.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "products")
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

    public Product() {
    }

    public Product(Long id, String name, String description, Double price, Integer quantity, Boolean archived) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.archived = archived;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getArchived() {
        return archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
