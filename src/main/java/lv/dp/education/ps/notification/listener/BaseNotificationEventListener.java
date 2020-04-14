package lv.dp.education.ps.notification.listener;

import lombok.extern.slf4j.Slf4j;
import lv.dp.education.ps.notification.NotificationEvent;
import lv.dp.education.ps.notification.NotificationService;
import lv.dp.education.ps.payment.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;


@Slf4j
public abstract class BaseNotificationEventListener implements ApplicationListener<NotificationEvent> {
    @Autowired
    private NotificationService notificationService;

    @Override
    @Async
    public void onApplicationEvent(NotificationEvent event) {
        var notificationEntity = notificationService.createNotificationEntity((PaymentEntity)event.getSource(), processorCode());

        try {
            processNotification((PaymentEntity) event.getSource());
            notificationService.completeNotification(notificationEntity);
        } catch (Exception e) {
            log.error("Error while process Notification for payment " + event.getSource(), e);
            notificationService.failNotification(notificationEntity, e.getMessage());
        }
    }

    protected abstract void processNotification(PaymentEntity source) throws NotificationFailedException;

    protected abstract String processorCode() ;
}
