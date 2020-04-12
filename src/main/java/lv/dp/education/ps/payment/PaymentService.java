package lv.dp.education.ps.payment;

import lv.dp.education.ps.common.UserService;
import lv.dp.education.ps.payment.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentRepository paymentRepository;

    public void createPayment(Payment entity) {
        entity.setCreator(userService.getCurrentUser());
        paymentRepository.save(entity);
    }

    public List<Payment> listPaymentsForClient() {
        return paymentRepository.findByCreator(userService.getCurrentUser());
    }

    public Payment getPaymentForClient(UUID uuid) {
        return paymentRepository.findByUuidAndCreator(uuid, userService.getCurrentUser());
    }
}
