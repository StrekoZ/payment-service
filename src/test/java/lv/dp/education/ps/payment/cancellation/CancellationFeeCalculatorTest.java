package lv.dp.education.ps.payment.cancellation;

import lv.dp.education.ps.payment.PaymentEntity;
import lv.dp.education.ps.payment.cancellation.exception.CancellationNotPossibleException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CancellationFeeCalculatorTest {
    @Mock
    private Clock clock;
    @Mock
    private PaymentCancellationFeeCoefficientProvider coefficientProvider;
    @InjectMocks
    private CancellationFeeCalculator calculator;

    @Before
    public void before() {
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(coefficientProvider.coefficient(any())).thenReturn(new BigDecimal("0.1"));
    }

    @Test
    public void testCalculatorDateProcessing() {
        // today at 01:30
        var time = LocalDate.now().atStartOfDay().plusHours(1).plusMinutes(30);

        assertThrows(CancellationNotPossibleException.class, () -> {
            // next day at 01:30
            when(clock.instant()).thenReturn(time.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
            calculator.calculateFee(createPayment(time));
        });
        assertThrows(CancellationNotPossibleException.class, () -> {
            // next day at 00:10
            when(clock.instant()).thenReturn(time.toLocalDate().plusDays(1).atStartOfDay().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant());
            calculator.calculateFee(createPayment(time));
        });
        assertThrows(CancellationNotPossibleException.class, () -> {
            // today at 00:10
            when(clock.instant()).thenReturn(time.toLocalDate().atStartOfDay().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant());
            calculator.calculateFee(createPayment(time));
        });
        assertDoesNotThrow(() -> {
            // today at 01:30
            when(clock.instant()).thenReturn(time.atZone(ZoneId.systemDefault()).toInstant());
            calculator.calculateFee(createPayment(time));
        });
        assertDoesNotThrow(() -> {
            // today at 03:30
            when(clock.instant()).thenReturn(time.plusHours(2).atZone(ZoneId.systemDefault()).toInstant());
            calculator.calculateFee(createPayment(time));
        });
    }

    @Test
    public void testCalculateFee() {
        // today at 01:30
        var time = LocalDate.now().atStartOfDay().plusHours(1).plusMinutes(30);

        when(clock.instant()).thenReturn(time.plusHours(2).atZone(ZoneId.systemDefault()).toInstant());
        assertEquals(new BigDecimal("0.20"), calculator.calculateFee(createPayment(time)));

        when(clock.instant()).thenReturn(time.plusHours(1).plusMinutes(59).atZone(ZoneId.systemDefault()).toInstant());
        assertEquals(new BigDecimal("0.10"), calculator.calculateFee(createPayment(time)));

        when(clock.instant()).thenReturn(time.plusHours(2).plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
        assertEquals(new BigDecimal("0.20"), calculator.calculateFee(createPayment(time)));
    }
    
    public PaymentEntity createPayment(LocalDateTime time) {
        var payment = new PaymentEntity();
        payment.setCreateDate(time);
        return payment;
    }
}