package br.com.urbainski.escola.dominio.aluno;

import br.com.urbainski.escola.dominio.evento.Evento;

import java.time.LocalDateTime;

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

    public CPF getCpfAluno() {
        return cpfAluno;
    }

}
