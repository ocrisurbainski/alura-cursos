import { Negociacao } from "./negociacao.js";

export class Negociacoes {

    private _negociacoes: Array<Negociacao> = new Array();

    constructor() {}

    public add(negociacao: Negociacao): void {
        this._negociacoes.push(negociacao);
    }

    public get lista(): ReadonlyArray<Negociacao> {
        return [...this._negociacoes];
    }
}