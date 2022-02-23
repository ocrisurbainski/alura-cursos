import { Negociacao } from "../interfaces/negociacao.js";
import { NegociacaoTO } from "../interfaces/negociacao-to.js";

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