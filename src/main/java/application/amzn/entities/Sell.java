package application.amzn.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sells")
public class Sell implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 0, message = "O valor não pode ser menor que zero.")
    private double total;

    @PastOrPresent(message = "A data de venda não pode ser futura.")
    private Instant createdAt;

    @FutureOrPresent(message = "A data de atualização não pode ser do passado.")
    private Instant updatedAt;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<Item> items;

    public Sell() {
    }

    public Sell(long id, double total, Instant createdAt, Instant updatedAt, List<Item> items) {
        this.id = id;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sell sell = (Sell) o;
        return Objects.equals(id, sell.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sell{" +
                "id=" + id +
                ", total=" + total +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", items=" + items +
                '}';
    }
}
