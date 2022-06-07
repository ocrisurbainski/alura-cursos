package br.com.urbainski.escola.academico.dominio.aluno;

import br.com.urbainski.escola.shared.dominio.CPF;

import java.util.List;

public interface AlunoRepository {

    void matriculate(Aluno aluno);

    Aluno findByCpf(CPF cpf);

    List<Aluno> findAll();

}
