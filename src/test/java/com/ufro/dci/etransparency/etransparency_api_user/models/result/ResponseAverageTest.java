package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class ResponseAverageTest {

    @Test
    void testResponseAverageConstructor() {
        ResponseAverage responseAverage = new ResponseAverage();

        assertNull(responseAverage.getCompletionPercentage());
        assertNull(responseAverage.getPeopleAnswered());
        assertNull(responseAverage.getTotalPeopleAnswered());
        assertNull(responseAverage.getRealAverage());
        assertNull(responseAverage.getIdealAverage());
        assertNull(responseAverage.getDate());
    }

    @Test
    void testAllArgsConstructor() {
        Date date = new Date();
        ResponseAverage responseAverage = new ResponseAverage(75.0, 50L, 100L, 85L, 90L, date);

        assertEquals(75.0, responseAverage.getCompletionPercentage());
        assertEquals(50L, responseAverage.getPeopleAnswered());
        assertEquals(100L, responseAverage.getTotalPeopleAnswered());
        assertEquals(85L, responseAverage.getRealAverage());
        assertEquals(90L, responseAverage.getIdealAverage());
        assertEquals(date, responseAverage.getDate());
    }

    @Test
    void testSettersAndGetters() {
        ResponseAverage responseAverage = new ResponseAverage();

        responseAverage.setCompletionPercentage(80.0);
        responseAverage.setPeopleAnswered(40L);
        responseAverage.setTotalPeopleAnswered(80L);
        responseAverage.setRealAverage(70L);
        responseAverage.setIdealAverage(85L);
        Date date = new Date();
        responseAverage.setDate(date);

        assertEquals(80.0, responseAverage.getCompletionPercentage());
        assertEquals(40L, responseAverage.getPeopleAnswered());
        assertEquals(80L, responseAverage.getTotalPeopleAnswered());
        assertEquals(70L, responseAverage.getRealAverage());
        assertEquals(85L, responseAverage.getIdealAverage());
        assertEquals(date, responseAverage.getDate());
    }

    @Test
    void testCompletionPercentageIsNull() {
        ResponseAverage responseAverage = new ResponseAverage();
        assertNull(responseAverage.getCompletionPercentage());
    }

    @Test
    void testDateSetting() {
        ResponseAverage responseAverage = new ResponseAverage();
        Date currentDate = new Date();
        responseAverage.setDate(currentDate);

        assertNotNull(responseAverage.getDate());
        assertEquals(currentDate, responseAverage.getDate());
    }
}
