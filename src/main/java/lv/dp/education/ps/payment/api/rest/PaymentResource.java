package lv.dp.education.ps.payment.api.rest;

import io.swagger.annotations.ApiOperation;
import lv.dp.education.ps.common.mapping.Mapper;
import lv.dp.education.ps.payment.PaymentEntity;
import lv.dp.education.ps.payment.PaymentService;
import lv.dp.education.ps.payment.api.rest.model.PaymentRestGetModel;
import lv.dp.education.ps.payment.api.rest.model.PaymentRestPutModel;
import lv.dp.education.ps.payment.api.rest.model.PaymentsRestGetModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("payment")
public class PaymentResource {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private Mapper mapper;

    @PutMapping
    @ApiOperation(value = "Create payment",
            notes = "Create new payment")
    @Secured("ROLE_CLIENT")
    public void createPayment(@RequestBody @Valid PaymentRestPutModel restModel,
                       HttpServletResponse response) {
        var entity = mapper.map(restModel, PaymentEntity.class);
        paymentService.createPayment(entity);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @GetMapping
    @ApiOperation(
            value = "List payments",
            notes = "List payments of a current user"
    )
    @Secured("ROLE_CLIENT")
    public List<PaymentsRestGetModel> listPayments() {
        return paymentService.listActivePaymentsForClient().stream()
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