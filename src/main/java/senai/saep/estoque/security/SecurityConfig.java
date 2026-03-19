package senai.saep.estoque.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import senai.saep.estoque.domain.Usuario;
import senai.saep.estoque.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            
            // 1. Desligamos o CSRF para facilitar o teste no Postman
            .csrf(csrf -> csrf.disable()) 
            
            // 2. Liberamos as rotas do seu Controller
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login", "/api/user/criar").permitAll() 
                .anyRequest().authenticated()                  
            )
            
            // 3. Ensina o Spring a salvar a sessão gerada pelo Controller
            .securityContext(context -> context
                .securityContextRepository(securityContextRepository()) 
            );

        return http.build();
    }

    // ============================================================================================
    // BEANS DE FERRAMENTAS DO SPRING SECURITY (O Controller e o Manager precisam deles)
    // ============================================================================================

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    // ============================================================================================
    // A PEÇA QUE FALTAVA: Ensina o Spring a buscar o usuário no banco para o Login
    // ============================================================================================
    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        return email -> {
            // Busca o usuário no seu banco de dados
            Usuario usuario = usuarioService.getUsuarioByEmail(email);
            
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
            }
            
            // Converte para o formato que o Spring Security entende
            return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha()) // A senha que o Spring vai comparar com a que veio no POST
                .roles("USER") 
                .build();
        };
    }
}