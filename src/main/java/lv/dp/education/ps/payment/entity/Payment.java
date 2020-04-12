package lv.dp.education.ps.payment.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payment")
@ToString(exclude = {})
@EqualsAndHashCode(exclude = {})
@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(nullable = false)
    private String creator;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private String debtorIBAN;

    @Column(nullable = false)
    private String creditorIBAN;

    private String details;

    private String creditorBankBIC;
}
