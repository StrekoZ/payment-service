package lv.dp.education.ps.payment;

import lv.dp.education.ps.common.UserService;
import lv.dp.education.ps.notification.NotificationEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private Clock clock;

    @InjectMocks
    private PaymentService paymentService;

    @Before
    public void before() {
        when(userService.getCurrentUser()).thenReturn("test");
        when(paymentRepository.save(any())).thenReturn(null);
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(clock.instant()).thenReturn(Instant.now());
    }

    @Test
    public void testNotificationPublishType1() {
        paymentService.createPayment(paymentEntityWithType(PaymentEntity.Type.TYPE1));
        verify(eventPublisher, times(1)).publishEvent(any(NotificationEvent.class));
    }

    @Test
    public void testNotificationPublishType2() {
        paymentService.createPayment(paymentEntityWithType(PaymentEntity.Type.TYPE2));
        verify(eventPublisher, times(1)).publishEvent(any(NotificationEvent.class));
    }

    @Test
    public void testNotificationPublishType3() {
        paymentService.createPayment(paymentEntityWithType(PaymentEntity.Type.TYPE3));
        verify(eventPublisher, times(0)).publishEvent(any(NotificationEvent.class));
    }

    private PaymentEntity paymentEntityWithType(PaymentEntity.Type type) {
        var payment = new PaymentEntity();
        payment.setType(type);
        return payment;
    }
}