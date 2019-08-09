package com.google.samples.apps.sunflower.data.source.local

import androidx.room.Embedded
import androidx.room.Relation
import com.google.samples.apps.sunflower.data.model.GardenPlanting
import com.google.samples.apps.sunflower.data.model.Plant

/**
 * This class captures the relationship between a [Plant] and a user's [GardenPlanting], which is
 * used by Room to fetch the related entities.
 */
class PlantAndGardenPlantings {

    @Embedded
    lateinit var plant: Plant

    @Relation(parentColumn = "id", entityColumn = "plant_id")
    var gardenPlantings: List<GardenPlanting> = arrayListOf()
}
