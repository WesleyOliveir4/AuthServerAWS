package br.pucpr.authserver.users.controller

import br.pucpr.authserver.exception.ForbiddenException
import br.pucpr.authserver.security.UserToken
import br.pucpr.authserver.users.SortDir
import br.pucpr.authserver.users.UserService
import br.pucpr.authserver.users.controller.requests.*
import br.pucpr.authserver.users.controller.responses.UserResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val service: UserService) {

    @PostMapping("/login/android")
    fun loginAndroid(@Valid @RequestBody login: LoginAndroidRequest) =
        service.loginAndroid(login.telephone!!, login.uuid!!, login.userName!!)

    @PostMapping("/login/confirm")
    fun confirmLogin(@Valid @RequestBody confirmLogin: ConfirmLoginAndroidRequest) =
        service.confirmLoginAndroid(confirmLogin.telephone!!, confirmLogin.uuid!!, confirmLogin.codeConfirmation!!)

    @PutMapping("/{telephone}")
    fun delete(@PathVariable telephone: String, userName: String?, userDescription: String?): ResponseEntity<Void> =
        if (service.putUserAndroid(telephone,userName,userDescription)) ResponseEntity.ok().build()
        else ResponseEntity.notFound().build()

    @GetMapping("/fetch/{telephone}")
    fun fetchUsers(@PathVariable telephone: String) =
        service.getUserAndroid(telephone)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}