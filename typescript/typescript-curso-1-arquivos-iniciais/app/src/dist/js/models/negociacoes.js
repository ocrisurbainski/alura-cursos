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
}
