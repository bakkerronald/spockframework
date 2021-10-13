package com.qualogy.javasig.spockframework.businesslogic

import com.qualogy.javasig.spockframework.model.PushNotification
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalTime

@Service
class NewPushNotificationHandler(
    private val clock: Clock,
    private val pushNotificationSender: PushNotificationSender,
    private val pushNotificationScheduler: PushNotificationScheduler,
    private val quietTimeStart: LocalTime,
    private val quietTimeEnd: LocalTime,
) {

    fun newPushNotification(pushNotification: PushNotification) {

        if (quietTime()) {
            pushNotificationScheduler.schedule(pushNotification)
        } else {
            pushNotificationSender.send(pushNotification)
        }
    }

    private fun quietTime(): Boolean {
        val currentTime = LocalTime.now(clock)
        return if (quietTimeStart < quietTimeEnd) {
            currentTime.isAfter(quietTimeStart) && currentTime.isBefore(quietTimeEnd)
        } else {
            currentTime.isAfter(quietTimeStart) || currentTime.isBefore(quietTimeEnd)
        }
    }

}