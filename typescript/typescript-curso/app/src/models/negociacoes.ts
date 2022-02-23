import { Comparable } from "../interfaces/comparable.js";
import { Negociacao } from "../interfaces/negociacao.js";
import { TextConverter } from "./text-converter.js";

export class Negociacoes implements TextConverter, Comparable<Negociacoes> {

    private _negociacoes: Array<Negociacao> = new Array();

    constructor() {}

    public add(negociacao: Negociacao): void {
        this._negociacoes.push(negociacao);
    }

    public get lista(): ReadonlyArray<Negociacao> {
        return [...this._negociacoes];
    }

    toText(): string {
        return JSON.stringify(this, null, 4);
    }
    
    ehIgual(value: Negociacoes): boolean {
        return JSON.stringify(this.lista) === JSON.stringify(value.lista);
    }
}