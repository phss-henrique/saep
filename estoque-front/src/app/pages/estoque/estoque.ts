import { Component, OnInit, ElementRef, ViewChild, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Api } from '../../api';

@Component({
  selector: 'app-estoque',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './estoque.html',
  styleUrl: './estoque.css'
})
export class Estoque implements OnInit {
  
  listaEstoque: any[] = [];

  // Objeto para Criação
  novoEstoque: any = {
    quantidade: null,
    localizacao: '',
    produto: { sku: '' }
  };

  // Objeto para Edição (Modal)
  estoqueEditando: any = {
    quantidade: null,
    localizacao: '',
    produto: { sku: '' }
  };

  // Referência do Modal
  @ViewChild('modalEditar') modalEditar!: ElementRef<HTMLDialogElement>;

  constructor(private api: Api, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.carregarEstoque();
  }

  carregarEstoque() {
    this.api.listarEstoque().subscribe({
      next: (dados) => {
        this.listaEstoque = dados;
        this.cdr.detectChanges(); 
      },
      error: (erro) => console.error('Erro ao buscar estoque:', erro)
    });
  }

  // ==========================================
  // LÓGICA DE CRIAÇÃO
  // ==========================================
  salvarEstoque() {
    if (!this.novoEstoque.produto.sku || !this.novoEstoque.quantidade) {
      alert('Por favor, preencha o SKU e a quantidade!');
      return;
    }

    this.api.criarEstoque(this.novoEstoque).subscribe({
      next: (resposta) => {
        alert('Entrada de estoque registrada com sucesso!');
        this.carregarEstoque(); 
        this.novoEstoque = { quantidade: null, localizacao: '', produto: { sku: '' } };
      },
      error: (erro) => alert('Falha ao registrar. Verifique se o SKU digitado realmente existe.')
    });
  }

  // ==========================================
  // LÓGICA DE EXCLUSÃO
  // ==========================================
  excluirEstoque(id: string) {
    if (confirm('Tem certeza que deseja excluir esta entrada de estoque?')) {
      this.api.deletarEstoque(id).subscribe({
        next: () => {
          alert('Estoque excluído com sucesso!');
          this.carregarEstoque();
        },
        error: (err) => alert('Erro ao excluir estoque.')
      });
    }
  }

  // ==========================================
  // LÓGICA DE EDIÇÃO (MODAL)
  // ==========================================
  abrirModalEdicao(item: any) {
    // Copiamos os dados com cuidado por causa do objeto aninhado (produto.sku)
    this.estoqueEditando = { 
      id: item.id,
      quantidade: item.quantidade,
      localizacao: item.localizacao,
      produto: { sku: item.produto?.sku || '' } 
    };
    
    this.modalEditar.nativeElement.showModal();
  }

  fecharModal() {
    this.modalEditar.nativeElement.close();
  }

  salvarEdicao() {
    this.api.atualizarEstoque(this.estoqueEditando.id, this.estoqueEditando).subscribe({
      next: () => {
        alert('Estoque atualizado com sucesso!');
        this.fecharModal();
        this.carregarEstoque();
      },
      error: (erro) => alert('Erro ao atualizar. Verifique se o SKU existe.')
    });
  }
}