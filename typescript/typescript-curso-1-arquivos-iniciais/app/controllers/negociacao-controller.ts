import { Negociacao } from "../models/negociacao.js";
import { Negociacoes } from "../models/negociacoes.js";

export class NegociacaoController {
    private _inputData: HTMLInputElement;
    private _inputQuantidade: HTMLInputElement;
    private _inputValor: HTMLInputElement;

    private _negociacoes: Negociacoes = new Negociacoes();

    constructor() {
        this._inputData = document.querySelector('#data');
        this._inputQuantidade = document.querySelector('#quantidade');
        this._inputValor = document.querySelector('#valor');
    }

    adicionar(): void {
        const negociacao = new Negociacao(
            this._inputData.valueAsDate, 
            this._inputQuantidade.valueAsNumber, 
            this._inputValor.valueAsNumber
        );
        
        this._negociacoes.add(negociacao);

        console.log(this._negociacoes.lista);

        this.limparFormulario();
    }

    private limparFormulario(): void {
        this._inputData.value = '';
        this._inputQuantidade.value = '';
        this._inputValor.value = '';

        this.setFocusFirstField();
    }

    private setFocusFirstField(): void {
        this._inputData.focus();
    }


}