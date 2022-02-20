import { inspect } from "../decorators/inspect.js";
import { metricaTempoExcecucao } from "../decorators/metrica-tempo-execucao.js";

export abstract class AbstractView<T> {

    protected _element: HTMLElement;

    constructor(selector: string) {
        const element = document.querySelector(selector);
        if (element) {
            this._element = element as HTMLElement;
        } else {
            throw new Error(`Selector \'${selector}\' informado não existe no DOM.`);
        }
    }

    @metricaTempoExcecucao()
    @inspect()
    public update(value: T): void {
        let template = this.template(value);
        this._element.innerHTML = template;
    }

    protected abstract template(value: T): string;
}