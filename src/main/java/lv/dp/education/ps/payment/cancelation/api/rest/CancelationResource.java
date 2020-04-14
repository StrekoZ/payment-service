package lv.dp.education.ps.payment.cancelation.api.rest;

import io.swagger.annotations.ApiOperation;
import lv.dp.education.ps.common.Mapper;
import lv.dp.education.ps.payment.PaymentService;
import lv.dp.education.ps.payment.api.rest.model.PaymentRestGetModel;
import lv.dp.education.ps.payment.api.rest.model.PaymentsRestGetModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("payment/{uuid}/cancelation")
public class CancelationResource {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private Mapper mapper;

    @PutMapping
    @ApiOperation(value = "Cancel payment",
            notes = "Create payment cancelation")
    @Secured("ROLE_CLIENT")
    public void createPayment(@PathVariable UUID uuid,
                       HttpServletResponse response) {
        var payment = paymentService.getPaymentForClient(uuid);
        if (payment == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        paymentService.cancelPayment(payment);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @GetMapping
    @ApiOperation(
            value = "List payments",
            notes = "List payments of a current user"
    )
    @Secured("ROLE_CLIENT")
    public List<PaymentsRestGetModel> listPayments() {
        return paymentService.listPaymentsForClient().stream()
                .map(e -> mapper.map(e, PaymentsRestGetModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{uuid}")
    @ApiOperation(
            value = "Show payment",
            notes = "Show Payment details"
    )
    @Secured("ROLE_CLIENT")
    public PaymentRestGetModel showPayment(@PathVariable UUID uuid,
                                           HttpServletResponse response) {
        var payment = paymentService.getPaymentForClient(uuid);
        if (payment == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return mapper.map(payment, PaymentRestGetModel.class);
    }

}