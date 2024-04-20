package ru.tinkoff.tinkoffer.presentation

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun fromEpochDate(ms: Long): String? {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val instant = Instant.ofEpochMilli(ms)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(formatter)
}