package org.blackbird.service;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.Notification;

import java.util.List;

public interface NotificationServiceImpl {
    Uni<Notification> findById(long id);
    Uni<List<Notification>> getNotifications();
    Uni<Notification> createNotification(Notification notification);
    Uni<Notification> createAbsenceNotification(Notification notification, long absenceId);
    Uni<Notification> updateNotification(Notification notification);
    Uni<Void> deleteNotification(long id);
}
