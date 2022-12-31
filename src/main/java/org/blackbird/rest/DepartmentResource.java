package org.blackbird.rest;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.Department;
import org.blackbird.service.DepartmentServiceImpl;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/departments")
public class DepartmentResource {
    @Inject
    private DepartmentServiceImpl departmentService;

    @GET
    public Uni<List<Department>> getDepartments() {
        return departmentService.getDepartments();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<Department> createDepartment(Department department) {
        return departmentService.createDepartment(department);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Uni<Department> updateDepartment(@PathParam("id") long id, Department department) {
        department.id = id;
        return departmentService.updateDepartment(department);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> deleteDepartment(@PathParam("id") long id) {
        return departmentService.deleteDepartment(id);
    }

}
