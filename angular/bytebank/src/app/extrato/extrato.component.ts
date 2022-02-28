import { Transferencia } from './../models/transferencia.model';
import { TransferenciaService } from './../services/transferencia.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-extrato',
  templateUrl: './extrato.component.html',
  styleUrls: ['./extrato.component.scss']
})
export class ExtratoComponent implements OnInit {

  public transferencias: Transferencia[];

  constructor(private transferencicService: TransferenciaService) { }

  ngOnInit(): void {
    this.transferencias = [];
    this.transferencicService.findAll().subscribe({
      next: (transferencias) => this.transferencias = transferencias
    });
  }

}
