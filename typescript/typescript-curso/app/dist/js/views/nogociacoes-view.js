var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { escapeHtml } from "../decorators/escape-html.js";
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
__decorate([
    escapeHtml()
], NegociacoesView.prototype, "template", null);
//# sourceMappingURL=nogociacoes-view.js.map