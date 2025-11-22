package view;

import model.Alinhamento;
import model.Dados;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class OutputFile {
    private final String registroSequencias;
    private final String registroResultado;

    public OutputFile(String registroSequencias, String registroResultado) {
        this.registroSequencias = registroSequencias;
        this.registroResultado = registroResultado;
    }

    public void salvarSequencias(String base, java.util.List<String> derivadas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(registroSequencias))) {
            bw.write(base); bw.newLine();
            for (String s : derivadas) { bw.write(s); bw.newLine(); }
        }
    }

    public void salvarResultado(int similar, int naoSimilar, int diferencas) throws IOException {
        try (BufferedWriter relatorio = new BufferedWriter(new FileWriter(registroResultado))) {
            relatorio.write("Relatório");
            relatorio.newLine();
            relatorio.write("Data: " + LocalDateTime.now());
            relatorio.newLine();
            relatorio.write("Parâmetros de pontuação: Similar = " + similar + " Não similar = " + naoSimilar + " Diferenças = " + diferencas);
            relatorio.newLine();
            relatorio.write(" = ".repeat(60));
            relatorio.newLine();
            relatorio.newLine();
        }
    }

    public void anexarAlinhamento(int id, Alinhamento linha, Dados data) throws IOException {
        try (BufferedWriter registro = new BufferedWriter(new FileWriter(registroResultado, true))) {
            registro.write(">>> Derivada #" + id + " (len =" + linha.getLinhaB().replace("-", "").length() + ")");
            registro.newLine();
            registro.write("Pontuação: " + linha.getPontuacao());
            registro.newLine();
            registro.write(linha.getLinhaA()); registro.newLine();
            registro.write(linhaMeio(linha.getLinhaA(), linha.getLinhaB())); registro.newLine();
            registro.write(linha.getLinhaB()); registro.newLine();
            registro.write("Similar = " + data.getSimilar() + "  Não similar = " + data.getNaoSimilar() +
                      "  Diferenças = " + data.getDiferencas() + "  |  % identidade = " + String.format("%.2f", data.getPorcentagem()) + "%");
            registro.newLine();
            registro.write("-".repeat(72));
            registro.newLine();
            registro.newLine();
        }
    }

    private String linhaMeio(String A, String B) {
        StringBuilder mid = new StringBuilder(A.length());
        for (int k = 0; k < A.length(); k++) {
            char x = A.charAt(k), y = B.charAt(k);
            if (x == '-' || y == '-') mid.append(' ');
            else if (x == y) mid.append('|');
            else mid.append('.');
        }
        return mid.toString();
    }
}
