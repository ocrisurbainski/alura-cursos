export class AbstractView {
    constructor(selector, escapeHtml = false) {
        this.escapeHtml = escapeHtml;
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
        if (this.escapeHtml) {
            template = template.replace(/<script>[\s\S]*?<\/script>/, '');
        }
        this._element.innerHTML = template;
    }
}
