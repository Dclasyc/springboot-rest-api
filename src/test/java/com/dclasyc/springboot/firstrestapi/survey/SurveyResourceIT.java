package com.dclasyc.springboot.firstrestapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    String str = """
            
            {
              "id": "Question1",
              "description": "Most Popular Cloud Platform Today",
              "options": [
                "AWS",
                "Azure",
                "Google Cloud",
                "Oracle Cloud"
              ],
              "correctAnswer": "AWS"
            }
            
            """;

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
    @Autowired
    private TestRestTemplate template;

    //{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
    //[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Thu, 23 Nov 2023 01:20:13 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]

    @Test
    void retrieveSurveyQuestionById_BasicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        String expectedResponse = """
                {
                "id":"Question1",
                "description":"Most Popular Cloud Platform Today",
                "options":["AWS","Azure","Google Cloud","Oracle Cloud"]

                }
                """;
//        assertEquals(expectedResponse.trim(), responseEntity.getBody());
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(),false);

//        System.out.println(responseEntity.getBody());
//        System.out.println(responseEntity.getHeaders());

    }

}
