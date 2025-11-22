package model;

public class Dados {
    private final int similar;
    private final int naoSimilar;
    private final int diferencas;
    private final double porcentagem;

    public Dados(int similar, int naoSimilar, int diferencas, double porcentagem) {
        this.similar = similar;
        this.naoSimilar = naoSimilar;
        this.diferencas = diferencas;
        this.porcentagem = porcentagem;
    }

    public int getSimilar() { return similar; }
    public int getNaoSimilar() { return naoSimilar; }
    public int getDiferencas() { return diferencas; }
    public double getPorcentagem() { return porcentagem; }
}
