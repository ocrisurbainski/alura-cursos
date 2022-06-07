package br.com.urbainski.escola.dominio.aluno;

import br.com.urbainski.escola.dominio.evento.Evento;
import br.com.urbainski.escola.dominio.evento.OuvinteEvento;

import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlunoMatriculadoLog extends OuvinteEvento {

    private static final Logger logger = Logger.getLogger(AlunoMatriculadoLog.class.getName());

    public void executar(Evento evento) {

        var alunoEvento = (AlunoMatriculadoEvento) evento;
        var dataHoraFormatadao = alunoEvento.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        var args = new Object[] { alunoEvento.getCpfAluno().getNumero(), dataHoraFormatadao };

        logger.log(Level.INFO, "Aluno com CPF: {0} matriculado: {1}", args);
    }

    @Override
    protected boolean deveExecutar(Evento evento) {
        return evento instanceof AlunoMatriculadoEvento;
    }

}
