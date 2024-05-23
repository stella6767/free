package freeapp.life.stella.api.config.security


import freeapp.life.stella.storage.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*


class UserPrincipal(
    val user: User,
    val customAuthorities: MutableCollection<out GrantedAuthority> =
        Collections.singletonList(SimpleGrantedAuthority("ROLE_USER")),
) : OAuth2User, UserDetails {

//    var authorities: List<GrantedAuthority> =
//        Collections.singletonList(SimpleGrantedAuthority("ROLE_USER"))

    override fun getName(): String {
        return user.username
    }

    override fun getAttributes(): Map<String, Any?> {
        return attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {

        return customAuthorities
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String? {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun toString(): String {
        return "UserPrincipal(user=$user, customAuthorities=$customAuthorities)"
    }


}