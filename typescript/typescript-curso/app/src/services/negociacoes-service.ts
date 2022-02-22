import { Negociacao } from "../models/negociacao.js";
import { NegociacaoTO } from "../models/negociacao-to.js";

export class NegociacoesService {

    public obterNegociacoes(): Promise<Negociacao[]> {
        return fetch('http://localhost:8080/dados')
            .then(res => res.json())
            .then((dados: NegociacaoTO[]) => {
                return dados.map(dado => {
                    return Negociacao.ofNegociacaoTO(dado);
                })
            });
    }
}