import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  public transferencias: any[] = [];

  public title = 'bytebank';

  public onTransfeir(event): void {
    const transferencia = {
      ...event,
      data: new Date()
    };
    this.transferencias.push(transferencia);
  }
}
