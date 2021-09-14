package com.griddynamics.qa.servicetesting;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTests extends BaseTest{

    @Test (enabled = false)
    public void getBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        // Set path parameter
        spec.pathParam("bookingId", bookingid);

        // Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        // Verify all fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Dmitry", "Lol");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Shyshkin", "Lol");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 150, "Lol");

        boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "Lol");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-03-25", "Lol");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-03-27", "Lol");

        softAssert.assertAll();
    }

    @Test
    public void getBookingXMLTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        // Set path parameter
        spec.pathParam("bookingId", bookingid);

        // Get response with booking ids
        Header xml = new Header("Accept", "application/xml");
        spec.header(xml);
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        // Verify all fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.xmlPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Dmitry", "Lol");

        String actualLastName = response.xmlPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Shyshkin", "Lol");

        int price = response.xmlPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 150, "Lol");

        boolean depositpaid = response.xmlPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositpaid, "Lol");

        String actualCheckin = response.xmlPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-03-25", "Lol");

        String actualCheckout = response.xmlPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-03-27", "Lol");

        softAssert.assertAll();
    }
}
