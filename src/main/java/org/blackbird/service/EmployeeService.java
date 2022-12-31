package org.blackbird.service;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.blackbird.model.Employee;
import org.hibernate.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EmployeeService implements EmployeeServiceImpl {
    @Inject
    private DepartmentServiceImpl departmentService;

    @Override
    public Uni<Employee> findById(long id) {
        return Employee.<Employee>findById(id).onItem()
                .ifNull().failWith(() -> new ObjectNotFoundException(id, "Arbeiter"));
    }

    @Override
    public Uni<List<Employee>> getEmployees() {
        return Employee.listAll();
    }

    @ReactiveTransactional
    @Override
    public Uni<Employee> createEmployee(Employee employee) {
        return employee.persistAndFlush();
    }

    @ReactiveTransactional
    @Override
    public Uni<Employee> updateEmplyoee(Employee employee) {
        return findById(employee.id)
                .chain(e -> Employee.getSession())
                .chain(s -> s.merge(employee));
    }

    @ReactiveTransactional
    @Override
    public Uni<Void> deleteEmployee(long id) {
        /**
         * Um sicherzustellen, dass der Benutzer nur dann gelöscht wird, wenn alle zugehörigen
         * Notifications, Department und TimeBooking gelöscht sind, kombinieren wir beide Löschvorgänge
         * in einem einzigen Uni-Objekt
         * findById(id)
         *                 .chain(u -> Uni.combine().all().unis(
         *                         Notification.delete("employee.id", u.id),
         *                         Department.delete("employee.id", u.id),
         *                         TimeBooking.delete("employee.id", u.id)
         *                 ).asTuple().chain(t -> u.delete()));
         */
        return findById(id).chain(t -> t.delete());
    }

    public Uni<Employee> getCurrentEmp() {
        return Employee.find("order by id").firstResult();
    }

    @Override
    public Uni<Employee> addEmployeeToDepartment(Employee employee, long depId) {
        return departmentService.findById(depId)
                .chain(department -> {
                    employee.setDepartment(department);
                    return employee.persistAndFlush();
                });
    }
}
