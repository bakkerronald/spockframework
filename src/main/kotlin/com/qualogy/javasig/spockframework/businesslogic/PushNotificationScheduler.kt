package com.qualogy.javasig.spockframework.businesslogic

import com.qualogy.javasig.spockframework.model.PushNotification

interface PushNotificationScheduler {

    fun schedule(pushNotification: PushNotification)
}