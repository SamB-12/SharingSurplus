package com.example.sharingsurplus.data.model

import com.example.sharingsurplus.data.states.dashboard.produce.ProduceStatus
import com.example.sharingsurplus.data.states.dashboard.produce.ProduceType

data class Produce(
    val produceId: String = "",
    val ownerId: String = "",
    val producerName: String = "",
    val produceName: String = "",
    val produceDescription: String = "",
    val produceType: ProduceType? = ProduceType.None,
    val produceQuantity: Int = 0,
    val produceUnit: String = "",
    val produceBestBeforeDate: String = "",
    val producePickupInstructions: String = "",
    val produceLocation: String = "",
    val produceLatitude: Double = 0.0,
    val produceLongitude: Double = 0.0,
    val produceImageUrl: String = "",
    val produceStatus: ProduceStatus = ProduceStatus.Available,
    val createdAt : Long = System.currentTimeMillis()
)
