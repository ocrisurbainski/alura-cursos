import { NegociacaoTO } from "./negociacao-to.js";
import { TextConverter } from "./text-converter.js";

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

    public static of(dataString: string, quantidade: number, valor: number): Negociacao {
        const data = new Date(dataString.replace(/-/g, ','));
        return new Negociacao(data, quantidade, valor);
    }

    public static ofNegociacaoTO(negociacaoTO: NegociacaoTO): Negociacao {
        return new Negociacao(new Date(), negociacaoTO.vezes, negociacaoTO.montante);
    }
}