package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SystemResultTest {

    @Test
    void testSystemResultConstructor() {
        SystemResult systemResult = new SystemResult();

        assertNull(systemResult.getId());
        assertNull(systemResult.getDimensions());
        assertNull(systemResult.getDimensionsAverage());
    }

//    @Test
//    void testSettersAndGetters() {
//        SystemResult systemResult = new SystemResult();
//
//        systemResult.setId(1L);
//
//        List<String> dimensions = new ArrayList<>();
//        dimensions.add("Dimension 1");
//        dimensions.add("Dimension 2");
//        systemResult.setDimensions(dimensions);
//
//        List<Double> dimensionsAverage = new ArrayList<>();
//        dimensionsAverage.add((double) 85L);
//        dimensionsAverage.add((double) 90L);
//        systemResult.setDimensionsAverage(dimensionsAverage);
//
//        assertEquals(1L, systemResult.getId());
//        assertEquals(2, systemResult.getDimensions().size());
//        assertTrue(systemResult.getDimensions().contains("Dimension 1"));
//        assertTrue(systemResult.getDimensions().contains("Dimension 2"));
//        assertEquals(2, systemResult.getDimensionsAverage().size());
//        assertTrue(systemResult.getDimensionsAverage().contains(85L));
//        assertTrue(systemResult.getDimensionsAverage().contains(90L));
//    }

    @Test
    void testEmptyDimensionsList() {
        SystemResult systemResult = new SystemResult();
        systemResult.setDimensions(new ArrayList<>());

        assertNotNull(systemResult.getDimensions());
        assertTrue(systemResult.getDimensions().isEmpty());
    }

    @Test
    void testEmptyDimensionsAverageList() {
        SystemResult systemResult = new SystemResult();
        systemResult.setDimensionsAverage(new ArrayList<>());

        assertNotNull(systemResult.getDimensionsAverage());
        assertTrue(systemResult.getDimensionsAverage().isEmpty());
    }

    @Test
    void testAddDimensionAndAverage() {
        SystemResult systemResult = new SystemResult();

        List<String> dimensions = new ArrayList<>();
        dimensions.add("Dimension A");
        systemResult.setDimensions(dimensions);

        List<Double> dimensionsAverage = new ArrayList<>();
        dimensionsAverage.add((double) 75L);
        systemResult.setDimensionsAverage(dimensionsAverage);

        systemResult.getDimensions().add("Dimension B");
        systemResult.getDimensionsAverage().add((double) 80L);

        assertEquals(2, systemResult.getDimensions().size());
        assertEquals(2, systemResult.getDimensionsAverage().size());
        assertEquals("Dimension B", systemResult.getDimensions().get(1));
        assertEquals(80L, systemResult.getDimensionsAverage().get(1));
    }
}
