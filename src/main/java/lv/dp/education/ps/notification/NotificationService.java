package lv.dp.education.ps.notification;

import lv.dp.education.ps.payment.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private Clock clock;

    public NotificationEntity createNotificationEntity(PaymentEntity source, String processorCode) {
        var notification = new NotificationEntity();
        notification.setProcessorCode(processorCode);
        notification.setStatus(NotificationEntity.Status.CREATED);
        notification.setCreateDate(LocalDateTime.now(clock));
        notification.setPayment(source);
        notificationRepository.save(notification);
        return notification;
    }

    public void completeNotification(NotificationEntity notification) {
        notification.setStatus(NotificationEntity.Status.COMPLETED);
        notification.setFinishDate(LocalDateTime.now(clock));
        notificationRepository.save(notification);
    }

    public void failNotification(NotificationEntity notification, String message) {
        notification.setStatus(NotificationEntity.Status.FAILED);
        notification.setFailedMessage(message);
        notification.setFinishDate(LocalDateTime.now(clock));
        notificationRepository.save(notification);
    }
}
