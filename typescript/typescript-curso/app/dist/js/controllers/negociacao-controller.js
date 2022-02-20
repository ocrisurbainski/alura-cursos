var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { metricaTempoExcecucao } from "../decorators/metrica-tempo-execucao.js";
import { DiasDaSemana } from "../enums/dias-da-semana.js";
import { Negociacao } from "../models/negociacao.js";
import { Negociacoes } from "../models/negociacoes.js";
import { MensagemView } from "../views/mensagem-view.js";
import { NegociacoesView } from "../views/nogociacoes-view.js";
export class NegociacaoController {
    constructor() {
        this._negociacoesView = new NegociacoesView('#negociacoesView', true);
        this._mensagemView = new MensagemView('#mensagemView');
        this._negociacoes = new Negociacoes();
        this._inputData = document.querySelector('#data');
        this._inputQuantidade = document.querySelector('#quantidade');
        this._inputValor = document.querySelector('#valor');
        this._negociacoesView.update(this._negociacoes);
    }
    adicionar() {
        const negociacao = Negociacao.of(this._inputData.value, this._inputQuantidade.valueAsNumber, this._inputValor.valueAsNumber);
        console.log(negociacao);
        if (this.validarNegociacao(negociacao)) {
            this._negociacoes.add(negociacao);
            this.updateView();
            this.limparFormulario();
        }
    }
    validarNegociacao(negociacao) {
        let result = true;
        if (this.ehFimDeSemana(negociacao.data)) {
            this._mensagemView.update('Apenas negociações em dias úteis são aceitas.');
            result = false;
        }
        return result;
    }
    ehFimDeSemana(data) {
        const dayOfWeek = data.getDay();
        return dayOfWeek === DiasDaSemana.DOMINGO || dayOfWeek === DiasDaSemana.SABADO;
    }
    limparFormulario() {
        this._inputData.value = '';
        this._inputQuantidade.value = '';
        this._inputValor.value = '';
        this.setFocusFirstField();
    }
    setFocusFirstField() {
        this._inputData.focus();
    }
    updateView() {
        this._negociacoesView.update(this._negociacoes);
        this._mensagemView.update('Negociação adicionada com sucesso.');
    }
}
__decorate([
    metricaTempoExcecucao()
], NegociacaoController.prototype, "adicionar", null);