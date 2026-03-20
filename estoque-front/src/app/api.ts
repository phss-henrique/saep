import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Api {
  private baseUrl = 'http://localhost:8080';

  private httpOptions = {
    withCredentials: true,
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient){}

  login(user:any):Observable<any>{
    return this.http.post(`${this.baseUrl}/api/login`, user, {...this.httpOptions, responseType: 'text'});
  }
  listarProdutos(): Observable<any[]>{
    return this.http.get<any[]>(`${this.baseUrl}/produtos/listar`, this.httpOptions);
  }
  criarProduto(produto:any): Observable<any>{
    return this.http.post(`${this.baseUrl}/produtos/criar`, produto, this.httpOptions);
  }
  listarEstoque(): Observable<any[]>{
    return this.http.get<any[]>(`${this.baseUrl}/estoque/listar`, this.httpOptions);
  }
  criarEstoque(estoque:any): Observable<any>{
    return this.http.post(`${this.baseUrl}/estoque/criar`, estoque, {...this.httpOptions, responseType: 'text'});
  }
  

  atualizarProduto(id: string, produto: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/produtos/atualizar/${id}`, produto, { ...this.httpOptions, responseType: 'text' });
  }

  deletarProduto(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/produtos/deletar/${id}`, { ...this.httpOptions, responseType: 'text' });
  }

  atualizarEstoque(id: string, estoque: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/estoque/atualizar/${id}`, estoque, { ...this.httpOptions, responseType: 'text' });
  }

  deletarEstoque(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/estoque/deletar/${id}`, { ...this.httpOptions, responseType: 'text' });
  }
}

