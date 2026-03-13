package senai.saep.estoque.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.util.UUID;


@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;

}

// import java.util.Collection;
// import java.util.List;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// public class Usuario implements UserDetails{
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     private UUID ID;
//     private String nome;
//     @Column(unique = true)
//     private String email;
//     private String senha;



//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
    
//         return List.of(new SimpleGrantedAuthority("ROLE_USER"));
//     }

//     @Override
//     public String getUsername() {
//         return email;
//     }
//     @Override
//     public String getPassword() {
//         return senha;
//     }
//     @Override
//     public boolean isAccountNonExpired() {
//         return true; 
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true; 
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true; 
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }
// }
