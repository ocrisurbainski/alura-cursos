export class Negociacoes {
    constructor() {
        this._negociacoes = new Array();
    }
    add(negociacao) {
        this._negociacoes.push(negociacao);
    }
    get lista() {
        return [...this._negociacoes];
    }
    toText() {
        return JSON.stringify(this, null, 4);
    }
    ehIgual(value) {
        return JSON.stringify(this.lista) === JSON.stringify(value.lista);
    }
}
