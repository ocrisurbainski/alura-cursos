package br.com.urbainski.escola.shared.dominio.evento;

import java.time.LocalDateTime;
import java.util.Map;

public interface Evento {

    LocalDateTime getDataHora();

    TipoEventoEnum getTipoEvento();

    Map<String, Object> getInformacoes();

}
