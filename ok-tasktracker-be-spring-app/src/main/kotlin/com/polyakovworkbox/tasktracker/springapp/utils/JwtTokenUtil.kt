package com.polyakovworkbox.tasktracker.springapp.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.stubs.TaskStub
import java.util.*

fun AuthConst.Companion.testUserToken(): String = testToken("TEST", "USER")

private fun testToken(vararg groups: String) = JWT.create()
    .withExpiresAt(
        GregorianCalendar().apply {
            set(2036, 0, 1, 0, 0, 0)
            timeZone = TimeZone.getTimeZone("UTC")
        }.time
    )
    .withAudience(AuthConst.TEST.audience)
    .withIssuer(AuthConst.TEST.issuer)
    .withClaim(AuthConst.ID_CLAIM, TaskStub.getModel().ownerId.asString())
    .withClaim(AuthConst.F_NAME_CLAIM, "Ivan")
    .withClaim(AuthConst.M_NAME_CLAIM, "I.")
    .withClaim(AuthConst.L_NAME_CLAIM, "Ivanov")
    .withArrayClaim(AuthConst.GROUPS_CLAIM, groups)
    .sign(Algorithm.HMAC256(AuthConst.TEST.secret))

data class AuthConst(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
) {

    companion object {
        const val ID_CLAIM = "id"
        const val GROUPS_CLAIM = "groups"
        const val F_NAME_CLAIM = "fname"
        const val M_NAME_CLAIM = "mname"
        const val L_NAME_CLAIM = "lname"

        val TEST = AuthConst(
            secret = "secret",
            issuer = "polyakovworkbox",
            audience = "tasktracker-users",
            realm = "Access to Tasks"
        )
    }
}