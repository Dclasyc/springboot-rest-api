package com.dclasyc.springboot.firstrestapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
    private static String GET_ALL_QUESTIONS_URL = "/surveys/Survey1/questions";
    private static String GET_ALL_SURVEYS_URL = "/surveys";
    private static String GET_SPECIFIC_SURVEY_URL = "/surveys/Survey1";
    private static String POST_SURVEY_URL = "/surveys/Survey1/questions";
    @Autowired
    private TestRestTemplate template;

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

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

    }

    @Test
    void retrieveAllSurveyQuestions_BasicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(GET_ALL_QUESTIONS_URL, String.class);

        // 20231124041428
        // http://localhost:8080/surveys/Survey1/questions
        String expectedResponse = """

                  [
                    {
                      "id": "Question1"
                    },
                    {
                      "id": "Question2"
                      },
                    {
                      "id": "Question3"
                      }
                  ]
                """;
        //Check for Content-Type returned
        //Check Status of request
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

    }

    @Test
    void retrieveAllSurvey_BasicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(GET_ALL_SURVEYS_URL, String.class);
        // 20231124042856
        // http://localhost:8080/surveys

        String expectedResponse = """
                  
                                   [
                                     {
                                       "id": "Survey1",

                                       "questions": [
                                         {
                                           "id": "Question1"
                                         },
                                         {
                                           "id": "Question2"
                                          },
                                         {
                                           "id": "Question3"
                                           }
                                       ]
                                     }
                                   ]
                """;
        //Check for Content-Type returned
        //Check Status of request
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    void getSurveyById_BasicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(GET_SPECIFIC_SURVEY_URL, String.class);
// 20231124043550
// http://localhost:8080/surveys/Survey1
        String expectedResponse = """
                  
                                   
                                     {
                                       "id": "Survey1",

                                       "questions": [
                                         {
                                           "id": "Question1"
                                         },
                                         {
                                           "id": "Question2"
                                          },
                                         {
                                           "id": "Question3"
                                           }
                                       ]
                                     }
                                   
                """;
        //Check for Content-Type returned
        //Check Status of request
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    void addNewSurveyQuestion_BasicScenario() {

        String requestBody = """
                         {
                         "description": "My Favourite Cloud Platform Today",
                         "options": [
                           "AWS",
                           "Azure",
                           "Google Cloud",
                           "Oracle Cloud"
                         ],
                         "correctAnswer": "AWS"
                         }
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = template.exchange(POST_SURVEY_URL, HttpMethod.POST, httpEntity, String.class, requestBody);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        String locationHeader = responseEntity.getHeaders().get("Location").get(0);
        assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));

        //Delete Created
        template.delete(locationHeader);

    }


}
