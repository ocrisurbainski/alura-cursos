var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { inspect } from "../decorators/inspect.js";
import { metricaTempoExcecucao } from "../decorators/metrica-tempo-execucao.js";
export class AbstractView {
    constructor(selector) {
        const element = document.querySelector(selector);
        if (element) {
            this._element = element;
        }
        else {
            throw new Error(`Selector \'${selector}\' informado não existe no DOM.`);
        }
    }
    update(value) {
        let template = this.template(value);
        this._element.innerHTML = template;
    }
}
__decorate([
    metricaTempoExcecucao(),
    inspect()
], AbstractView.prototype, "update", null);
