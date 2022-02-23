export class Negociacao {
    constructor(_data, quantidade, valor) {
        this._data = _data;
        this.quantidade = quantidade;
        this.valor = valor;
    }
    get volume() {
        return this.quantidade * this.valor;
    }
    get data() {
        const novaData = new Date(this._data.getTime());
        return novaData;
    }
    toText() {
        return `
            Data: ${this.data},
            Quantidade: ${this.quantidade},
            Valor: ${this.valor}
        `;
    }
    ehIgual(negociacao) {
        if (negociacao === undefined || negociacao === null) {
            return false;
        }
        return this.isSameDate(negociacao._data);
    }
    isSameDate(value) {
        const isSameDate = this._data.getDate() === value.getDate(), isSameMonth = this._data.getMonth() === value.getMonth(), isSameYear = this._data.getFullYear() === value.getFullYear();
        return isSameDate && isSameMonth && isSameYear;
    }
    static of(dataString, quantidade, valor) {
        const data = new Date(dataString.replace(/-/g, ','));
        return new Negociacao(data, quantidade, valor);
    }
    static ofNegociacaoTO(negociacaoTO) {
        return new Negociacao(new Date(), negociacaoTO.vezes, negociacaoTO.montante);
    }
}
//# sourceMappingURL=negociacao.js.map