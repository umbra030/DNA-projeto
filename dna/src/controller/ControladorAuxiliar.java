package controller;

import model.Alinhamento;
import model.Dados;

public class ControladorAuxiliar {
    public static Dados calcularDados(Alinhamento al) {
        String A = al.getLinhaA(), B = al.getLinhaB();
        int similar = 0, naoSimilar = 0, diferencas = 0, comparado = 0;
        for (int k = 0; k < A.length(); k++) {
            char a = A.charAt(k), b = B.charAt(k);
            if (a == '-' || b == '-') {
                diferencas++;
                continue;
            }
            comparado++;
            if (a == b) similar++;
            else naoSimilar++;
        }
        double porcentagem;
        if (comparado == 0) {
            porcentagem = 0.0;
        } else {
            porcentagem = 100.0 * similar / comparado;
        }
        return new Dados(similar, naoSimilar, diferencas, porcentagem);
    }
}
