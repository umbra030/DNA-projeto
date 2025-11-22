package model;

import java.util.Objects;

public class DnaSequencia {
    private final String id;
    private final String sequencia;

    public DnaSequencia(String id, String sequencia) {
        this.id = Objects.requireNonNull(id);
        this.sequencia = Objects.requireNonNull(sequencia).toUpperCase();
    }

    public String getId() { return id; }
    public String getSequencia() { return sequencia; }
    public int length() { return sequencia.length(); }

    public String toString() {
        return ">" + id + "\n" + sequencia;
    }

}