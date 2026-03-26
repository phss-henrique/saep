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

  novoEstoque: any = {
    quantidade: null,
    localizacao: '',
    produto: { sku: '' }
  };

  estoqueEditando: any = {
    quantidade: null,
    localizacao: '',
    produto: { sku: '' }
  };

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

  
  salvarEstoque() {
    if (!this.novoEstoque.produto.sku || !this.novoEstoque.quantidade) {
      alert('Por favor, preencha o SKU e a quantidade!');
      return;
    }

    this.api.criarEstoque(this.novoEstoque).subscribe({
      next: (resposta) => {
        this.carregarEstoque(); 
        this.novoEstoque = { quantidade: null, localizacao: '', produto: { sku: '' } };
      },
      error: (erro) => alert(`Falha ao registrar. Verifique se o SKU digitado realmente existe. ${erro}`)
    });
  }

  
  excluirEstoque(id: string) {
    if (confirm('Tem certeza que deseja excluir esta entrada de estoque?')) {
      this.api.deletarEstoque(id).subscribe({
        next: () => {
          alert('Estoque excluído com sucesso!');
          this.carregarEstoque();
        },
        error: (erro) => alert(`Erro ao excluir estoque. ${erro}`)
      });
    }
  }

  
  abrirModalEdicao(item: any) {
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
        this.fecharModal();
        this.carregarEstoque();
      },
      error: (erro) => alert(`Erro ao atualizar. Verifique se o SKU existe. ${erro}`)
    });
  }
}