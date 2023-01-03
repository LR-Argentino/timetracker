package org.blackbird;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.blackbird.model.Employee;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class DepartmentResourceTest {

    @Test
    public void testCreateDepartment() {
        createSomeEmployees();
        given()
                .body("{\"depName\":\"Application Development\",\"shortName\":\"APD\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/departments")
                .then()
                .statusCode(201)
                .body(
                        "shortName", is("APD"));
    }

    @Test
    public void testGetDepartments() {
        testCreateDepartment();
        given()
                .when().get("/api/v1/departments")
                .then()
                .statusCode(200)
                .body("data.size()", greaterThanOrEqualTo(1));
    }

    @Test
    public void testUpdateDepartment() {

    }

    @Test
    public void testDeleteDepartment() {

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
