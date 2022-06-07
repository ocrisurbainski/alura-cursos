package br.com.urbainski.escola.academico.dominio.aluno;

import br.com.urbainski.escola.util.ValidationUtils;

public class Telefone {

    private Integer id;

    private Integer idAluno;

    private final String ddd;
    private final String numero;

    public Telefone(String ddd, String numero) {
        this(null, null, ddd, numero);
    }

    public Telefone(Integer id, Integer idAluno, String ddd, String numero) {
        if (ValidationUtils.isDDDInvalid(ddd)) {
            throw new IllegalArgumentException("DDD inválido");
        }
        if (ValidationUtils.isNumeroTelefoneInvalid(numero)) {
            throw new IllegalArgumentException("Número telefone inválido");
        }
        this.id = id;
        this.idAluno = idAluno;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumero() {
        return numero;
    }

}
