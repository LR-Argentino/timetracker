package org.blackbird.service;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import org.blackbird.model.TimeBooking;
import org.hibernate.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TimeBookingService implements TimeBookingServiceImpl {

    @Inject
    private EmployeeServiceImpl employeeService;

    @Override
    public Uni<TimeBooking> findById(long id) {
        return employeeService.getCurrentEmp()
                .chain(employee -> TimeBooking.<TimeBooking>findById(id)
                        .onItem().ifNull().failWith(() -> new ObjectNotFoundException(id, "Zeit Buchung"))
                        .onItem().invoke(booking -> {
                            if (!employee.equals(booking.getEmployee())) {
                                throw new UnauthorizedException("You are not allowed to update this TimeBooking");
                            }
                        }));
    }

    @Override
    public Uni<List<TimeBooking>> getTimeBookings() {
        return employeeService.getCurrentEmp()
                .chain(employee -> TimeBooking.find("employee", employee).list());
    }

    @ReactiveTransactional
    @Override
    public Uni<TimeBooking> createTimeBooking(TimeBooking booking) {
        return employeeService.getCurrentEmp()
                .chain(employee -> {
                    booking.setEmployee(employee);
                    return booking.persistAndFlush();
                });
    }

    @ReactiveTransactional
    @Override
    public Uni<TimeBooking> updateTimeBooking(TimeBooking booking) {
        return findById(booking.id)
                .chain(b -> TimeBooking.getSession())
                .chain(s -> s.merge(booking));
    }

    @ReactiveTransactional
    @Override
    public Uni<Void> deleteTimeBooking(long id) {
        return findById(id)
                .chain(b -> b.delete());
    }
}
