import { TextConverter } from "../models/text-converter.js";

export function imprimir(...objetos: TextConverter[]) {
    for (let objeto of objetos) {
        console.log(objeto.toText());
    }
}