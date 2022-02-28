import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TransferenciaService {

  private _transferencias: any[];

  constructor() {
    this._transferencias = [];
  }

  get transferencias(): any[] {
    return this._transferencias;
  }

  public adicionar(transferencia: any): void {
    transferencia.data = new Date();
    this._transferencias.push(transferencia);
  }
}
