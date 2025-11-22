package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutador {
    private static final char[] DNA = {'A','T','C','G'};
    private final Random aleatorio;
    private final int porcentagemSubstituir;
    private final int porcentagemDeletar;
    private final int porcentagemInserir;

    public Mutador(Random aleatorio, int porcentagemSubstituir, int porcentagemDeletar, int porcentagemInserir) {
        this.aleatorio = aleatorio;
        this.porcentagemSubstituir = porcentagemSubstituir;
        this.porcentagemDeletar = porcentagemDeletar;
        this.porcentagemInserir = porcentagemInserir;
    }

    public List<DnaSequencia> mutate(DnaSequencia base, int cont) {
        List<DnaSequencia> out = new ArrayList<>(cont);
        for (int i = 0; i < cont; i++) out.add(new DnaSequencia("derivada_" + (i+1), mutar(base.getSequencia())));
        return out;
    }

    private String mutar(String original) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            if (aleatorio.nextInt(100) < porcentagemInserir) out.append(DNA[aleatorio.nextInt(DNA.length)]);
            int chance = aleatorio.nextInt(100);
            if (chance < porcentagemDeletar) continue;
            else if (chance < porcentagemDeletar + porcentagemSubstituir) {
                char atual = original.charAt(i);
                char nova;
                do { nova = DNA[aleatorio.nextInt(DNA.length)]; } while (nova == atual);
                out.append(nova);
            } else {
                out.append(original.charAt(i));
            }
        }
        if (aleatorio.nextInt(100) < porcentagemInserir) out.append(DNA[aleatorio.nextInt(DNA.length)]);
        return out.toString();
    }
}
