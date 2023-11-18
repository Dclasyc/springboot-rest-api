package com.dclasyc.springboot.firstrestapi.survey;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);

    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }


    public Survey getSurveyById(String surveyId) {

        Predicate<? super Survey> predicate = survey -> survey.getId().equalsIgnoreCase(surveyId);
        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();
        return optionalSurvey.orElse(null);

    }


    public List<Question> retrieveAllSurveyQuestions(String surveyId) {
        Survey survey = getSurveyById(surveyId);
        if(survey==null){
            return null;
        }
        return survey.getQuestions();
    }

    public Question retrieveSurveyQuestionById(String surveyId, String questionId) {
        List<Question> questions = retrieveAllSurveyQuestions(surveyId);
        if(questions == null){
            return null;
        }
        Predicate<? super Question> Predicate = question -> question.getId().equalsIgnoreCase(questionId);
        Optional<Question> optionQuestion = questions.stream().filter(Predicate).findFirst();
        return optionQuestion.orElse(null);
    }


    public String addNewSurveyQuestion(String surveyId, Question question) {
        List<Question> questions = retrieveAllSurveyQuestions(surveyId);
        question.setId(getRandomId());
        questions.add(question);
        return question.getId();
    }

    private static String getRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(32, secureRandom).toString();
    }


    public String deleteSurveyQuestionById(String surveyId, String questionId) {
        List<Question> questions = retrieveAllSurveyQuestions(surveyId);
        if(questions == null){
            return null;
        }
        Predicate<? super Question> Predicate = question -> question.getId().equalsIgnoreCase(questionId);
        boolean removed = questions.removeIf(Predicate);
        if(!removed) {
            return null;
        }
        return questionId;

    }

    public String updateSurveyQuestionById(String surveyId, String questionId, Question question) {
        List<Question> questions = retrieveAllSurveyQuestions(surveyId);
        questions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
        questions.add(question);
        return question.getId();
    }
}

