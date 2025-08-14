package com.ufro.dci.etransparency.etransparency_api_user.models.level;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import com.ufro.dci.etransparency.etransparency_api_user.models.survey.Question;

import org.junit.jupiter.api.Test;

class LevelTest {

    @Test
    void testLevelConstructor() {
        Level level = new Level();
        assertNull(level.getId());
        assertFalse(level.isActive());
        assertNull(level.getName());
        assertNull(level.getTrueLevelValue());
        assertNull(level.getQuestions());
        assertNull(level.getDimension());
        if (level.getAnswers() == null) {
            level.setAnswers(new ArrayList<>());
        }
        assertNotNull(level.getAnswers());
        assertTrue(level.getAnswers().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        Level level = new Level();
        level.setId(1L);
        level.setActive(true);
        level.setName("Level 1");
        level.setTrueLevelValue(5L);
        level.setQuestions("Question 1, Question 2");
        assertEquals(1L, level.getId());
        assertTrue(level.isActive());
        assertEquals("Level 1", level.getName());
        assertEquals(5L, level.getTrueLevelValue());
        assertEquals("Question 1, Question 2", level.getQuestions());
    }

    @Test
    void testSetDimension() {
        Level level = new Level();
        Dimension dimension = new Dimension();
        dimension.setName("Dimension 1");
        level.setDimension(dimension);
        assertNotNull(level.getDimension());
        assertEquals("Dimension 1", level.getDimension().getName());
    }

    @Test
    void testAddAnswer() {
        Level level = new Level();
        level.setAnswers(new ArrayList<>());
        Question question1 = new Question();
        question1.setText("What is the level?");
        Question question2 = new Question();
        question2.setText("How satisfied are you?");
        level.getAnswers().add(question1);
        level.getAnswers().add(question2);
        assertEquals(2, level.getAnswers().size());
        assertEquals("What is the level?", level.getAnswers().get(0).getText());
        assertEquals("How satisfied are you?", level.getAnswers().get(1).getText());
    }

    @Test
    void testRemoveAnswer() {
        Level level = new Level();
        Question question = new Question();
        question.setText("What is the level?");
        level.setAnswers(new ArrayList<>(List.of(question)));
        assertEquals(1, level.getAnswers().size());
        level.getAnswers().remove(question);
        assertTrue(level.getAnswers().isEmpty());
    }

    @Test
    void testLevelToString() {
        Level level = new Level();
        level.setId(1L);
        level.setActive(true);
        level.setName("Level 1");
        level.setTrueLevelValue(10L);
        level.setQuestions("Sample Question");
        assertTrue(level.toString().contains("Level(id=1"));
        assertTrue(level.toString().contains("isActive=true"));
        assertTrue(level.toString().contains("name=Level 1"));
    }
}
