package senai.saep.estoque.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import senai.saep.estoque.domain.Estoque;
import senai.saep.estoque.repository.EstoqueRepository;

@RequiredArgsConstructor
@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;
    
    public List<Estoque> getTodosEstoque() {
        return estoqueRepository.findAll(); 
    }
}
