package br.pucpr.authserver.users.controller.requests

import jakarta.validation.constraints.NotBlank
import java.util.*

data class LoginAndroidRequest(
    @NotBlank
    val telephone: String?,

    @NotBlank
    val userName: String?,

    @NotBlank
    val uuid: UUID?
)
