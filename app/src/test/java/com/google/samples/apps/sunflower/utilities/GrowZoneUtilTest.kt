package com.google.samples.apps.sunflower.utilities

import org.junit.Assert.assertEquals
import org.junit.Test

class GrowZoneUtilTest {

    @Test fun getZoneForLatitude() {
        assertEquals(13, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(0.0))
        assertEquals(13, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(7.0))
        assertEquals(12, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(7.1))
        assertEquals(1, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(84.1))
        assertEquals(1, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(90.0))
    }

    @Test fun getZoneForLatitude_negativeLatitudes() {
        assertEquals(13, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(-7.0))
        assertEquals(12, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(-7.1))
        assertEquals(1, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(-84.1))
        assertEquals(1, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(-90.0))
    }

    // Bugfix test for https://github.com/googlesamples/android-sunflower/issues/8
    @Test fun getZoneForLatitude_GitHub_issue8() {
        assertEquals(9, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(35.0))
        assertEquals(8, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(42.0))
        assertEquals(7, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(49.0))
        assertEquals(6, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(56.0))
        assertEquals(5, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(63.0))
        assertEquals(4, com.google.samples.apps.sunflower.utilities.helper.getZoneForLatitude(70.0))
    }
}