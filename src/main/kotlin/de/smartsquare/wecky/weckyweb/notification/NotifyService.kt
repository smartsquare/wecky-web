package de.smartsquare.wecky.weckyweb.notification

import com.amazonaws.services.lambda.invoke.LambdaFunction
import de.smartsquare.wecky.weckyweb.domain.WebsiteChange

interface NotifyService {

    @LambdaFunction(functionName = "Wecky-NotifyUser")
    fun notifyUser(websiteChange: WebsiteChange)
}