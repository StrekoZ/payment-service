package lv.dp.education.ps.payment.api.rest.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lv.dp.education.ps.payment.PaymentEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentType3RestPutModel extends PaymentRestPutModel {
    @NotNull
    @Pattern(regexp = "USD|EUR")
    protected String currency;

    @NotNull
    private String creditorBankBIC;

    @Override
    public PaymentEntity.Type getType() {
        return PaymentEntity.Type.TYPE3;
    }
}
