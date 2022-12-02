package de.davidkoehlmann.ecommerceapplicationbackend.account;

import de.davidkoehlmann.ecommerceapplicationbackend.cart.Cart;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@Table(name = "Account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Enumerated(EnumType.STRING)
    private AccountRole role;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonLocked;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);
        return List.of(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonLocked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountNonExpired != account.accountNonExpired) return false;
        if (accountNonLocked != account.accountNonLocked) return false;
        if (credentialsNonLocked != account.credentialsNonLocked) return false;
        if (enabled != account.enabled) return false;
        if (!Objects.equals(id, account.id)) return false;
        if (!Objects.equals(username, account.username)) return false;
        if (!Objects.equals(password, account.password)) return false;
        if (!Objects.equals(cart, account.cart)) return false;
        return role == account.role;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (cart != null ? cart.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (accountNonExpired ? 1 : 0);
        result = 31 * result + (accountNonLocked ? 1 : 0);
        result = 31 * result + (credentialsNonLocked ? 1 : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}
