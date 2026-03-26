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
import senai.saep.estoque.domain.Estoque;
import senai.saep.estoque.domain.Produto;
import senai.saep.estoque.repository.ProdutoRepository;
import senai.saep.estoque.service.EstoqueService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    private final EstoqueService estoqueService;
    private final ProdutoRepository produtoRepository;

    @GetMapping("/listar")
    public List<Estoque> listarEstoque() {
        return estoqueService.getTodosEstoque();
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarEstoque(@RequestBody Estoque estoque) {
        String skuBuscado = estoque.getProduto().getSku();

        Produto produtoRealCompleto = produtoRepository.findBySku(skuBuscado);

        estoqueService.criarEstoque(estoque, produtoRealCompleto);

        return ResponseEntity.ok("Estoque salvo com sucesso e linkado ao produto!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarEstoque(@PathVariable UUID id, @RequestBody Estoque estoque) {
        try {
            estoqueService.atualizarEstoque(id, estoque);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarEstoque(@PathVariable UUID id) {
        try {
            estoqueService.deletarEstoque(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
