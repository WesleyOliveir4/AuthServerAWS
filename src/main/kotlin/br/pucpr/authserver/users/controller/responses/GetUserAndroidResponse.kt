package br.pucpr.authserver.users.controller.responses

import br.pucpr.authserver.users.UserAndroid
import java.util.UUID

data class GetUserAndroidResponse(
    val telephone: String,
    val uuid: UUID?,
    val sms: String,
    val userName: String?,
    val userDescription: String?,
) {
    constructor(user: UserAndroid) : this(
        user.telephone,
        user.uuid,
        user.sms,
        user.userName,
        user.userDescription
    )
}