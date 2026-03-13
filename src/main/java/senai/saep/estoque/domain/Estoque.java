package senai.saep.estoque.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="estoque")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    private String nome;
    private int quantidade;
    private String localizacao;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produtoId;
}
