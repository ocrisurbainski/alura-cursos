package br.com.urbainski.escola.dominio.aluno;

import java.util.List;

public interface AlunoRepository {

    void matriculate(Aluno aluno);

    Aluno findByCpf(CPF cpf);

    List<Aluno> findAll();

}
