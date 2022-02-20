export class AbstractView {
    constructor(selector) {
        this._element = document.querySelector(selector);
    }
    update(value) {
        this._element.innerHTML = this.template(value);
    }
}
