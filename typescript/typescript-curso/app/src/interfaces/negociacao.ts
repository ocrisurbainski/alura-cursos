import { NegociacaoTO } from "./negociacao-to.js";
import { TextConverter } from "../models/text-converter.js";

export class Negociacao implements TextConverter{

    constructor(
        private _data: Date, 
        public readonly quantidade: number, 
        public readonly valor: number) { }

    get volume(): number {
        return this.quantidade * this.valor;
    }

    get data(): Date {
        const novaData = new Date(this._data.getTime());
        return novaData;
    }

    toText(): string {
        return `
            Data: ${this.data},
            Quantidade: ${this.quantidade},
            Valor: ${this.valor}
        `;
    }

    public ehIgual(negociacao: Negociacao): boolean {
        if (negociacao === undefined || negociacao === null) {
            return false;
        }
        return this.isSameDate(negociacao._data);
    }

    private isSameDate(value: Date): boolean {
        const isSameDate = this._data.getDate() === value.getDate(),
            isSameMonth = this._data.getMonth() === value.getMonth(),
            isSameYear = this._data.getFullYear() === value.getFullYear();
        return isSameDate && isSameMonth && isSameYear;
    }

    public static of(dataString: string, quantidade: number, valor: number): Negociacao {
        const data = new Date(dataString.replace(/-/g, ','));
        return new Negociacao(data, quantidade, valor);
    }

    public static ofNegociacaoTO(negociacaoTO: NegociacaoTO): Negociacao {
        return new Negociacao(new Date(), negociacaoTO.vezes, negociacaoTO.montante);
    }
}