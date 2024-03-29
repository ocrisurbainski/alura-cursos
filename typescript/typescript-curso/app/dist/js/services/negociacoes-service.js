import { Negociacao } from "../interfaces/negociacao.js";
export class NegociacoesService {
    obterNegociacoes() {
        return fetch('http://localhost:8080/dados')
            .then(res => res.json())
            .then((dados) => {
            return dados.map(dado => {
                return Negociacao.ofNegociacaoTO(dado);
            });
        });
    }
}
//# sourceMappingURL=negociacoes-service.js.map