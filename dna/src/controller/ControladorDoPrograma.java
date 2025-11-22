package controller;

import model.*;
import view.Output;
import view.OutputFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ControladorDoPrograma {
    private final Output output;
    private final OutputFile outputFile;
    private final Alinhador alinhar;
    private final Mutador mutador;

    public ControladorDoPrograma(Output output, OutputFile outputFile, Alinhador alinha, Mutador mutador) {
        this.output = output;
        this.outputFile = outputFile;
        this.alinhar = alinha;
        this.mutador = mutador;
    }

    public void run() {
    	output.titulo();
        int qtd = output.quantidade();

        Random random = new Random();
        String baseSequencia = ControladorMutador.gerarBaseAleatoria(100, random);
        output.base(baseSequencia);

        List<DnaSequencia> derivadas = mutador.mutate(new DnaSequencia("base", baseSequencia), qtd);
        List<String> stringsDerivadas = derivadas.stream().map(DnaSequencia::getSequencia).collect(Collectors.toList());

        try {
        	outputFile.salvarSequencias(baseSequencia, stringsDerivadas);
        	outputFile.salvarResultado( ((ModeloNeedle)alinhar).getSimilar(),
                                         ((ModeloNeedle)alinhar).getNaoSimilar(),
                                         ((ModeloNeedle)alinhar).getDiferencas());
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivos: " + e.getMessage());
        }

        int i = 1;
        for (DnaSequencia d : derivadas) {
            Alinhamento a = alinhar.alinha(new DnaSequencia("base", baseSequencia), d);
            Dados data = ControladorAuxiliar.calcularDados(a);
            output.mostrarAlinhamento(i, a, data);
            try {
            	outputFile.anexarAlinhamento(i, a, data);
            } catch (IOException e) {
                System.out.println("Erro ao escrever relatório: " + e.getMessage());
            }
            i++;
        }
        System.out.println("Concluído.");
    }
}
