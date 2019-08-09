package com.google.samples.apps.sunflower.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.samples.apps.sunflower.data.model.Plant
import com.google.samples.apps.sunflower.data.source.local.AppDatabase
import com.google.samples.apps.sunflower.data.source.local.PlantDao
import com.google.samples.apps.sunflower.utilities.getValue
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlantDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var plantDao: PlantDao
    private val plantA = Plant("1", "A", "", 1, 1, "")
    private val plantB = Plant("2", "B", "", 1, 1, "")
    private val plantC = Plant("3", "C", "", 2, 2, "")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        plantDao = database.plantDao()

        // Insert plants in non-alphabetical order to test that results are sorted by name
        plantDao.insertAll(listOf(plantB, plantC, plantA))
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetPlants() {
        val plantList = getValue(plantDao.getPlants())
        assertThat(plantList.size, equalTo(3))

        // Ensure plant list is sorted by name
        assertThat(plantList[0], equalTo(plantA))
        assertThat(plantList[1], equalTo(plantB))
        assertThat(plantList[2], equalTo(plantC))
    }

    @Test fun testGetPlantsWithGrowZoneNumber() {
        val plantList = getValue(plantDao.getPlantsWithGrowZoneNumber(1))
        assertThat(plantList.size, equalTo(2))
        assertThat(getValue(plantDao.getPlantsWithGrowZoneNumber(2)).size, equalTo(1))
        assertThat(getValue(plantDao.getPlantsWithGrowZoneNumber(3)).size, equalTo(0))

        // Ensure plant list is sorted by name
        assertThat(plantList[0], equalTo(plantA))
        assertThat(plantList[1], equalTo(plantB))
    }

    @Test fun testGetPlant() {
        assertThat(getValue(plantDao.getPlant(plantA.plantId)), equalTo(plantA))
    }
}