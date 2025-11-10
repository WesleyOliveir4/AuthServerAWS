package br.pucpr.authserver.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserAndroidRepository : JpaRepository<UserAndroid, Long> {
    fun findByTelephone(telephone: String): UserAndroid?

    @Query("select distinct u from UserAndroid u" +
            " join u.roles r" +
            " where r.name = :role" +
            " order by u.telephone")
    fun findByRole(role: String): List<UserAndroid>
}