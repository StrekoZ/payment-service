package lv.dp.education.ps.notification.listener;

import lv.dp.education.ps.payment.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class RestNotificationEventListener extends BaseNotificationEventListener {
    @Override
    protected void processNotification(PaymentEntity source) throws NotificationFailedException {
        // todo rest call here
    }

    @Override
    protected String processorCode() {
        return "REST";
    }
}
