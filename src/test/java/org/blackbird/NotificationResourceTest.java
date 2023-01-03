package org.blackbird;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.blackbird.model.Employee;
import org.blackbird.model.Notification;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class NotificationResourceTest {

    @Test
    public void testCreateNotification() {
        createSomeEmployees();
        given()
                .body("{\"content\":\"Elternsprechtag\",\"startDate\":\"2022-01-22T06:07:08.644\",\"endDate\":\"2022-01-22T18:07:08.644\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/notifications")
                .then()
                .statusCode(201)
                .body(
                        "content", is("Elternsprechtag"));
    }

    @Test
    public void testGetNotifications() {
        testCreateNotification();
        given()
                .when().get("/api/v1/notifications")
                .then()
                .statusCode(200)
                .body("data.size()", greaterThanOrEqualTo(1));
    }

    @Test
    public void testUpdateNotification() {
        createSomeEmployees();
        var notification = given()
                .body("{\"content\":\"Elternsprechtag\",\"startDate\":\"2022-01-22T06:07:08.644\",\"endDate\":\"2022-01-22T18:07:08.644\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/notifications")
                .as(Notification.class);
        notification.setEndDate(LocalDateTime.of(2022, 10, 23, 10, 20, 35));
        given()
                .body(notification)
                .contentType(ContentType.JSON)
                .when().put("/api/v1/notifications/" + notification.id)
                .then()
                .statusCode(200)
                .body("endDate", is(LocalDateTime.of(2022, 10, 23, 10, 20, 35).toString()));
    }

    @Test
    public void testDeleteNotification() {
        createSomeEmployees();
        var notification = given()
                .body("{\"content\":\"Elternsprechtag\",\"startDate\":\"2022-01-22T06:07:08.644\",\"endDate\":\"2022-01-22T18:07:08.644\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/notifications")
                .as(Notification.class);

        given()
                .when().delete("/api/v1/notifications/" + notification.id)
                .then()
                .statusCode(204);
        assertThat(Notification.findById(notification.id).await().indefinitely(), nullValue());
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
