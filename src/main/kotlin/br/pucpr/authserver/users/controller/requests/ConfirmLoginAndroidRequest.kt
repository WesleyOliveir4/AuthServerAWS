package br.pucpr.authserver.users.controller.requests

import jakarta.validation.constraints.NotBlank
import java.util.*

data class ConfirmLoginAndroidRequest(
    @NotBlank
    val telephone: String?,

    @NotBlank
    val uuid: UUID?,

    @NotBlank
    val codeConfirmation: String?,

)
