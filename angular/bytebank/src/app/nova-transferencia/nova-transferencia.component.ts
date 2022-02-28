import { TransferenciaService } from './../services/transferencia.service';
import { Component, EventEmitter, Output, OnInit } from '@angular/core';

@Component({
  selector: 'app-nova-transferencia',
  templateUrl: './nova-transferencia.component.html',
  styleUrls: ['./nova-transferencia.component.scss']
})
export class NovaTransferenciaComponent implements OnInit {

  public valor: number;
  public destino: string;

  constructor(private transferenciaService: TransferenciaService) { }

  ngOnInit(): void {
    this.limparCampos();
  }

  public transferir(): void {
    this.transferenciaService.adicionar({
      valor: this.valor,
      destino: this.destino,
    });

    this.limparCampos();
  }

  private limparCampos(): void {
    this.valor = undefined;
    this.destino = '';
  }

}
