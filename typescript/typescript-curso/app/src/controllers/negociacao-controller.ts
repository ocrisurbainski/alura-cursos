import { domInject } from "../decorators/dom-inject.js";
import { metricaTempoExcecucao } from "../decorators/metrica-tempo-execucao.js";
import { DiasDaSemana } from "../enums/dias-da-semana.js";
import { Negociacao } from "../models/negociacao.js";
import { Negociacoes } from "../models/negociacoes.js";
import { NegociacoesService } from "../services/negociacoes-service.js";
import { imprimir } from "../util/imprimir.js";
import { MensagemView } from "../views/mensagem-view.js";
import { NegociacoesView } from "../views/nogociacoes-view.js";

export class NegociacaoController {
    @domInject('#data')
    private _inputData: HTMLInputElement;

    @domInject('#quantidade')
    private _inputQuantidade: HTMLInputElement;

    @domInject('#valor')
    private _inputValor: HTMLInputElement;

    private _negociacoesView = new NegociacoesView('#negociacoesView');

    private _mensagemView = new MensagemView('#mensagemView');

    private _negociacoes: Negociacoes = new Negociacoes();

    private _negociacoesService = new NegociacoesService();

    constructor() {
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

            imprimir(negociacao, this._negociacoes);
    
            this.updateView();
    
            this.limparFormulario();
        }
    }

    public importarDados(): void {
        this._negociacoesService.obterNegociacoes().then((negociacoes: Negociacao[]) => {
            for (let negociacao of negociacoes) {
                this._negociacoes.add(negociacao);
            }

            this._negociacoesView.update(this._negociacoes);
        });
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