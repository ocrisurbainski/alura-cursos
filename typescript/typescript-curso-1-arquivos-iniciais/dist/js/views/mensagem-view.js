import { AbstractView } from "./abstract-view.js";
export class MensagemView extends AbstractView {
    template(message) {
        return `
            <p class="alert alert-info">${message}</p>
        `;
    }
}
