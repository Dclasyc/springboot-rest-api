package com.dclasyc.springboot.firstrestapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.assertj.core.api.Assertions.*;

public class JsonAssertTest {

    @Test
    void test() throws JSONException {
        String expectedResponse = """
                {
                "id":"Question1",
                "description":"Most Popular Cloud Platform Today",
                "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                "correctAnswer":"AWS"
                }
                """;

        String actualResponse = """
                {
                "id":"Question1",
                "description":"Most Popular Cloud Platform Today",
                "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                "correctAnswer":"AWS"
                }
                """;

        JSONAssert.assertEquals(expectedResponse, actualResponse,true);


    }

}
