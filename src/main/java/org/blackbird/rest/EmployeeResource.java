package org.blackbird.rest;

import io.smallrye.mutiny.Uni;
import org.blackbird.model.Employee;
import org.blackbird.service.EmployeeServiceImpl;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/employees")
public class EmployeeResource {
    @Inject
    private EmployeeServiceImpl employeeService;

    // JSON response media type is inferred from the presence of the quarkus-resteasy-reactive-jackson
    // dependency and is used as the default for most of the return types
    @GET
    public Uni<List<Employee>> getEmployees() {
        return employeeService.getEmployees();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<Employee> createEmployee(Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GET
    @Path("{id}")
    public Uni<Employee> getEmployee(@PathParam("id") long id) {
        return employeeService.findById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Uni<Employee> updateEmployee(@PathParam("id") long id, Employee employee) {
        employee.id = id;
        return employeeService.updateEmplyoee(employee);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> deleteEmployee(@PathParam("id") long id) {
        return employeeService.deleteEmployee(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add/{id}")
    public Uni<Employee> addEmployeeToDepartment(@PathParam("id") long id, Employee employee) {
        return employeeService.addEmployeeToDepartment(employee, id);
    }
}
