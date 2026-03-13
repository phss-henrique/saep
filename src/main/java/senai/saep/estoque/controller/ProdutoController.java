package senai.saep.estoque.controller;


import org.springframework.web.bind.annotation.PostMapping;
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
}
