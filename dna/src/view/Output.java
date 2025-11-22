package view;

import model.Alinhamento;
import model.Dados;

import java.util.Scanner;

public class Output {
    private final Scanner sc = new Scanner(System.in);

    public void titulo() {
        System.out.println("Sequenciamento genético");
    }

    public int quantidade() {
        System.out.print("Quantas fitas de DNA gerar? ");
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception ex) {
                System.out.print("Valor inválido. Digite um número inteiro: ");
            }
        }
    }

    public void base(String base) {
        System.out.println("\nGene base:");
        System.out.println(base);
        System.out.println();
    }

    public void mostrarAlinhamento(int i, Alinhamento a, Dados s) {
        System.out.println("<>" + i + "° derivada " + "  Pontuação = " + a.getPontuacao());
        System.out.println(a.getLinhaA());
        System.out.println(linhaMeio(a.getLinhaA(), a.getLinhaB()));
        System.out.println(a.getLinhaB());
        System.out.println();
        System.out.printf("Similar = %d Não similar = %d Diferenças = %d identidade = %.2f%%%n",
                s.getSimilar(), s.getNaoSimilar(), s.getDiferencas(), s.getPorcentagem());
        System.out.println();
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
