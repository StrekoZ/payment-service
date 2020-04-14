package lv.dp.education.ps.payment.api.rest;

import lv.dp.education.ps.payment.PaymentEntity;
import lv.dp.education.ps.payment.api.rest.model.PaymentRestGetModel;
import lv.dp.education.ps.payment.api.rest.model.PaymentsRestGetModel;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentEntityResourceTest {

    @LocalServerPort
    private int port;

    private WebTarget service1;
    private WebTarget service2;

    @Before
    public void before() {
        service1 = ClientBuilder.newClient()
                .register(HttpAuthenticationFeature.basic("client1", "123"))
                .target("http://localhost:" + port);
        service2 = ClientBuilder.newClient()
                .register(HttpAuthenticationFeature.basic("client2", "123"))
                .target("http://localhost:" + port);
    }

    @Test
    public void testCreatePayment_invalid_amount() throws IOException {
        Response response = service1.path("payment")
                .request()
                .put(Entity.entity(readResource("payment_type1_invalid_amount.json"), MediaType.APPLICATION_JSON));
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testCreatePayment_without_type() throws IOException {
        Response response = service1.path("payment")
                .request()
                .put(Entity.entity(readResource("payment_without_type.json"), MediaType.APPLICATION_JSON));
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testListPayments() throws IOException {
        service1.path("payment").request()
                .put(Entity.entity(readResource("payment_type1_valid.json"), MediaType.APPLICATION_JSON));
        service1.path("payment").request()
                .put(Entity.entity(readResource("payment_type2_valid.json"), MediaType.APPLICATION_JSON));
        service2.path("payment").request()
                .put(Entity.entity(readResource("payment_type3_valid.json"), MediaType.APPLICATION_JSON));

        List<PaymentsRestGetModel> payments = service2.path("payment").request().get(new GenericType<>() {});
        assertEquals(1, payments.size());

        Response response = service1.path("payment/" + payments.get(0).getUuid()).request().get();
        assertEquals(404, response.getStatus());

        PaymentRestGetModel payment = service2.path("payment/" + payments.get(0).getUuid()).request().get(PaymentRestGetModel.class);
        assertEquals(PaymentEntity.Type.TYPE3, payment.getType());
        assertEquals(new BigDecimal("3.33"), payment.getAmount());
        assertEquals("debtorIban3", payment.getDebtorIBAN());
        assertEquals("creditorIban3", payment.getCreditorIBAN());
        assertEquals(PaymentEntity.Currency.EUR, payment.getCurrency());
        assertEquals("BIC", payment.getCreditorBankBIC());
    }

    private String readResource(String resource) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(resource));
    }
}