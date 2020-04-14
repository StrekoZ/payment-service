package lv.dp.education.ps.payment.api.rest.model;

import lv.dp.education.ps.common.mapping.Mapper;
import lv.dp.education.ps.payment.Currency;
import lv.dp.education.ps.payment.PaymentEntity;
import lv.dp.education.ps.payment.cancellation.CancellationEntity;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentMappingConfigTest {

    private Mapper mapper;

    @Before
    public void before() {
        mapper = new Mapper();
        mapper.init(Set.of(new PaymentMappingConfig().paymentEntityToPaymentRestGetModel()));
    }

    @Test
    public void testPaymentEntityToPaymentRestGetModel() {
        var payment = new PaymentEntity();

        var restPayment = mapper.map(payment, PaymentRestGetModel.class);
        assertEquals(false, restPayment.getCancelled());
        assertNull(restPayment.getCancellationFee());

        var cancellation = new CancellationEntity();
        cancellation.setCurrency(Currency.USD);
        cancellation.setFee(BigDecimal.TEN);
        payment.setCancellation(cancellation);

        restPayment = mapper.map(payment, PaymentRestGetModel.class);
        assertEquals(true, restPayment.getCancelled());
        assertEquals(BigDecimal.TEN, restPayment.getCancellationFee().getAmount());
        assertEquals(Currency.USD.name(), restPayment.getCancellationFee().getCurrency());
    }

}