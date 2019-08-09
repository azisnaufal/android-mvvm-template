package com.google.samples.apps.sunflower.data

import com.google.samples.apps.sunflower.data.source.local.PlantAndGardenPlantings
import org.junit.Assert.assertTrue
import org.junit.Test

class PlantAndGardenPlantingTest {

    @Test fun test_default_values() {
        val p = PlantAndGardenPlantings()
        assertTrue(p.gardenPlantings.isEmpty())
    }
}