package br.com.urbainski.escola.dominio.evento;

public interface PublicadorEvento {

    void adicionar(OuvinteEvento ouvinteEvento);

    void publicar(Evento evento);

}
