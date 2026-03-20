import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Api } from '../../api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})

export class Login {
  email = '';
  senha = '';
  
  constructor(private api: Api, private router: Router){}

  fazerLogin(){
    const credentials = {
      email: this.email,
      senha: this.senha
    };
    this.api.login(credentials).subscribe({
      next: (response) => {
        localStorage.setItem('usuarioLogado', 'true');
        this.router.navigate(['/produtos']);
      },
      error: (erro) => {
        console.log('Erro no login:', erro);
        alert('Falha no login. Verifique suas credenciais e tente novamente.');
      }

    });
  }
}
