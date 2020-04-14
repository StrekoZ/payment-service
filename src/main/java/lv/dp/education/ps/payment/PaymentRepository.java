package lv.dp.education.ps.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    List<PaymentEntity> findByCreator(String userName);

    PaymentEntity findByUuidAndCreator(UUID uuid, String currentUser);
}