package senai.saep.estoque.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import senai.saep.estoque.domain.Usuario;
import senai.saep.estoque.repository.UsuarioRepository;


@RequiredArgsConstructor
@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getTodosUsuarios() {
        return usuarioRepository.findAll(); 
    }
    public Usuario criarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public Usuario getUsuarioById(UUID id){
        return usuarioRepository.findById(id).orElse(null);
    }
    public Usuario getUsuarioByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
}
