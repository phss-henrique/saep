package senai.saep.estoque.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import senai.saep.estoque.domain.Produto;
import senai.saep.estoque.service.ProdutoService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    
    private final ProdutoService produtoService;

    @PostMapping("/criar")
    public Produto criarProduto(@RequestBody Produto produto) {
        return produtoService.criarProduto(produto);
    }

    @GetMapping("/listar")
    public List<Produto> getProdutos(){
        return produtoService.getTodosProdutos();
    }
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarProduto(@PathVariable UUID id, @RequestBody Produto produto) {
        try {
            produtoService.atualizarProduto(id, produto);
            return ResponseEntity.ok("Produto atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable UUID id) {
        try {
            produtoService.deletarProduto(id);
            return ResponseEntity.ok("Produto deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar: " + e.getMessage());
        }
    }

}
