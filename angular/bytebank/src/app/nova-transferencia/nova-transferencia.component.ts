import { Transferencia } from './../models/transferencia.model';
import { TransferenciaService } from './../services/transferencia.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nova-transferencia',
  templateUrl: './nova-transferencia.component.html',
  styleUrls: ['./nova-transferencia.component.scss']
})
export class NovaTransferenciaComponent implements OnInit {

  public valor: number;
  public destino: string;

  constructor(
    private transferenciaService: TransferenciaService,
    private router: Router) { }

  ngOnInit(): void {
    this.limparCampos();
  }

  public transferir(): void {

    const transferencia = {
      valor: this.valor,
      destino: this.destino,
    } as Transferencia;

    this.transferenciaService.adicionar(transferencia).subscribe({
      next: (transferencia) => {
        console.log(transferencia);
        this.limparCampos();

        this.router.navigateByUrl('extrato');
      },
      error: (err) => console.log(err)
    });
  }

  private limparCampos(): void {
    this.valor = undefined;
    this.destino = '';
  }

}
