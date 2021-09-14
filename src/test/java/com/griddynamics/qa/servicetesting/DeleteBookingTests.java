package com.griddynamics.qa.servicetesting;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DeleteBookingTests extends BaseTest {

    @Test
    public void deleteBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        // Delete booking
        Response responseDelete = RestAssured.given(spec).
                auth().preemptive().basic("admin", "password123")
                .delete("/booking/" + bookingid);
        responseDelete.print();

        // Verifications
        // Verify response 201
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201, but it's not");

        Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + + bookingid);
        responseGet.print();

        Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Lol");
    }
}