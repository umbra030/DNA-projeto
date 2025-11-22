package view;

import model.Alinhamento;
import model.Dados;

import java.io.PrintStream;
import java.util.Scanner;

public class Output {
    private final Scanner sc;
    private final PrintStream out;

    public Output() {
        this(System.out, new Scanner(System.in));
    }

    //testes ou redirecionamento de saída
    public Output(PrintStream out, Scanner sc) {
        this.out = out;
        this.sc = sc;
    }

    public void titulo() {
        out.println("Sequenciamento genético");
    }

    public int quantidade() {
        out.print("Quantas fitas de DNA gerar? ");
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception ex) {
                out.print("Valor inválido. Digite um número inteiro: ");
            }
        }
    }

    public void base(String base) {
        out.println();
        out.println("Gene base:");
        out.println(base);
        out.println();
    }

    public void mostrarAlinhamento(int i, Alinhamento a, Dados s) {
        out.println("<>" + i + "° derivada  Pontuação = " + a.getPontuacao());
        out.println(a.getLinhaA());
        out.println(linhaMeio(a.getLinhaA(), a.getLinhaB()));
        out.println(a.getLinhaB());
        out.println();
        out.printf("Similar = %d Não similar = %d Diferenças = %d identidade = %.2f%%%n",
                s.getSimilar(), s.getNaoSimilar(), s.getDiferencas(), s.getPorcentagem());
        out.println();
    }

    public void mensagem(String msg) {
        out.println(msg);
    }

    private String linhaMeio(String A, String B) {
        int len = Math.min(A.length(), B.length());
        StringBuilder mid = new StringBuilder(len);
        for (int k = 0; k < len; k++) {
            char x = A.charAt(k), y = B.charAt(k);
            if (x == '-' || y == '-') mid.append(' ');
            else if (x == y) mid.append('|');
            else mid.append('.');
        }
        return mid.toString();
    }
    
    public PrintStream getOut() { return out; }

}
