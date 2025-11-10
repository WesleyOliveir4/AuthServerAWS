package br.pucpr.authserver.users

import br.pucpr.authserver.exception.BadRequestException
import br.pucpr.authserver.exception.NotFoundException
import br.pucpr.authserver.roles.RoleRepository
import br.pucpr.authserver.security.Jwt
import br.pucpr.authserver.users.controller.responses.*
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.Random
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    val repositoryAndroid: UserAndroidRepository,
) {

    fun loginAndroid(telephone: String, uuid: UUID, userName : String): ResponseEntity<LoginAndroidResponse?>{
        val user = repositoryAndroid.findByTelephone(telephone)

        if (user != null) {
            if (user.uuid == uuid) {
//                Caso o telefone e uuid existam para um usuário ativo, faça o login e retorne o usuário.
                val userSave  = UserAndroid(
                    telephone = telephone,
                    uuid = uuid,
                    userName = userName,
                )
                repositoryAndroid.save(userSave)

                return ResponseEntity
                    .status(200)
                    .body (
                        LoginAndroidResponse(
                            user = UserAndroidResponse(
                                userSave
                            )
                        )
                    )
            } else {
                // SMS de confirmação
                val smsCode = Math.random().toString().takeLast(5)
                user.sms = smsCode

                repositoryAndroid.save(user)

                return ResponseEntity
                    .status(202)
                    .body (
                        LoginAndroidResponse(
                            user = UserAndroidResponse(
                                user
                            )
                        )
                    )
            }
        } else {
            // Criar novo e enviar SMS

            val userSave  = UserAndroid(
                telephone = telephone,
                userName = userName,
                sms = Math.random().toString().takeLast(5)
            )
            repositoryAndroid.save(userSave)

            return ResponseEntity
                .status(202)
                .body (
                    LoginAndroidResponse(
                        user = UserAndroidResponse(
                            userSave
                        )
                    )
                )
        }
    }

    fun confirmLoginAndroid(telephone: String, uuid: UUID, codeConfirmation : String): ResponseEntity<ConfirmLoginAndroidResponse?> {
        val user = repositoryAndroid.findByTelephone(telephone)
        if (user != null) {
            if(user.sms.isEmpty()) {
                //Usuario não tinha sms enviado para o telefone da request
                return ResponseEntity
                    .status(404)
                    .body (
                        ConfirmLoginAndroidResponse(
                            UserAndroidResponse(
                                user
                            )
                        )
                    )
            }else if(user.sms == codeConfirmation){
                //Sms da request é igual ao sms cadastrado para o telefone
                user.uuid = uuid
                user.sms = ""

                repositoryAndroid.save(user)

                return ResponseEntity
                    .status(200)
                    .body (
                        ConfirmLoginAndroidResponse(
                            UserAndroidResponse(
                                user
                            )
                        )
                    )
            }
            else{
                //Sms da request é diferente do cadastrado para o telefone

                return ResponseEntity
                    .status(404)
                    .body (
                        ConfirmLoginAndroidResponse(
                            UserAndroidResponse(
                                user
                            )
                        )
                    )
            }


        }else{
            //O telefone do usuario nunca foi cadastrado
            return ResponseEntity
                .status(404)
                .body (
                    ConfirmLoginAndroidResponse(
                        UserAndroidResponse(
                            UserAndroid()
                        )
                    )
                )
        }

    }

    fun putUserAndroid(telephone: String,userName: String? ,userDescription: String?): Boolean{
        val user = repositoryAndroid.findByTelephone(telephone)

        if (user != null){
            userName?.let {
                user.userName = it
            }
            userDescription?.let {
                user.userDescription = userDescription
            }
            repositoryAndroid.save(user)
            return true
        }else{
            return false
        }

    }

    fun getUserAndroid(telephone: String): GetUserAndroidResponse?{
        val user = repositoryAndroid.findByTelephone(telephone) ?: return null

        return GetUserAndroidResponse(
            user
        )
    }



    companion object {
        private val log = LoggerFactory.getLogger(UserService::class.java)
    }
}