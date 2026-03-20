import { CommonModule } from '@angular/common';
import { Component, OnInit, ElementRef, ViewChild, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Api } from '../../api';

@Component({
  selector: 'app-produtos',
  standalone: true, 
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './produtos.html',
  styleUrl: './produtos.css',
})
export class Produtos implements OnInit {
  listaProdutos: any[] = [];
  
  // Objeto para Criação
  novoProduto: any = {
    nome: '',
    sku: '',
    material: '',
    categoria: '',
    tamanho: null,
    peso: null
  };

  // Objeto para Edição (Modal)
  produtoEditando: any = {};

  // Pega a referência do modal lá do HTML
  @ViewChild('modalEditar') modalEditar!: ElementRef<HTMLDialogElement>;

  constructor(private api: Api, private router: Router, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.carregarProdutos();
  }

  carregarProdutos() {
    this.api.listarProdutos().subscribe({
      next: (produtos) => {
        this.listaProdutos = produtos;
        this.cdr.detectChanges();
      },
      error: (erro) => {
        console.log('Erro ao carregar produtos:', erro);
        alert('Não foi possível carregar os produtos. Tente novamente mais tarde.');
      }
    });
  }

  // ==========================================
  // LÓGICA DE CRIAÇÃO
  // ==========================================
  salvarProduto() {
    if (!this.novoProduto.nome || !this.novoProduto.sku) {
      alert('Nome e SKU são obrigatórios!');
      return;
    }

    this.api.criarProduto(this.novoProduto).subscribe({
      next: (response) => {
        alert('Produto criado com sucesso!');
        this.carregarProdutos();
        // Limpa o formulário de criação
        this.novoProduto = {nome:'', sku:'', material:'', categoria:'', tamanho:null, peso:null};
      },
      error: (erro) => {
        console.error('Erro ao criar produto:', erro);
        alert('Falha ao criar produto. Verifique os dados e tente novamente.');
      }
    });
  }

  // ==========================================
  // LÓGICA DE EXCLUSÃO
  // ==========================================
  excluirProduto(id: string) {
    if (confirm('Tem certeza que deseja excluir este produto?')) {
      this.api.deletarProduto(id).subscribe({
        next: () => {
          alert('Produto excluído!');
          this.carregarProdutos();
        },
        error: (err) => alert('Erro ao excluir produto.')
      });
    }
  }

  // ==========================================
  // LÓGICA DE EDIÇÃO (MODAL)
  // ==========================================
  abrirModalEdicao(produto: any) {
    this.produtoEditando = { ...produto }; // Copia os dados pro modal
    this.modalEditar.nativeElement.showModal(); // Abre o modal do DaisyUI
  }

  fecharModal() {
    this.modalEditar.nativeElement.close(); // Fecha o modal
  }

  salvarEdicao() {
    this.api.atualizarProduto(this.produtoEditando.id, this.produtoEditando).subscribe({
      next: () => {
        alert('Produto atualizado com sucesso!');
        this.fecharModal();
        this.carregarProdutos();
      },
      error: (erro) => alert('Erro ao atualizar produto.')
    });
  }
}