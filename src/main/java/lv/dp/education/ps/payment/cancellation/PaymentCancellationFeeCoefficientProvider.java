package lv.dp.education.ps.payment.cancellation;

import lv.dp.education.ps.payment.PaymentEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;

@Component
public class PaymentCancellationFeeCoefficientProvider {
    private HashMap<PaymentEntity.Type, BigDecimal> coefficients = new HashMap<>();

    @PostConstruct
    public void init() {
        coefficients.put(PaymentEntity.Type.TYPE1, new BigDecimal("0.05"));
        coefficients.put(PaymentEntity.Type.TYPE2, new BigDecimal("0.10"));
        coefficients.put(PaymentEntity.Type.TYPE3, new BigDecimal("0.15"));
    }

    public BigDecimal coefficient(PaymentEntity.Type paymentType) {
        return coefficients.get(paymentType);
    }
}
