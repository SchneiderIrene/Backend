package de.leafgrow.leafgrow_project.security;

import de.leafgrow.leafgrow_project.domain.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class AuthInfo implements Authentication {

    private boolean authenticated;
    private String username;
    private Set<Role> roles;

    public AuthInfo(String username, Set<Role> roles) {
        this.username = username;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null; //don't use
    }

    @Override
    public Object getDetails() {
        return null; //don't use
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthInfo authInfo = (AuthInfo) o;

        if (authenticated != authInfo.authenticated) return false;
        if (!Objects.equals(username, authInfo.username)) return false;
        return Objects.equals(roles, authInfo.roles);
    }

    @Override
    public int hashCode() {
        int result = (authenticated ? 1 : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("AuthInfo: username - %s, roles - %s, authenticated - %s",
                username, roles, authenticated ? "yes" : "no");
    }
}
