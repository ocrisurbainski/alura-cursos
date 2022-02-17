import { Negociacao } from "../models/negociacao.js";
import { Negociacoes } from "../models/negociacoes.js";
export class NegociacaoController {
    constructor() {
        this._negociacoes = new Negociacoes();
        this._inputData = document.querySelector('#data');
        this._inputQuantidade = document.querySelector('#quantidade');
        this._inputValor = document.querySelector('#valor');
    }
    adicionar() {
        const negociacao = new Negociacao(this._inputData.valueAsDate, this._inputQuantidade.valueAsNumber, this._inputValor.valueAsNumber);
        this._negociacoes.add(negociacao);
        console.log(this._negociacoes.lista);
        this.limparFormulario();
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
}
