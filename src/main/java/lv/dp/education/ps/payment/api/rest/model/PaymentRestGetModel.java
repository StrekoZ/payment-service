package lv.dp.education.ps.payment.api.rest.model;

import lombok.Data;
import lv.dp.education.ps.payment.entity.Currency;
import lv.dp.education.ps.payment.entity.PaymentType;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRestGetModel {
    private UUID uuid;
    private PaymentType type;
    private BigDecimal amount;
    private Currency currency;
    private String debtorIBAN;
    private String creditorIBAN;
    private String details;
    private String creditorBankBIC;
}
