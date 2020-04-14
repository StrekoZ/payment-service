package lv.dp.education.ps.payment.cancellation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CancellationRepository extends JpaRepository<CancellationEntity, UUID> {

}