package lv.dp.education.ps.payment.api.rest.model;

import lv.dp.education.ps.common.mapping.DefaultMappingConfiguration;
import lv.dp.education.ps.common.mapping.MappingConfiguration;
import lv.dp.education.ps.payment.entity.Payment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentMappingConfig {

    @Bean
    public MappingConfiguration paymentType1RestPutModelToPayment() {
        return new DefaultMappingConfiguration(PaymentType1RestPutModel.class, Payment.class);
    }

    @Bean
    public MappingConfiguration paymentType2RestPutModelToPayment() {
        return new DefaultMappingConfiguration(PaymentType2RestPutModel.class, Payment.class);
    }

    @Bean
    public MappingConfiguration paymentType3RestPutModelToPayment() {
        return new DefaultMappingConfiguration(PaymentType3RestPutModel.class, Payment.class);
    }
}
