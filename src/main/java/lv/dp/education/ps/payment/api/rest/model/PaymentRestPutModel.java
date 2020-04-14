package lv.dp.education.ps.payment.api.rest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lv.dp.education.ps.payment.PaymentEntity;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentType1RestPutModel.class, name = "TYPE1"),
        @JsonSubTypes.Type(value = PaymentType2RestPutModel.class, name = "TYPE2"),
        @JsonSubTypes.Type(value = PaymentType3RestPutModel.class, name = "TYPE3")
})
public abstract class PaymentRestPutModel {
    @Digits(integer = 20, fraction = 2)
    @NotNull
    protected BigDecimal amount;

    @NotNull
    protected String debtorIBAN;

    @NotNull
    protected String creditorIBAN;

    @JsonIgnore
    public abstract PaymentEntity.Type getType();
}
