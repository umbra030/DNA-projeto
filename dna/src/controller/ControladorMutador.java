package controller;

import java.util.Random;

public class ControladorMutador {
    private static final char[] DNA = {'A','T','C','G'};
    public static String gerarBaseAleatoria(int n, Random random) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(DNA[random.nextInt(DNA.length)]);
        return sb.toString();
    }
}
