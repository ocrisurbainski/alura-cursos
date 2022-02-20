import { AbstractView } from "./abstract-view.js";

export class MensagemView extends AbstractView<string> {

    protected template(message: string): string {
        return `
            <p class="alert alert-info">${message}</p>
        `;
    }

}