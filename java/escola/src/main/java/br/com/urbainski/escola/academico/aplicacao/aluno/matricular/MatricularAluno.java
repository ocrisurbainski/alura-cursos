package br.com.urbainski.escola.academico.aplicacao.aluno.matricular;

import br.com.urbainski.escola.academico.dominio.aluno.AlunoMatriculadoEvento;
import br.com.urbainski.escola.academico.dominio.aluno.AlunoRepository;
import br.com.urbainski.escola.shared.dominio.CPF;
import br.com.urbainski.escola.shared.dominio.evento.PublicadorEvento;

public class MatricularAluno {

    private final AlunoRepository alunoRepository;
    private final PublicadorEvento publicadorEvento;

    public MatricularAluno(AlunoRepository alunoRepository, PublicadorEvento publicadorEvento) {
        this.alunoRepository = alunoRepository;
        this.publicadorEvento = publicadorEvento;
    }

    public void executar(MatricularAlunoDTO dto) {
        var aluno = dto.criarAluno();
        this.alunoRepository.matriculate(aluno);

        var evento = new AlunoMatriculadoEvento(new CPF(dto.getCpf()));
        this.publicadorEvento.publicar(evento);
    }

}
