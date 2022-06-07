package br.com.urbainski.escola.gameficacao.dominio.selo;

import br.com.urbainski.escola.shared.dominio.CPF;

import java.util.List;

public interface SeloRepository {

    void salvar(Selo selo);

    List<Selo> findByCpf(CPF cpf);

}
