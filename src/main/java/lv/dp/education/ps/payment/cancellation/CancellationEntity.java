package lv.dp.education.ps.payment.cancellation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lv.dp.education.ps.payment.Currency;
import lv.dp.education.ps.payment.PaymentEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cancellation")
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class CancellationEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @OneToOne
    @JoinColumn(name = "payment_uuid")
    private PaymentEntity payment;

    @Column(nullable = false)
    private BigDecimal fee;

    @Column(nullable = false)
    private Currency currency;
}
