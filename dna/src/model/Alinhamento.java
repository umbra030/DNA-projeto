package model;

public class Alinhamento {
    private final int pontuacao;
    private final String linhaA;
    private final String linhaB;

    public Alinhamento(int pontuacao, String linhaA, String linhaB) {
        this.pontuacao = pontuacao;
        this.linhaA = linhaA;
        this.linhaB = linhaB;
    }

    public int getPontuacao() { return pontuacao; }
    public String getLinhaA() { return linhaA; }
    public String getLinhaB() { return linhaB; }
}
