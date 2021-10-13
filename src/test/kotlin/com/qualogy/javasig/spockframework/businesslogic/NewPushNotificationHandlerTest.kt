package com.qualogy.javasig.spockframework.businesslogic

import com.qualogy.javasig.spockframework.model.PushNotification
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Clock
import java.time.ZoneId

@ExtendWith(MockitoExtension::class)
internal class NewPushNotificationHandlerTest {

    @Mock
    private lateinit var pushNotificationSender: PushNotificationSender
    @Mock
    private lateinit var pushNotificationScheduler: PushNotificationScheduler

    @Test
    fun `quietTime is on the same day - should send if outside quiet time`() {
        val pushNotification = PushNotification(
            messageKey = "message.key",
            customerId = 1
        )
        val underTest = NewPushNotificationHandler(
            Clock.fixed(instant("07:15"), ZoneId.systemDefault()),
            pushNotificationSender,
            pushNotificationScheduler,
            time("01:00"),
            time("06:00")
        )

        underTest.newPushNotification(pushNotification)
        verify(pushNotificationScheduler, times(0)).schedule(pushNotification)
        verify(pushNotificationSender, times(1)).send(pushNotification)
    }

    @Test
    fun `quietTime is on the same day - should schedule if in quiet time`() {
        val pushNotification = PushNotification(
            messageKey = "message.key",
            customerId = 1
        )
        val underTest = NewPushNotificationHandler(
            Clock.fixed(instant("04:15"), ZoneId.systemDefault()),
            pushNotificationSender,
            pushNotificationScheduler,
            time("01:00"),
            time("06:00")
        )

        underTest.newPushNotification(pushNotification)
        verify(pushNotificationScheduler, times(1)).schedule(pushNotification)
        verify(pushNotificationSender, times(0)).send(pushNotification)
    }

    @Test
    fun `quietTime is on different days - should schedule if in quiet time on first day`() {
        val pushNotification = PushNotification(
            messageKey = "message.key",
            customerId = 1
        )
        val underTest = NewPushNotificationHandler(
            Clock.fixed(instant("23:15"), ZoneId.systemDefault()),
            pushNotificationSender,
            pushNotificationScheduler,
            time("23:00"),
            time("07:00")
        )

        underTest.newPushNotification(pushNotification)
        verify(pushNotificationScheduler, times(1)).schedule(pushNotification)
        verify(pushNotificationSender, times(0)).send(pushNotification)
    }

    @Test
    fun `quietTime is on different days - should schedule if in quiet time on second day`() {
        val pushNotification = PushNotification(
            messageKey = "message.key",
            customerId = 1
        )
        val underTest = NewPushNotificationHandler(
            Clock.fixed(instant("06:15"), ZoneId.systemDefault()),
            pushNotificationSender,
            pushNotificationScheduler,
            time("23:00"),
            time("07:00")
        )

        underTest.newPushNotification(pushNotification)
        verify(pushNotificationScheduler, times(1)).schedule(pushNotification)
        verify(pushNotificationSender, times(0)).send(pushNotification)
    }
}