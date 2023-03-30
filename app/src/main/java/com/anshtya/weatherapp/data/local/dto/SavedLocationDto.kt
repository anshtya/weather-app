package com.anshtya.weatherapp.data.local.dto

import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import com.anshtya.weatherapp.domain.model.SavedLocation


fun SavedLocationModel.toDomainModel(): SavedLocation {
    return SavedLocation(
        condition = condition,
        last_updated = last_updated,
        temp_c = temp_c,
        country = country,
        name = name,
        region = region
    )
}