import { TransferenciaService } from './../services/transferencia.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-extrato',
  templateUrl: './extrato.component.html',
  styleUrls: ['./extrato.component.scss']
})
export class ExtratoComponent implements OnInit {

  public transferencias: any[];

  constructor(private transferencicService: TransferenciaService) { }

  ngOnInit(): void {
      this.transferencias = this.transferencicService.transferencias;
  }

}
