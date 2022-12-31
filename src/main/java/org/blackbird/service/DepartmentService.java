package org.blackbird.service;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.blackbird.model.Department;
import org.hibernate.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class DepartmentService implements DepartmentServiceImpl{

    @Inject
    private EmployeeServiceImpl employeeService;

    @Override
    public Uni<Department> findById(long id) {
        return Department.<Department>findById(id)
                .onItem().ifNull().failWith(() -> new ObjectNotFoundException(id, "Abteilung"));
    }

    @Override
    public Uni<List<Department>> getDepartments() {
        return Department.listAll();
    }

    @ReactiveTransactional
    @Override
    public Uni<Department> createDepartment(Department department) {
        return department.persistAndFlush();
    }

    @ReactiveTransactional
    @Override
    public Uni<Department> updateDepartment(Department department) {
        return findById(department.id)
                .chain(department1 -> Department.getSession())
                .chain(s -> s.merge(department));
    }

    @ReactiveTransactional
    @Override
    public Uni<Void> deleteDepartment(long id) {
        return findById(id).chain(department -> department.delete());
    }
}
