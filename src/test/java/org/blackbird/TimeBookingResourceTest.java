package org.blackbird;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.blackbird.model.Employee;
import org.blackbird.model.TimeBooking;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TimeBookingResourceTest {

    @Test
    public void testGetTimeBookings() {
        testCreateTimeBooking();
        given()
                .when().get("/api/v1/bookings")
                .then()
                .statusCode(200)
                .body("data.size()", greaterThanOrEqualTo(1));
    }

    @Test
    public void testCreateTimeBooking() {
        createSomeEmployees();
        given()
                .body("{\"startDate\":\"2022-01-22T06:07:08.644\",\"endDate\":\"2022-01-22T12:30:08.644\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/bookings")
                .then()
                .statusCode(201)
                .body(
                        "startDate", is("2022-01-22T06:07:08.644"));
    }

    @Test
    public void testUpdateTimeBooking() {
        createSomeEmployees();
        var booking = given()
                .body("{\"startDate\":\"2022-01-22T06:07:08.644\",\"endDate\":\"2022-01-22T12:30:08.644\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/bookings")
                .as(TimeBooking.class);
        booking.setEndDate(LocalDateTime.of(2022, 10, 23, 10, 20, 35));
        given()
                .body(booking)
                .contentType(ContentType.JSON)
                .when().put("/api/v1/bookings/" + booking.id)
                .then()
                .statusCode(200)
                .body("endDate", is(LocalDateTime.of(2022, 10, 23, 10, 20, 35).toString()));
    }

    @Test
    public void deleteTimeBooking() {
        createSomeEmployees();
        var booking = given()
                .body("{\"startDate\":\"2022-01-22T06:07:08.644\",\"endDate\":\"2022-01-22T12:30:08.644\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/bookings")
                .as(TimeBooking.class);

        given()
                .when().delete("/api/v1/bookings/" + booking.id)
                .then()
                .statusCode(204);
        assertThat(TimeBooking.findById(booking.id).await().indefinitely(), nullValue());
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
