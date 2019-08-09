package com.google.samples.apps.sunflower.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

@Entity(tableName = "plants")
data class Plant(
        @PrimaryKey @ColumnInfo(name = "id") @field:SerializedName("plantId") val plantId: String,
        @field:SerializedName("name") val name: String,
        @field:SerializedName("description") val description: String,
        @field:SerializedName("growZoneNumber") val growZoneNumber: Int,
        @field:SerializedName("wateringInterval") val wateringInterval: Int = 7, // how often the plant should be watered, in days
        @field:SerializedName("imageUrl") val imageUrl: String = ""
) {

    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     */
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        since > lastWateringDate.apply { add(DAY_OF_YEAR, wateringInterval) }

    override fun toString() = name
}
