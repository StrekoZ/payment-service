package lv.dp.education.ps.payment;

import lv.dp.education.ps.payment.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public void createPayment(Payment entity, String userName) {
        entity.setCreator(userName);
        paymentRepository.save(entity);
    }

    public List<Payment> listPaymentsForClient(String userName) {
        return paymentRepository.findByCreator(userName);
    }
}
