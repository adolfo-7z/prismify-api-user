package com.ufro.dci.etransparency.etransparency_api_user.models.survey;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import org.junit.jupiter.api.Test;

class SurveyTest {

    @Test
    void testSurveyConstructor() {
        Survey survey = new Survey();

        assertNull(survey.getId());
        assertNull(survey.getTitle());
        if (survey.getQuestions() == null) {
            survey.setQuestions(new ArrayList<>());
        }
        assertNotNull(survey.getQuestions());
        assertTrue(survey.getQuestions().isEmpty());
        assertNull(survey.getProcess());
        assertEquals(0, survey.getResponseCount());
    }

    @Test
    void testSettersAndGetters() {
        Survey survey = new Survey();
        Process process = new Process();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());

        survey.setTitle("Survey Title");
        survey.setQuestions(questions);
        survey.setProcess(process);
        survey.setResponseCount(5);

        assertEquals("Survey Title", survey.getTitle());
        assertEquals(questions, survey.getQuestions());
        assertEquals(process, survey.getProcess());
        assertEquals(5, survey.getResponseCount());
    }

    @Test
    void testAddQuestion() {
        Survey survey = new Survey();
        survey.setQuestions(new ArrayList<>());

        Question question = new Question();
        survey.getQuestions().add(question);

        assertEquals(1, survey.getQuestions().size());
        assertEquals(question, survey.getQuestions().get(0));
    }

    @Test
    void testRemoveQuestion() {
        Survey survey = new Survey();
        Question question1 = new Question();
        Question question2 = new Question();
        survey.setQuestions(new ArrayList<>(List.of(question1, question2)));

        survey.getQuestions().remove(question1);

        assertEquals(1, survey.getQuestions().size());
        assertEquals(question2, survey.getQuestions().get(0));
    }

    @Test
    void testIncrementResponseCount() {
        Survey survey = new Survey();
        survey.setResponseCount(3);

        survey.setResponseCount(survey.getResponseCount() + 1);

        assertEquals(4, survey.getResponseCount());
    }
}
