package org.blackbird.service;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.Employee;

import java.util.List;

public interface EmployeeServiceImpl {
    Uni<Employee> findById(long id);
    Uni<List<Employee>> getEmployees();
    Uni<Employee> createEmployee(Employee employee);
    Uni<Employee> updateEmplyoee(Employee employee);
    Uni<Void> deleteEmployee(long id);
    Uni<Employee> getCurrentEmp();

    Uni<Employee> addEmployeeToDepartment(Employee employee, long depId);
}
