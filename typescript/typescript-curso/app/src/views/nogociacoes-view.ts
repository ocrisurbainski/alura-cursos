import { escapeHtml } from "../decorators/escape-html.js";
import { Negociacoes } from "../models/negociacoes.js";
import { AbstractView } from "./abstract-view.js";

export class NegociacoesView extends AbstractView<Negociacoes> {

    private _formatter =  new Intl.DateTimeFormat();

    @escapeHtml()
    protected template(negociacoes: Negociacoes): string {
        return `
            <table class="table table-hover table-bordered">
                <thead>
                    <tr>
                        <th>Data</th>
                        <th>Quantidade</th>
                        <th>Valor</th>
                    </tr>
                </thead>
                <tbody>
                    ${negociacoes.lista.map(negociacao => {
                        return `
                            <tr>
                                <td>${this.formatarData(negociacao.data)}</td>
                                <td>${negociacao.quantidade}</td>
                                <td>${negociacao.valor}</td>
                            </tr>
                        `;
                    }).join('')}
                </tbody>
            </table>
        `;
    }

    private formatarData(data: Date): string {
        return this._formatter.format(data);
    }
    
}