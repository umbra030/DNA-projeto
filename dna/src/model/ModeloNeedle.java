package model;

import java.util.Objects;

public class ModeloNeedle implements Alinhador {
    private final int similar;
    private final int naoSimilar;
    private final int diferencas;

    public ModeloNeedle(int similar, int naoSimilar, int diferencas) {
        this.similar = similar;
        this.naoSimilar = naoSimilar;
        this.diferencas = diferencas;
    }
    
    public int getSimilar() {
		return similar;
	}

	public int getNaoSimilar() {
		return naoSimilar;
	}

	public int getDiferencas() {
		return diferencas;
	}

	private enum Direcao { NONE, DIAG, UP, LEFT }
    
    @Override
    public Alinhamento alinha(DnaSequencia A, DnaSequencia B) {
        Objects.requireNonNull(A);
        Objects.requireNonNull(B);
        String a = A.getSequencia();
        String b = B.getSequencia();
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        Direcao[][] bt = new Direcao[n + 1][m + 1];

        //bordas
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][0] + diferencas;
            bt[i][0] = Direcao.UP;
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = dp[0][j - 1] + diferencas;
            bt[0][j] = Direcao.LEFT;
        }
        bt[0][0] = Direcao.NONE;

        for (int i = 1; i <= n; i++) {
            char ca = a.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                char cb = b.charAt(j - 1);

                int pontuacaoDiag;
                if (ca == cb) pontuacaoDiag = dp[i - 1][j - 1] + similar;
                else pontuacaoDiag = dp[i - 1][j - 1] + naoSimilar;

                int pontuacaoUp = dp[i - 1][j] + diferencas;
                int pontuacaoLeft = dp[i][j - 1] + diferencas;

                int melhor = pontuacaoDiag;
                Direcao dir = Direcao.DIAG;
                if (pontuacaoUp > melhor) {
                    melhor = pontuacaoUp;
                    dir = Direcao.UP;
                }
                if (pontuacaoLeft > melhor) {
                    melhor = pontuacaoLeft;
                    dir = Direcao.LEFT;
                }

                dp[i][j] = melhor;
                bt[i][j] = dir;
            }
        }

        //volta
        StringBuilder saidaA = new StringBuilder();
        StringBuilder saidaB = new StringBuilder();
        int i = n, j = m;
        while (i > 0 || j > 0) {
            Direcao d = bt[i][j];
            if (d == Direcao.DIAG) {
                saidaA.append(a.charAt(i - 1));
                saidaB.append(b.charAt(j - 1));
                i--; j--;
            } else if (d == Direcao.UP) {
                saidaA.append(a.charAt(i - 1));
                saidaB.append('-');
                i--;
            } else {
                saidaA.append('-');
                saidaB.append(b.charAt(j - 1));
                j--;
            }
        }
        return new Alinhamento(dp[n][m], saidaA.reverse().toString(), saidaB.reverse().toString());
  
    }
    
}