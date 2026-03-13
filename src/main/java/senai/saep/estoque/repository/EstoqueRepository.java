package senai.saep.estoque.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import senai.saep.estoque.domain.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, UUID> {
    List<Estoque> findByNome(String nome);
    List<Estoque> findAll();

}
