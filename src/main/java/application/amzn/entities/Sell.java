package application.amzn.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "sells")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
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
