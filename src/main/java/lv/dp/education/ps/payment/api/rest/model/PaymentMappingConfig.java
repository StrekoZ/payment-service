package lv.dp.education.ps.payment.api.rest.model;

import lv.dp.education.ps.common.mapping.MappingConfiguration;
import lv.dp.education.ps.payment.PaymentEntity;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentMappingConfig {

    @Bean
    public MappingConfiguration paymentEntityToPaymentRestGetModel() {
        return mapperFactory -> mapperFactory.classMap(PaymentEntity.class, PaymentRestGetModel.class)
                .byDefault()
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(PaymentEntity paymentEntity, PaymentRestGetModel paymentRestGetModel, MappingContext context) {
                        super.mapAtoB(paymentEntity, paymentRestGetModel, context);
                        if (paymentEntity.getCancellation() != null) {
                            paymentRestGetModel.setCancelled(true);
                            paymentRestGetModel.setCancellationFee(new PaymentRestGetModel.CancellationFee(
                                    paymentEntity.getCancellation().getFee(),
                                    paymentEntity.getCancellation().getCurrency().name()
                            ));
                        } else {
                            paymentRestGetModel.setCancelled(false);
                        }
                    }
                })
                .register();
    }
}
