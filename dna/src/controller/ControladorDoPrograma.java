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
    
    public void runForAll(List<DnaSequencia> entradas, int variantesPorFita) {
        output.titulo();
        // titulo
        try {
            // salva lista de originais
            List<String> apenasSeqs = entradas.stream().map(DnaSequencia::getSequencia).collect(Collectors.toList());
            outputFile.salvarSequencias("## arquivo de entrada (originais)", apenasSeqs);
            if (alinhar instanceof ModeloNeedle) {
                ModeloNeedle m = (ModeloNeedle) alinhar;
                outputFile.salvarResultado(m.getSimilar(), m.getNaoSimilar(), m.getDiferencas());
            }
        } catch (IOException e) {
            output.getOut().println("Erro ao salvar nome de sequência: " + e.getMessage());
        }

        int seqIndex = 1;
        for (DnaSequencia original : entradas) {
            output.getOut().println("\nProcessando sequência " + seqIndex + " id = " + original.getId() + " tamanho = " + original.length());
            // gera derivadas para seq
            List<DnaSequencia> derivadas = mutador.mutate(original, variantesPorFita);
            List<String> seqs = derivadas.stream().map(DnaSequencia::getSequencia).collect(Collectors.toList());
            try {
                outputFile.salvarSequencias(original.getSequencia(), seqs);
            } catch (IOException ex) {
                output.getOut().println("Erro ao salvar derivadas: " + ex.getMessage());
            }

            int i = 1;
            for (DnaSequencia deriv : derivadas) {
                Alinhamento a = alinhar.alinha(original, deriv);
                Dados data = ControladorAuxiliar.calcularDados(a);
                output.mostrarAlinhamento(i, a, data);
                try {
                    outputFile.anexarAlinhamento(i, a, data);
                } catch (IOException ex) {
                    output.getOut().println("Erro ao anexar alinhamento: " + ex.getMessage());
                }
                i++;
            }
            seqIndex++;
        }
        output.getOut().println("Processamento completo.");
    
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
