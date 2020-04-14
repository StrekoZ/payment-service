package lv.dp.education.ps.payment.cancellation.api.rest;

import io.swagger.annotations.ApiOperation;
import lv.dp.education.ps.payment.PaymentService;
import lv.dp.education.ps.payment.cancellation.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("payment/{uuid}/cancellation")
public class CancellationResource {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CancellationService cancellationService;

    @PutMapping
    @ApiOperation(value = "Cancel payment",
            notes = "Create payment cancellation")
    @Secured("ROLE_CLIENT")
    public void cancelPayment(@PathVariable UUID uuid,
                       HttpServletResponse response) {
        var payment = paymentService.getPaymentForClient(uuid);
        if (payment == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        cancellationService.cancelPayment(payment);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

}