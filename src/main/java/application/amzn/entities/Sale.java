package application.amzn.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Sale implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Min(value = 0, message = "O valor não pode ser menor que zero.")
    private double total;

    @PastOrPresent(message = "A data de venda não pode ser futura.")
    private Instant createdAt;

    @FutureOrPresent(message = "A data de atualização não pode ser do passado.")
    private Instant updatedAt;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<Item> items = new ArrayList<>();

    public Sale() {
        total = 0;
        createdAt = Instant.now();
    }

    public void incrementTotal(double amount) {
        total += amount;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
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
