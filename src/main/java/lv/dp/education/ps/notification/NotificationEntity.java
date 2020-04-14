package lv.dp.education.ps.notification;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lv.dp.education.ps.payment.PaymentEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class NotificationEntity {

    public enum Status {CREATED, COMPLETED, FAILED}

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(nullable = false)
    private String processorCode;

    @ManyToOne
    @JoinColumn(name="payment_uuid", nullable=false)
    private PaymentEntity payment;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime createDate;

    private LocalDateTime finishDate;

    private String failedMessage;

}
