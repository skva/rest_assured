package com.griddynamics.qa.servicetesting;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTests extends BaseTest{

    @Test
    public void getBookingIdWithoutFilterTest() {
        // Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        //Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingids is empty");
    }

    // 2.10 homework
    @Test
    public void getFirstIdBook() {
        // Get response with book id 1
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/1");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        //Verify at least 1 booking id in response
        Assert.assertEquals(response.jsonPath().get("firstname"), "Jim", "lol");
        Assert.assertEquals(response.jsonPath().get("lastname"), "Wilson", "lol");
    }

    @Test
    public void getBookingIdsWithFilterTest() {
        // Add query parameter to spec
        spec.queryParam("firstname", "Susan");
        spec.queryParam("lastname", "Smith");

        // Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        //Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingids is empty");
    }
}
