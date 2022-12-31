package org.blackbird.service;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.Department;

import java.util.List;

public interface DepartmentServiceImpl {
    Uni<Department> findById(long id);
    Uni<List<Department>> getDepartments();
    Uni<Department> createDepartment(Department department);
    Uni<Department> updateDepartment(Department department);
    Uni<Void> deleteDepartment(long id);
}
