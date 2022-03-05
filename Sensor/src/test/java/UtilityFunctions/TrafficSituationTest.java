package UtilityFunctions;

import sensor.TrafficSituation;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrafficSituationTest {

    @org.junit.jupiter.api.Test
    void getTrafficSituationfr() {
        assertEquals(TrafficSituation.getTrafficSituation(0), "NO TRAFFIC");
    }
    @org.junit.jupiter.api.Test
    void getTrafficSituationma() {
        assertEquals(TrafficSituation.getTrafficSituation(1), "LOW TRAFFIC");
    }

    @org.junit.jupiter.api.Test
    void getTrafficSituationst() {
        assertEquals(TrafficSituation.getTrafficSituation(2), "HIGH TRAFFIC");
    }

@org.junit.jupiter.api.Test
    void getTrafficSituationsta() {
        assertEquals(TrafficSituation.getTrafficSituation(3), "JAM");
    }
}