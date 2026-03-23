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
        try {
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                    usuario.getEmail(), 
                    usuario.getSenha()
            );

            Authentication authentication = authenticationManager.authenticate(token);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);

            return ResponseEntity.ok("Login realizado com sucesso!");
            
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body("Erro: E-mail ou senha incorretos!");
        }
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
