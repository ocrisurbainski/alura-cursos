import { AbstractView } from "./abstract-view.js";
export class NegociacoesView extends AbstractView {
    constructor() {
        super(...arguments);
        this._formatter = new Intl.DateTimeFormat();
    }
    template(negociacoes) {
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
    formatarData(data) {
        return this._formatter.format(data);
    }
}
