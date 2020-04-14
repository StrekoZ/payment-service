package lv.dp.education.ps.notification;

import lv.dp.education.ps.payment.PaymentEntity;
import org.springframework.context.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {
    public NotificationEvent(PaymentEntity paymentEntity) {
        super(paymentEntity);
    }
}
