export abstract class AbstractView<T> {

    protected _element: HTMLElement;

    constructor(selector: string, private escapeHtml: boolean = false) {
        const element = document.querySelector(selector);
        if (element) {
            this._element = element as HTMLElement;
        } else {
            throw new Error(`Selector \'${selector}\' informado não existe no DOM.`);
        }
    }

    public update(value: T): void {
        let template = this.template(value);
        if (this.escapeHtml) {
            template = template.replace(/<script>[\s\S]*?<\/script>/, '');
        }
        this._element.innerHTML = template;
    }

    protected abstract template(value: T): string;
}