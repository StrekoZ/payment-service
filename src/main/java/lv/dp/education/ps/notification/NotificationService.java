package lv.dp.education.ps.notification;

import lv.dp.education.ps.payment.PaymentEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;

@Service
public class NotificationService {
    @Inject
    private NotificationRepository notificationRepository;

    public NotificationEntity createNotificationEntity(PaymentEntity source, String processorCode) {
        var notification = new NotificationEntity();
        notification.setProcessorCode(processorCode);
        notification.setStatus(NotificationEntity.Status.CREATED);
        notification.setCreateDate(LocalDateTime.now());
        notification.setPayment(source);
        notificationRepository.save(notification);
        return notification;
    }

    public void completeNotification(NotificationEntity notification) {
        notification.setStatus(NotificationEntity.Status.COMPLETED);
        notification.setFinishDate(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public void failNotification(NotificationEntity notification, String message) {
        notification.setStatus(NotificationEntity.Status.FAILED);
        notification.setFailedMessage(message);
        notification.setFinishDate(LocalDateTime.now());
        notificationRepository.save(notification);
    }
}
