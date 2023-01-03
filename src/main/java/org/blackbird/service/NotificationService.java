package org.blackbird.service;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import org.blackbird.model.AbsenceType;
import org.blackbird.model.Notification;
import org.hibernate.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class NotificationService implements NotificationServiceImpl{
    @Inject
    private EmployeeServiceImpl employeeService;

    @Inject
    private AbsenceTypeServiceImpl absenceTypeService;

    @Override
    public Uni<Notification> findById(long id) {
        return employeeService.getCurrentEmp()
                .chain(employee -> Notification.<Notification>findById(id)
                        .onItem().ifNull().failWith(() -> new ObjectNotFoundException(id, "Abwesenheitsantrag konnte nicht gefunden werden."))
                        .onItem().invoke(notification -> {
                            if (!employee.equals(notification.getEmployee())) {
                                throw new UnauthorizedException("Du bist nicht erlaubt den Abwesenheitsantrag abzurufen!");
                            }
                        })
                );
    }

    @Override
    public Uni<List<Notification>> getNotifications() {
        return employeeService.getCurrentEmp()
                .chain(employee -> Notification.find("employee", employee).list());
    }

    @ReactiveTransactional
    @Override
    public Uni<Notification> createNotification(Notification notification) {
        return employeeService.getCurrentEmp()
                .chain(employee -> {
                    notification.setEmployee(employee);
                    return notification.persistAndFlush();
                });
    }

    @ReactiveTransactional
    @Override
    public Uni<Notification> createAbsenceNotification(Notification notification, long absenceId) {
        return employeeService.getCurrentEmp()
                .chain(employee -> {
                    notification.setEmployee(employee);
                    return absenceTypeService.<AbsenceType>findById(absenceId);
                }).onItem().transformToUni(item -> {
                    notification.setAbsenceType(item);
                    return notification.persistAndFlush();
                });
    }

    @ReactiveTransactional
    @Override
    public Uni<Notification> updateNotification(Notification notification) {
        return findById(notification.id)
                .chain(notification1 -> Notification.getSession())
                .chain(s -> s.merge(notification));
    }

    @ReactiveTransactional
    @Override
    public Uni<Void> deleteNotification(long id) {
        return findById(id).chain(notification -> notification.delete());
    }
}
