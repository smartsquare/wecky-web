package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.lambda.invoke.LambdaFunction
import de.smartsquare.wecky.weckyweb.domain.Website

interface NotifyService {

    @LambdaFunction(functionName = "awscodestar-wecky-notify-lambd-TriggerNotification-3LF7X6LQ10LP")
    fun notifyUser(website: Website)
}