package lv.dp.education.ps.payment.cancellation.api.rest;

import lv.dp.education.ps.common.api.rest.BaseResourceTest;
import lv.dp.education.ps.payment.Currency;
import lv.dp.education.ps.payment.api.rest.model.PaymentRestGetModel;
import lv.dp.education.ps.payment.api.rest.model.PaymentsRestGetModel;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CancellationResourceTest extends BaseResourceTest {

    @Test
    public void testPaymentCancellation() throws IOException {
        // create payment
        withClient1.path("payment").request()
                .put(Entity.entity(readResource("/lv/dp/education/ps/payment/api/rest/payment_type1_valid.json"), MediaType.APPLICATION_JSON));
        List<PaymentsRestGetModel> payments = withClient1.path("payment").request()
                .get(new GenericType<>() {});

        final UUID paymentUUID = payments.get(0).getUuid();

        // try to cancel with different user
        var response = withClient2.path("payment/"+paymentUUID+"/cancellation").request()
                .put(Entity.entity("", MediaType.APPLICATION_JSON));
        assertEquals(404, response.getStatus());

        // cancel payment
        response = withClient1.path("payment/"+paymentUUID+"/cancellation").request()
                .put(Entity.entity("", MediaType.APPLICATION_JSON));
        assertEquals(201, response.getStatus());

        // check payment status
        var payment = withClient1.path("payment/" + paymentUUID).request()
                .get(PaymentRestGetModel.class);

        assertEquals(true, payment.getCancelled());
        assertEquals(new BigDecimal("0.00"), payment.getCancellationFee().getAmount());
        assertEquals(Currency.EUR.name(), payment.getCancellationFee().getCurrency());

        // cancel payment again
        response = withClient1.path("payment/"+paymentUUID+"/cancellation").request()
                .put(Entity.entity("", MediaType.APPLICATION_JSON));
        assertEquals(400, response.getStatus());
    }
}