package com.griddynamics.qa.servicetesting;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTests extends BaseTest {

    @Test
    public void partialUpdateBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Jack");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2020-08-29");
        body.put("bookingdates", bookingdates);

        // Update booking
        Response responseUpdate = RestAssured.given(spec).
                auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON).body(body.toString())
                .patch("/booking/" + bookingid);
        responseUpdate.print();

        // Verifications
        // Verify response 200
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Jack", "firstname in response is not expected");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Shyshkin", "lastname in response is not expected");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-08-29", "checkin in response is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2021-09-13", "checkout in response is not expected");

        String actualAdditionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }
}