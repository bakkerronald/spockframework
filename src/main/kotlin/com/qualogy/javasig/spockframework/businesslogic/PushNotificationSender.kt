package com.qualogy.javasig.spockframework.businesslogic

import com.qualogy.javasig.spockframework.model.PushNotification

interface PushNotificationSender {

    fun send(pushNotification: PushNotification)
}