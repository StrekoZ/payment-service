package lv.dp.education.ps.payment;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lv.dp.education.ps.payment.cancellation.CancellationEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment")
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class PaymentEntity {

    public enum Type {TYPE1, TYPE2, TYPE3}

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @org.hibernate.annotations.Type(type = "uuid-char")
    private UUID uuid;

    @Column(nullable = false)
    private String creator;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

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

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private CancellationEntity cancellation;
}
