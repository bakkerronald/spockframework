package com.qualogy.javasig.spockframework.businesslogic

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun time(text: String): LocalTime = LocalTime.parse(text)
fun dateTime(timeText: String): LocalDateTime = LocalDateTime.of(LocalDate.now(), time(timeText))
fun instant(localDateTime: LocalDateTime): Instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
fun instant(timeText: String): Instant = instant(dateTime(timeText))
