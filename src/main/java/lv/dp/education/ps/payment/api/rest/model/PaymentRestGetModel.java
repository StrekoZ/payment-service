package lv.dp.education.ps.payment.api.rest.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRestGetModel {
    private UUID uuid;
    private String type;
    private BigDecimal amount;
    private String currency;
    private String debtorIBAN;
    private String creditorIBAN;
    private String details;
    private String creditorBankBIC;
}
