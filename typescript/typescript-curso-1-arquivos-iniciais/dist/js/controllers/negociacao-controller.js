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
