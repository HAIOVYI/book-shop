package mate.academy.mybookshop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private UserEntity user;

    @NotNull
    private Status status;

    @NotNull
    private BigDecimal total;

    @NotNull
    private LocalDateTime orderDate;






}
