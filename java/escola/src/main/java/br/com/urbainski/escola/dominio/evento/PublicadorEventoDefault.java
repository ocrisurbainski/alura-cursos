package br.com.urbainski.escola.dominio.evento;

import java.util.ArrayList;
import java.util.List;

public class PublicadorEventoDefault implements PublicadorEvento {

    private static PublicadorEventoDefault INSTANCE;
    public final List<OuvinteEvento> ouvintes = new ArrayList<>();

    private PublicadorEventoDefault() {

    }

    @Override
    public void adicionar(OuvinteEvento ouvinteEvento) {
        this.ouvintes.add(ouvinteEvento);
    }

    @Override
    public void publicar(Evento evento) {
        this.ouvintes.forEach(ouvinteEvento -> ouvinteEvento.executar(evento));
    }

    public static PublicadorEventoDefault getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublicadorEventoDefault();
        }
        return INSTANCE;
    }

}
