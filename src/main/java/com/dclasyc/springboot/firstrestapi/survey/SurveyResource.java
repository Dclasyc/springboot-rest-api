package com.dclasyc.springboot.firstrestapi.survey;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyResource {
    //Create instance of Survey Class
    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        super();
        this.surveyService = surveyService;
    }

    // We are building /surveys => surveys
    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys(){
        return  surveyService.retrieveAllSurveys();
    };

    //List Survey by id /surveys/{id}
    @RequestMapping("/surveys/{SurveyId}")
    public Survey getSurveyById(@PathVariable String SurveyId){
        Survey survey = surveyService.getSurveyById(SurveyId);
        if(survey == null){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        return survey;
    }

    @RequestMapping("/surveys/{SurveyId}/questions")
    public List<Question> retrieveAllSurveyQuestion(@PathVariable String SurveyId){
        List<Question> questions = surveyService.retrieveAllSurveyQuestions(SurveyId);

        if(questions == null){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }

        return questions;
    }

    @RequestMapping("/surveys/{SurveyId}/questions/{QuestionId}")
    public Question retrieveSurveyQuestionById(@PathVariable String SurveyId, @PathVariable String QuestionId){

        Question question = surveyService.retrieveSurveyQuestionById(SurveyId, QuestionId);

        if(question == null){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }

        return question;
    }

    @RequestMapping(value = "/surveys/{SurveyId}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String SurveyId, @RequestBody Question question){
        String questionId= surveyService.addNewSurveyQuestion(SurveyId, question);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{QuestionId}")
                        .buildAndExpand(questionId)
                        .toUri();
        return ResponseEntity.created(location).build();
    }


    @RequestMapping(value = "/surveys/{SurveyId}/questions/{QuestionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestionById(@PathVariable String SurveyId, @PathVariable String QuestionId){

        surveyService.deleteSurveyQuestionById(SurveyId, QuestionId);

        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = "/surveys/{SurveyId}/questions/{QuestionId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSurveyQuestionById(
            @PathVariable String SurveyId,
            @PathVariable String QuestionId,
            @RequestBody Question question){

       String questionId = surveyService.updateSurveyQuestionById(SurveyId, QuestionId, question);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{QuestionId}")
                .buildAndExpand(questionId)
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
