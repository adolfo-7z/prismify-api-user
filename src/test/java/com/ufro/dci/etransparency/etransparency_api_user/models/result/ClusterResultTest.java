package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ClusterResultTest {

    @Test
    void testClusterResultConstructor() {
        ClusterResult clusterResult = new ClusterResult();

        assertNull(clusterResult.getDimensionName());
        assertEquals(null, clusterResult.getCentroid());
        assertNull(clusterResult.getPoints());
        assertEquals(0, clusterResult.getWeight());
    }

    @SuppressWarnings("unused")
    @Test
    void testAllArgsConstructor() {
        List<String> points = List.of("1.2", "3.4", "5.6");
        ClusterResult clusterResult = new ClusterResult();
        clusterResult.setDimensionName("Dimension A");
        clusterResult.setCentroid("4.5");
        clusterResult.setPoints(points);
        clusterResult.setWeight(10);
        ProcessResult processResult = new ProcessResult();
        assertEquals("Dimension A", clusterResult.getDimensionName());
        assertEquals("4.5", clusterResult.getCentroid());
        assertEquals(points, clusterResult.getPoints());
        assertEquals(10, clusterResult.getWeight());
    }

    @SuppressWarnings("unused")
    @Test
    void testSettersAndGetters() {
        ClusterResult clusterResult = new ClusterResult();
        clusterResult.setDimensionName("Dimension B");
        clusterResult.setCentroid("3.5");
        clusterResult.setPoints(List.of("1.0", "2.0", "3.0"));
        clusterResult.setWeight(5);
        ProcessResult processResult = new ProcessResult();
        assertEquals("Dimension B", clusterResult.getDimensionName());
        assertEquals("3.5", clusterResult.getCentroid());
        assertEquals(3, clusterResult.getPoints().size());
        assertEquals(5, clusterResult.getWeight());
    }

    @Test
    void testPointsListOperations() {
        ClusterResult clusterResult = new ClusterResult();
        clusterResult.setPoints(new ArrayList<>());
        clusterResult.getPoints().add("2.5");
        assertEquals(1, clusterResult.getPoints().size());
        assertEquals("2.5", clusterResult.getPoints().get(0));
        clusterResult.getPoints().remove(0);
        assertTrue(clusterResult.getPoints().isEmpty());
    }

    @Test
    void testDimensionNameSetterAndGetter() {
        ClusterResult clusterResult = new ClusterResult();
        clusterResult.setDimensionName("Test Dimension");
        assertEquals("Test Dimension", clusterResult.getDimensionName());
    }

    @Test
    void testCentroidSetterAndGetter() {
        ClusterResult clusterResult = new ClusterResult();
        clusterResult.setCentroid("7.8");
        assertEquals("7.8", clusterResult.getCentroid());
    }

    @Test
    void testWeightSetterAndGetter() {
        ClusterResult clusterResult = new ClusterResult();
        clusterResult.setWeight(15);
        assertEquals(15, clusterResult.getWeight());
    }

}
