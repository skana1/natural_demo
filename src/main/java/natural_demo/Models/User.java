package natural_demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @AllArgsConstructor

@Table(name="sys_users")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iduser")
    private Long iduser;
    @Column(name = "nome")
    private String nome;
    @Column(name = "password")
    private String password;
    @Column(name = "dataBasedPremissionInfo")
    private String dataBasedPremissionInfo;
    @Column(name = "displayName")
    private String displayName;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
