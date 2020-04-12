package lv.dp.education.ps.payment.api.rest.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lv.dp.education.ps.payment.entity.PaymentType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentType2RestPutModel extends PaymentRestPutModel {
    @NotNull
    @Pattern(regexp = "USD")
    protected String currency;

    private String details;

    @Override
    public PaymentType getType() {
        return PaymentType.TYPE2;
    }
}