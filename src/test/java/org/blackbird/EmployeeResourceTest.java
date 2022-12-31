package org.blackbird;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.blackbird.model.Employee;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@QuarkusTest
public class EmployeeResourceTest {
    @Test
    public void testGetEmployees() {
        createSomeEmployees();
        var test = Employee.listAll().await().indefinitely();
        given()
                .when()
                .get("/api/v1/employees")
                .then()
                .statusCode(200)
                .body("data.size()", greaterThanOrEqualTo(2));
    }

    @Test
    public void testCreateEmployee() {
        given()
            .body("{\"firstName\":\"test\",\"lastName\":\"junittest\",\"email\":\"test@junit.com\", \"shortName\":\"tdd\"}")
            .contentType(ContentType.JSON)
            .when().post("/api/v1/employees")
            .then()
            .statusCode(201)
            .body(
                "firstName", is("test"),
                "shortName", is("tdd"),
                "phoneNumber", nullValue());
    }

    @Test
    public void testUpdateEmployee() {
        var employee = given()
                .body("{\"firstName\":\"test\",\"lastName\":\"junittest\",\"email\":\"test@junit.com\", \"shortName\":\"tdd\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/employees")
                .as(Employee.class);
        employee.setFirstName("update");
        given()
                .body(employee)
                .contentType(ContentType.JSON)
                .when().put("/api/v1/employees/" + employee.id)
                .then()
                .statusCode(200)
                .body("firstName", is("update"));
    }

    @Test
    public void deleteEmployee() {
        var employeeToDelete = given()
                .body("{\"firstName\":\"test\",\"lastName\":\"junittest\",\"email\":\"test@junit.com\", \"shortName\":\"tdd\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/employees")
                .as(Employee.class);

        given()
                .when().delete("/api/v1/employees/" + employeeToDelete.id)
                .then()
                .statusCode(204);
        assertThat(Employee.findById(employeeToDelete.id).await().indefinitely(), nullValue());
    }

    public void createSomeEmployees() {
        var employeeToDelete = given()
                .body("{\"firstName\":\"test\",\"lastName\":\"junittest\",\"email\":\"test@junit.com\", \"shortName\":\"tdd\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/employees")
                .as(Employee.class);
        var secondEmp = given()
                .body("{\"firstName\":\"second\",\"lastName\":\"emp\",\"email\":\"test@junit.com\", \"shortName\":\"tdd\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/employees")
                .as(Employee.class);
    }
}
