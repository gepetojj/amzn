package application.amzn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 0, message = "A quantidade não pode ser menor que zero.")
    private int quantity;

    @Min(value = 0, message = "O preço não pode ser menor que zero.")
    private double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sell_id")
    @JsonIgnore
    private Sell sale;

    public Item(int quantity, double price, Product product, Sell sale) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", product=" + product +
                '}';
    }
}
