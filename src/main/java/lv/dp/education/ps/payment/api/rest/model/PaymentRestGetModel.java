package lv.dp.education.ps.payment.api.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Boolean cancelled;
    private CancellationFee cancellationFee;

    @Data
    @AllArgsConstructor @NoArgsConstructor
    public static class CancellationFee {
        private BigDecimal amount;
        private String currency;
    }
}
