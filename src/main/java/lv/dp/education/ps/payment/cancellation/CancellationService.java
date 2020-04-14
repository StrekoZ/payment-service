package lv.dp.education.ps.payment.cancellation;

import lv.dp.education.ps.payment.Currency;
import lv.dp.education.ps.payment.PaymentEntity;
import lv.dp.education.ps.payment.cancellation.exception.PaymentAlreadyCancelledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class CancellationService {
    @Autowired
    private CancellationRepository cancellationRepository;
    @Autowired
    private CancellationFeeCalculator cancellationFeeCalculator;
    @Autowired
    private Clock clock;

    public void cancelPayment(PaymentEntity payment) {
        if (payment.getCancellation() != null) {
            throw new PaymentAlreadyCancelledException();
        }
        var cancellation = new CancellationEntity();
        cancellation.setPayment(payment);
        cancellation.setCreateDate(LocalDateTime.now(clock));
        cancellation.setFee(cancellationFeeCalculator.calculateFee(payment));
        cancellation.setCurrency(Currency.EUR);
        cancellationRepository.save(cancellation);
    }



}
