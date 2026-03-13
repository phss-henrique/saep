package senai.saep.estoque.service;

import java.util.List;

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
    
}
