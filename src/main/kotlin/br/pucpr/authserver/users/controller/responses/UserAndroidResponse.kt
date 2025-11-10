package br.pucpr.authserver.users.controller.responses

import br.pucpr.authserver.users.UserAndroid
import java.util.UUID

data class UserAndroidResponse(
    val telephone: String,
    val uuid: UUID?,
    val userName: String?,
    val sms: String,
) {
    constructor(user: UserAndroid) : this(
        user.telephone,
        user.uuid,
        user.userName,
        user.sms
    )
}