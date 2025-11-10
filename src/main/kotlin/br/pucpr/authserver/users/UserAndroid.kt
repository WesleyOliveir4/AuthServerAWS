package br.pucpr.authserver.users


import br.pucpr.authserver.roles.Role
import jakarta.persistence.*
import java.util.UUID
@Entity
@Table(
    name = "TblUserAndroid",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["telephone"])
    ]
)
class UserAndroid(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var telephone: String = "",

    @Column
    var uuid: UUID? = null,

    @Column(nullable = false)
    var sms: String = "",

    @Column
    var userName: String? = null,

    @Column
    var userDescription: String? = null,

    @ManyToMany
    @JoinTable(
        name = "UuidRole",
        joinColumns = [JoinColumn(name = "idUserAndroid")],
        inverseJoinColumns = [JoinColumn(name = "idRole")]
    )
    val roles: MutableSet<Role> = mutableSetOf()
)