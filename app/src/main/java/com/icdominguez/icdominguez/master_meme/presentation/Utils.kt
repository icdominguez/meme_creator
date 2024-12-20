package com.icdominguez.icdominguez.master_meme.presentation

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

object Utils {
    fun generateRandomNumber(generatedNumbers: List<Int>): Int {
        val range = 1..10000
        val randomNumber = Random.nextInt(range.first, range.last)

        return if(generatedNumbers.contains(randomNumber)) {
            generateRandomNumber(generatedNumbers)
        } else {
            randomNumber
        }
    }

    fun localDateTimeToCalendar(localDateTime: LocalDateTime): Calendar {
        val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        val date = Date.from(instant)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }
}