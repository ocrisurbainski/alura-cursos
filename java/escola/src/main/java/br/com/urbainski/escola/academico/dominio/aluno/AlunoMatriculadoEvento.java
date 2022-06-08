package br.com.urbainski.escola.academico.dominio.aluno;

import br.com.urbainski.escola.shared.dominio.CPF;
import br.com.urbainski.escola.shared.dominio.evento.Evento;
import br.com.urbainski.escola.shared.dominio.evento.TipoEventoEnum;

import java.time.LocalDateTime;
import java.util.Map;

public class AlunoMatriculadoEvento implements Evento {

    private final CPF cpfAluno;
    private final LocalDateTime dataHora;

    public AlunoMatriculadoEvento(CPF cpfAluno) {
        this.cpfAluno = cpfAluno;
        this.dataHora = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public TipoEventoEnum getTipoEvento() {
        return TipoEventoEnum.ALUNO_MATRICULADO;
    }

    @Override
    public Map<String, Object> getInformacoes() {
        return Map.of("cpf", cpfAluno);
    }

    public CPF getCpfAluno() {
        return cpfAluno;
    }

}
