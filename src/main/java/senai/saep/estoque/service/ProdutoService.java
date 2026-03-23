package senai.saep.estoque.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import senai.saep.estoque.domain.Produto;
import senai.saep.estoque.repository.ProdutoRepository;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> getTodosProdutos() {
        return produtoRepository.findAll(); 
    }
    public Produto criarProduto(Produto produto){
        return produtoRepository.save(produto);
    }
    public Produto atualizarProduto(UUID id, Produto dadosAtualizados) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        produtoExistente.setNome(dadosAtualizados.getNome());
        produtoExistente.setSku(dadosAtualizados.getSku());
        produtoExistente.setMaterial(dadosAtualizados.getMaterial());
        produtoExistente.setCategoria(dadosAtualizados.getCategoria());
        produtoExistente.setTamanho(dadosAtualizados.getTamanho());
        produtoExistente.setPeso(dadosAtualizados.getPeso());

        return produtoRepository.save(produtoExistente);
    }
    public void deletarProduto(UUID id) {
        produtoRepository.deleteById(id);
    }
}
