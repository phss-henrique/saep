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
  
  novoProduto: any = {
    nome: '',
    sku: '',
    material: '',
    categoria: '',
    tamanho: null,
    peso: null
  };

  produtoEditando: any = {};

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

  
  salvarProduto() {
    if (!this.novoProduto.nome || !this.novoProduto.sku) {
      alert('Nome e SKU são obrigatórios!');
      return;
    }

    this.api.criarProduto(this.novoProduto).subscribe({
      next: (response) => {
        alert('Produto criado com sucesso!');
        this.carregarProdutos();
        this.novoProduto = {nome:'', sku:'', material:'', categoria:'', tamanho:null, peso:null};
      },
      error: (erro) => {
        console.error('Erro ao criar produto:', erro);
        alert('Falha ao criar produto. Verifique os dados e tente novamente.');
      }
    });
  }

  
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

  
  abrirModalEdicao(produto: any) {
    this.produtoEditando = { ...produto }; 
    this.modalEditar.nativeElement.showModal();
  }

  fecharModal() {
    this.modalEditar.nativeElement.close(); 
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