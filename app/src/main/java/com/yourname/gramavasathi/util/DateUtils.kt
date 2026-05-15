package com.yourname.gramavasathi.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    fun toString(date: LocalDate): String = date.format(formatter)

    fun fromString(dateStr: String): LocalDate =
        LocalDate.parse(dateStr, formatter)

    fun toDisplayString(dateStr: String): String {
        return try {
            LocalDate.parse(dateStr, formatter).format(displayFormatter)
        } catch (e: Exception) {
            dateStr
        }
    }

    fun nightsBetween(checkIn: LocalDate, checkOut: LocalDate): Int =
        ChronoUnit.DAYS.between(checkIn, checkOut).toInt()

    fun getDatesInRange(checkIn: LocalDate, checkOut: LocalDate): List<String> {
        val dates = mutableListOf<String>()
        var current = checkIn
        // Guard against infinite loops
        if (checkIn.isAfter(checkOut)) return emptyList()
        
        var count = 0
        while (!current.isAfter(checkOut) && count < 365) {
            dates.add(toString(current))
            current = current.plusDays(1)
            count++
        }
        return dates
    }
}