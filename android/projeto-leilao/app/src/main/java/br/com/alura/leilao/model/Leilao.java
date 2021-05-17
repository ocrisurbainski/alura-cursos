package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.leilao.exception.LanceMenorMaiorLanceException;
import br.com.alura.leilao.exception.UsuarioDeuCindoLancesException;
import br.com.alura.leilao.exception.UsuarioLancesSeguidosException;
import br.com.alura.leilao.util.MoedaUtil;

import android.support.annotation.NonNull;

public class Leilao implements Serializable {

    private final long id;
    private final String descricao;
    private final List<Lance> lances;

    public Leilao(long id, String descricao) {

        this.id = id;
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public Leilao(String descricao) {

        this.id = 0L;
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public long getId() {

        return id;
    }

    public String getDescricao() {

        return descricao;
    }

    public double getMaiorLance() {

        if (lances.isEmpty()) {

            return 0.0;
        }

        return lances.get(0).getValor();
    }

    public String getMaiorLanceFormatado() {

        return MoedaUtil.format(getMaiorLance());
    }

    public double getMenorLance() {

        if (lances.isEmpty()) {

            return 0.0;
        }

        return lances.get(lances.size() - 1).getValor();
    }

    public String getMenorLanceFormatado() {

        return MoedaUtil.format(getMenorLance());
    }

    public List<Lance> getLances() {

        return lances;
    }

    public void addLance(@NonNull Lance lance) {

        validarLance(lance);

        lances.add(lance);

        sortLances();
    }

    public void validarLance(@NonNull Lance lance) {

        if (novoLanceEhMenorQueMaiorLance(lance)) {

            throw new LanceMenorMaiorLanceException(lance.getValor(), getMaiorLance());
        }

        if (ehMesmoUsuarioDoUltimoLance(lance)) {

            throw new UsuarioLancesSeguidosException();
        }

        if (usuarioJaDeuCincoLances(lance.getUsuario())) {

            throw new UsuarioDeuCindoLancesException();
        }
    }

    public boolean novoLanceEhMenorQueMaiorLance(@NonNull Lance lance) {

        if (lance.getValor() < getMaiorLance()) {

            return true;
        }

        return false;
    }

    private boolean usuarioJaDeuCincoLances(Usuario usuario) {

        int countLance = 0;

        for (Lance lance : lances) {

            if (lance.getUsuario().equals(usuario)) {

                countLance++;
            }

            if (countLance == 5) {

                return true;
            }
        }

        return false;
    }

    private void sortLances() {

        Collections.sort(lances);
    }

    private boolean ehMesmoUsuarioDoUltimoLance(Lance lance) {

        if (lances.size() == 0) {

            return false;
        }

        Lance ultimoLance = lances.get(0);

        return ultimoLance.getUsuario().equals(lance.getUsuario());
    }

    public List<Lance> getTresMaioresLances() {

        if (lances == null || lances.isEmpty()) {

            return new ArrayList<>();
        }

        int size = lances.size() < 3 ? lances.size() : 3;

        return lances.subList(0, size);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Leilao leilao = (Leilao) o;

        if (id != leilao.id) {
            return false;
        }
        return descricao.equals(leilao.descricao);
    }

    @Override
    public int hashCode() {

        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + descricao.hashCode();
        return result;
    }
}