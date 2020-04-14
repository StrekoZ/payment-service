package lv.dp.education.ps.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
}
