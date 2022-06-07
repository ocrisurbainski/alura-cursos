package br.com.urbainski.escola.shared.dominio.evento;

public abstract class OuvinteEvento {

    public void processar(Evento evento) {
        if (deveExecutar(evento)) {
            executar(evento);
        }
    }

    protected abstract void executar(Evento evento);

    protected abstract boolean deveExecutar(Evento evento);

}
