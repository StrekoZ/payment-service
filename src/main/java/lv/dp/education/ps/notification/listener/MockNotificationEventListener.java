package lv.dp.education.ps.notification.listener;

import lv.dp.education.ps.payment.PaymentEntity;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MockNotificationEventListener extends BaseNotificationEventListener {
    @Override
    protected void processNotification(PaymentEntity source) throws NotificationFailedException {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (new Random().nextBoolean()) {
            throw new NotificationFailedException("MockNotificationEventListener randomly failed");
        }
    }

    @Override
    protected String processorCode() {
        return "MOCK";
    }
}
