package lv.dp.education.ps.payment;

import lv.dp.education.ps.common.UserService;
import lv.dp.education.ps.notification.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private Clock clock;

    public void createPayment(PaymentEntity entity) {
        entity.setCreator(userService.getCurrentUser());
        entity.setCreateDate(LocalDateTime.now(clock));
        paymentRepository.save(entity);
        notifyAboutCreatedPayment(entity);
    }

    public List<PaymentEntity> listActivePaymentsForClient() {
        return paymentRepository.findByCreatorAndCancellationIsNull(userService.getCurrentUser());
    }

    public PaymentEntity getPaymentForClient(UUID uuid) {
        return paymentRepository.findByUuidAndCreator(uuid, userService.getCurrentUser());
    }

    private void notifyAboutCreatedPayment(PaymentEntity entity) {
        if (PaymentEntity.Type.TYPE1.equals(entity.getType())
                || PaymentEntity.Type.TYPE2.equals(entity.getType())) {
            eventPublisher.publishEvent(new NotificationEvent(entity));
        }
    }
}
