import { metricaTempoExcecucao } from "../decorators/metrica-tempo-execucao.js";
import { DiasDaSemana } from "../enums/dias-da-semana.js";
import { Negociacao } from "../models/negociacao.js";
import { Negociacoes } from "../models/negociacoes.js";
import { MensagemView } from "../views/mensagem-view.js";
import { NegociacoesView } from "../views/nogociacoes-view.js";

export class NegociacaoController {
    private _inputData: HTMLInputElement;
    private _inputQuantidade: HTMLInputElement;
    private _inputValor: HTMLInputElement;

    private _negociacoesView = new NegociacoesView('#negociacoesView', true);

    private _mensagemView = new MensagemView('#mensagemView');

    private _negociacoes: Negociacoes = new Negociacoes();

    constructor() {
        this._inputData = document.querySelector('#data') as HTMLInputElement;
        this._inputQuantidade = document.querySelector('#quantidade') as HTMLInputElement;
        this._inputValor = document.querySelector('#valor') as HTMLInputElement;
        this._negociacoesView.update(this._negociacoes);
    }

    @metricaTempoExcecucao()
    public adicionar(): void {
        const negociacao = Negociacao.of(
            this._inputData.value, 
            this._inputQuantidade.valueAsNumber, 
            this._inputValor.valueAsNumber
        );

        console.log(negociacao);
        
        if (this.validarNegociacao(negociacao)) {
            
            this._negociacoes.add(negociacao);
    
            this.updateView();
    
            this.limparFormulario();
        }
    }

    private validarNegociacao(negociacao: Negociacao): boolean {
        let result = true;
        
        if (this.ehFimDeSemana(negociacao.data)) {
            this._mensagemView.update('Apenas negociações em dias úteis são aceitas.');
            result = false;
        }
        
        return result;
    }

    private ehFimDeSemana(data: Date): boolean {
        const dayOfWeek = data.getDay();
        return dayOfWeek === DiasDaSemana.DOMINGO || dayOfWeek === DiasDaSemana.SABADO;
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

    private updateView(): void {
        this._negociacoesView.update(this._negociacoes);
        this._mensagemView.update('Negociação adicionada com sucesso.');
    }

}