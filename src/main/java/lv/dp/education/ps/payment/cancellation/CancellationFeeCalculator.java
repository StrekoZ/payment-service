package lv.dp.education.ps.payment.cancellation;

import lv.dp.education.ps.payment.PaymentEntity;
import lv.dp.education.ps.payment.cancellation.exception.CancellationNotPossibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CancellationFeeCalculator {
    @Autowired
    private Clock clock;
    @Autowired
    private PaymentCancellationFeeCoefficientProvider coefficientProvider;

    public BigDecimal calculateFee(PaymentEntity payment) throws CancellationNotPossibleException {
        validatePaymentDate(payment.getCreateDate());

        return coefficientProvider.coefficient(payment.getType())
                .multiply(fullHours(payment.getCreateDate()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void validatePaymentDate(LocalDateTime createDate) throws CancellationNotPossibleException {
        if (LocalDate.now(clock).isAfter(createDate.toLocalDate())) {
            throw new CancellationNotPossibleException("Payment create time is not today");
        }
        if (LocalDateTime.now(clock).isBefore(createDate)) {
            throw new CancellationNotPossibleException("Payment create time is in future");
        }
    }

    private BigDecimal fullHours(LocalDateTime paymentCreateDate) {
        return BigDecimal.valueOf(Duration.between(paymentCreateDate, LocalDateTime.now(clock)).toHours());
    }

}
