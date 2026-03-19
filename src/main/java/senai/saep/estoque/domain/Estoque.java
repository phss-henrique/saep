package senai.saep.estoque.domain;

import java.util.UUID;

import jakarta.persistence.*;
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
    private UUID id;
    private int quantidade;
    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
