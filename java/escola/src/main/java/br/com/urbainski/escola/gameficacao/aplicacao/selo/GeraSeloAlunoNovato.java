package br.com.urbainski.escola.gameficacao.aplicacao.selo;

import br.com.urbainski.escola.gameficacao.dominio.selo.Selo;
import br.com.urbainski.escola.gameficacao.dominio.selo.SeloRepository;
import br.com.urbainski.escola.shared.dominio.CPF;
import br.com.urbainski.escola.shared.dominio.evento.Evento;
import br.com.urbainski.escola.shared.dominio.evento.OuvinteEvento;
import br.com.urbainski.escola.shared.dominio.evento.TipoEventoEnum;

public class GeraSeloAlunoNovato extends OuvinteEvento {

    private final SeloRepository seloRepository;

    public GeraSeloAlunoNovato(SeloRepository seloRepository) {
        this.seloRepository = seloRepository;
    }

    @Override
    protected void executar(Evento evento) {

        var cpfAluno = (CPF) evento.getInformacoes().get("cpf");
        var seloAlunoNovato = new Selo("Novato", cpfAluno);
        this.seloRepository.salvar(seloAlunoNovato);
    }

    @Override
    protected boolean deveExecutar(Evento evento) {
        return TipoEventoEnum.ALUNO_MATRICULADO.equals(evento.getTipoEvento());
    }

}
