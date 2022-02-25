import { Component, EventEmitter, Output, OnInit } from '@angular/core';

@Component({
  selector: 'app-nova-transferencia',
  templateUrl: './nova-transferencia.component.html',
  styleUrls: ['./nova-transferencia.component.scss']
})
export class NovaTransferenciaComponent implements OnInit {

  @Output() onTransfer = new EventEmitter<any>();

  public valor: number;
  public destino: number;

  constructor() { }

  ngOnInit(): void {
    this.limparCampos();
  }

  public transferir(): void {
    this.onTransfer.emit({
      valor: this.valor,
      destino: this.destino,
    });

    this.limparCampos();
  }

  private limparCampos(): void {
    this.valor = 0;
    this.destino = 0;
  }

}
