package senai.saep.estoque.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import senai.saep.estoque.domain.Estoque;
import senai.saep.estoque.domain.Produto;
import senai.saep.estoque.repository.EstoqueRepository;
import senai.saep.estoque.repository.ProdutoRepository;

@RequiredArgsConstructor
@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    
    public List<Estoque> getTodosEstoque() {
        return estoqueRepository.findAll(); 
    }
    public Estoque atualizarEstoque(UUID id, Estoque dadosAtualizados) {
        
        Estoque estoqueExistente = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado!"));

        String skuBuscado = dadosAtualizados.getProduto().getSku();

        Produto produtoReal = produtoRepository.findBySku(skuBuscado);
        if (produtoReal == null) {
            throw new RuntimeException("Nenhum produto encontrado com o SKU " + skuBuscado);
        }

        estoqueExistente.setQuantidade(dadosAtualizados.getQuantidade());
        estoqueExistente.setLocalizacao(dadosAtualizados.getLocalizacao());
        
        estoqueExistente.setProduto(produtoReal);

        return estoqueRepository.save(estoqueExistente);
    }

    public void deletarEstoque(UUID id) {
        estoqueRepository.deleteById(id);
    }
}
