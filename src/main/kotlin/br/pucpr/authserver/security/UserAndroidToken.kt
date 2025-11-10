package br.pucpr.authserver.security

import br.pucpr.authserver.users.User
import br.pucpr.authserver.users.UserAndroid
import com.fasterxml.jackson.annotation.JsonIgnore

data class UserAndroidToken(
    val id: Long,
    val telephone: String,
    val roles: Set<String>
) {
    constructor(): this(0, "", setOf())
    constructor(userAndroid: UserAndroid): this(
        id = userAndroid.id!!,
        telephone = userAndroid.telephone,
        roles = userAndroid.roles.map { it.name }.toSortedSet()
    )

    @get:JsonIgnore
    val isAdmin: Boolean get() = "ADMIN" in roles
}
