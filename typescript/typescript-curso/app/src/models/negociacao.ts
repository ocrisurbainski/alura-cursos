export class Negociacao {

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

    public static of(dataString: string, quantidade: number, valor: number): Negociacao {
        const data = new Date(dataString.replace(/-/g, ','));
        return new Negociacao(data, quantidade, valor);
    }
}