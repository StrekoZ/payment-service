package lv.dp.education.ps.payment.api.rest;

import lv.dp.education.ps.common.api.rest.BaseResourceTest;
import lv.dp.education.ps.payment.Currency;
import lv.dp.education.ps.payment.PaymentEntity;
import lv.dp.education.ps.payment.api.rest.model.PaymentRestGetModel;
import lv.dp.education.ps.payment.api.rest.model.PaymentsRestGetModel;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PaymentResourceTest extends BaseResourceTest {

    @Test
    public void testCreatePayment_invalid_amount() throws IOException {
        Response response = withClient1.path("payment")
                .request()
                .put(Entity.entity(readResource("payment_type1_invalid_amount.json"), MediaType.APPLICATION_JSON));
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testCreatePayment_without_type() throws IOException {
        Response response = withClient1.path("payment")
                .request()
                .put(Entity.entity(readResource("payment_without_type.json"), MediaType.APPLICATION_JSON));
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testListPayments() throws IOException {
        withClient1.path("payment").request()
                .put(Entity.entity(readResource("payment_type1_valid.json"), MediaType.APPLICATION_JSON));
        withClient1.path("payment").request()
                .put(Entity.entity(readResource("payment_type2_valid.json"), MediaType.APPLICATION_JSON));
        withClient2.path("payment").request()
                .put(Entity.entity(readResource("payment_type3_valid.json"), MediaType.APPLICATION_JSON));

        List<PaymentsRestGetModel> payments = withClient2.path("payment").request().get(new GenericType<>() {});
        assertEquals(1, payments.size());

        Response response = withClient1.path("payment/" + payments.get(0).getUuid()).request().get();
        assertEquals(404, response.getStatus());

        PaymentRestGetModel payment = withClient2.path("payment/" + payments.get(0).getUuid()).request().get(PaymentRestGetModel.class);
        assertEquals(PaymentEntity.Type.TYPE3.name(), payment.getType());
        assertEquals(new BigDecimal("3.33"), payment.getAmount());
        assertEquals("debtorIban3", payment.getDebtorIBAN());
        assertEquals("creditorIban3", payment.getCreditorIBAN());
        assertEquals(Currency.EUR.name(), payment.getCurrency());
        assertEquals("BIC", payment.getCreditorBankBIC());
    }
}