import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Transferencia } from '../models/transferencia.model';

@Injectable({
  providedIn: 'root'
})
export class TransferenciaService {

  private url: string;

  constructor(private httpClient: HttpClient) {
    this.url = `${environment.apiUrl}/transferencias`;
  }

  public findAll(): Observable<Transferencia[]> {
    return this.httpClient.get<Transferencia[]>(this.url);
  }

  public adicionar(transferencia: Transferencia): Observable<Transferencia> {
    transferencia.data = new Date();
    return this.httpClient.post<Transferencia>(this.url, transferencia);
  }
}
