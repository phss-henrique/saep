package senai.saep.estoque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import senai.saep.estoque.domain.Usuario;
import senai.saep.estoque.service.UsuarioService;
import org.springframework.security.web.context.SecurityContextRepository;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) {
        
        // Cria o token com as credenciais enviadas pelo front
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                usuario.getEmail(), 
                usuario.getSenha()
        );

        // O Spring vai no banco de dados e verifica se a senha bate
        Authentication authentication = authenticationManager.authenticate(token);

        // Se a senha estiver correta, criamos o contexto de segurança
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // A MÁGICA DA SESSÃO: Salva o contexto. 
        // Isso faz o Spring enviar o cookie JSESSIONID automaticamente na resposta HTTP.
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok("Login realizado com sucesso!");
    }
    

    @PostMapping("/user/criar")
    public ResponseEntity<String> criarUser(
        @RequestBody Usuario usuarioDTO
    ) {
        if (usuarioService.getUsuarioByEmail(usuarioDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok("User created successfully");
    }
}
