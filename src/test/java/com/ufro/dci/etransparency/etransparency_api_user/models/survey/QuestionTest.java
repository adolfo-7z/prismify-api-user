package com.ufro.dci.etransparency.etransparency_api_user.models.survey;

import static org.junit.jupiter.api.Assertions.*;

import com.ufro.dci.etransparency.etransparency_api_user.models.level.Level;
import org.junit.jupiter.api.Test;

class QuestionTest {

    @Test
    void testQuestionConstructor() {
        Question question = new Question();

        assertNull(question.getId());
        assertNull(question.getText());
        assertNull(question.getLevelName());
        assertNull(question.getDimensionName());
        assertNull(question.getSurvey());
        assertNull(question.getLevel());
        assertEquals(0, question.getStronglyDisagreeCount());
        assertEquals(0, question.getDisagreeCount());
        assertEquals(0, question.getNeutralCount());
        assertEquals(0, question.getAgreeCount());
        assertEquals(0, question.getStronglyAgreeCount());
    }

    @Test
    void testSettersAndGetters() {
        Question question = new Question();
        Level level = new Level();

        question.setText("Sample Question");
        question.setLevelName("Level 1");
        question.setDimensionName("Dimension A");
        question.setLevel(level);
        question.setStronglyDisagreeCount(5);
        question.setDisagreeCount(10);
        question.setNeutralCount(15);
        question.setAgreeCount(20);
        question.setStronglyAgreeCount(25);

        assertEquals("Sample Question", question.getText());
        assertEquals("Level 1", question.getLevelName());
        assertEquals("Dimension A", question.getDimensionName());
        assertEquals(level, question.getLevel());
        assertEquals(5, question.getStronglyDisagreeCount());
        assertEquals(10, question.getDisagreeCount());
        assertEquals(15, question.getNeutralCount());
        assertEquals(20, question.getAgreeCount());
        assertEquals(25, question.getStronglyAgreeCount());
    }

    @Test
    void testDefaultCounts() {
        Question question = new Question();

        assertEquals(0, question.getStronglyDisagreeCount());
        assertEquals(0, question.getDisagreeCount());
        assertEquals(0, question.getNeutralCount());
        assertEquals(0, question.getAgreeCount());
        assertEquals(0, question.getStronglyAgreeCount());
    }

    @Test
    void testIncrementCounts() {
        Question question = new Question();

        question.setStronglyDisagreeCount(1);
        question.setDisagreeCount(2);
        question.setNeutralCount(3);
        question.setAgreeCount(4);
        question.setStronglyAgreeCount(5);

        question.setStronglyDisagreeCount(question.getStronglyDisagreeCount() + 1);
        question.setDisagreeCount(question.getDisagreeCount() + 1);
        question.setNeutralCount(question.getNeutralCount() + 1);
        question.setAgreeCount(question.getAgreeCount() + 1);
        question.setStronglyAgreeCount(question.getStronglyAgreeCount() + 1);

        assertEquals(2, question.getStronglyDisagreeCount());
        assertEquals(3, question.getDisagreeCount());
        assertEquals(4, question.getNeutralCount());
        assertEquals(5, question.getAgreeCount());
        assertEquals(6, question.getStronglyAgreeCount());
    }
}
