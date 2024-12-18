package com.icdominguez.icdominguez.memecreator.presentation

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
}