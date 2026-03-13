package senai.saep.estoque.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import senai.saep.estoque.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    public List<Usuario> findByNome(String nome);
    public List<Usuario> findAll();
    public Usuario findByEmail(String email);
}
