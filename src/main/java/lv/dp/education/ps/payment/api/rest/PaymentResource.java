package lv.dp.education.ps.payment.api.rest;

import io.swagger.annotations.ApiOperation;
import lv.dp.education.ps.common.mapping.Mapper;
import lv.dp.education.ps.payment.PaymentService;
import lv.dp.education.ps.payment.api.rest.model.PaymentRestPutModel;
import lv.dp.education.ps.payment.api.rest.model.PaymentsRestGetModel;
import lv.dp.education.ps.payment.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
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
    public void invest(@RequestBody @Valid PaymentRestPutModel restModel,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        Payment entity = mapper.map(restModel, Payment.class);
        paymentService.createPayment(entity, request.getUserPrincipal().getName());
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @GetMapping
    @ApiOperation(
            value = "List payments",
            notes = "List payments of a current user"
    )
    @Secured("ROLE_CLIENT")
    public List<PaymentsRestGetModel> listPayments(HttpServletRequest request) {
        return paymentService.listPaymentsForClient(request.getUserPrincipal().getName()).stream()
                .map(e -> mapper.map(e, PaymentsRestGetModel.class))
                .collect(Collectors.toList());
    }

}