package senai.saep.estoque.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import senai.saep.estoque.domain.Estoque;
import senai.saep.estoque.domain.Produto;
import senai.saep.estoque.repository.EstoqueRepository;
import senai.saep.estoque.repository.ProdutoRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    @GetMapping("/listar")
    public List<Estoque> listarEstoque() {
        return estoqueRepository.findAll();
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarEstoque(@RequestBody Estoque estoque) {
    String skuBuscado = estoque.getProduto().getSku(); 
    
    Produto produtoRealCompleto = produtoRepository.findBySku(skuBuscado);

    
    estoque.setProduto(produtoRealCompleto);

    
    estoqueRepository.save(estoque);

    return ResponseEntity.ok("Estoque salvo com sucesso e linkado ao produto!");
    }

}
