package com.google.samples.apps.sunflower.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*
import java.util.Calendar.DAY_OF_YEAR

@Entity(tableName = "plants")
data class Plant(
        @PrimaryKey @ColumnInfo(name = "id") @field:Json(name = "plantId") val plantId: String,
        @field:Json(name = "name") val name: String,
        @field:Json(name = "description") val description: String,
        @field:Json(name = "growZoneNumber") val growZoneNumber: Int,
        @field:Json(name = "wateringInterval") val wateringInterval: Int = 7, // how often the plant should be watered, in days
        @field:Json(name = "imageUrl") val imageUrl: String = ""
) {

    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     * This function is used in unit test. but I deleted it :D
     */
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        since > lastWateringDate.apply { add(DAY_OF_YEAR, wateringInterval) }

    override fun toString() = name
}
