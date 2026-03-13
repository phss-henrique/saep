package senai.saep.estoque.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import senai.saep.estoque.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    List<Produto> findByNome(String nome);
    List<Produto> findAll();
}
