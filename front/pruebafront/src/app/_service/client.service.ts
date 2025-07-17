import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cliente } from '../model/Cliente';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private url = `http://localhost:8080/clients`;
  mensajeCambio = new Subject<string>();

  constructor(private http: HttpClient) { }

  listarPorDocumento(documentClient: string) {
    return this.http.get<any>(`${this.url}/${documentClient}`);
  }

  listar() {
    return this.http.get<any>(`${this.url}`);
  }

  guardar(cliente: Cliente){
      return this.http.post(`${this.url}`, cliente);
    }

  editar(cliente: Cliente) {
    return this.http.put(`${this.url}`, cliente);
  }

  eliminar(document: any) {
    return this.http.delete<any>(`${this.url}/${document}`);
  }
}
