package br.com.urbainski.escola.academico.dominio.aluno;

import java.util.List;

public interface TelefoneRepository {

    void salvar(Integer idAluno, Telefone telefone);

    List<Telefone> findByIdAluno(Integer idAluno);

}
