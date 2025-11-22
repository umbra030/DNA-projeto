package controller;
import model.Mutador;
import model.DnaSequencia;
import model.ModeloNeedle;
import view.Output;
import view.OutputFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ControladorMain {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Output output = new Output();
        OutputFile outputFile = new OutputFile("src/sequencias.txt", "src/relatorio.txt");
        ModeloNeedle alinhador = new ModeloNeedle(+1, -1, -2);
        Random random = new Random();
        Mutador mutador = new Mutador(random, 15, 5, 5);
        ControladorDoPrograma controle = new ControladorDoPrograma(output, outputFile, alinhador, mutador);

        System.out.println("Escolha o modo:");
        System.out.println("1) Modo interativo (gerar base aleatória e pedir qtd)");
        System.out.println("2) Processar FASTA (ler arquivo e gerar variantes)");
        System.out.print("Opção: ");
        String opc = sc.nextLine().trim();

        switch (opc) {
        
            case "1":
                controle.run();
                break;
                
            case "2":
                System.out.print("Caminho do FASTA: ");
                Path arquivo = Paths.get("src", "gusanos16S.mafft.txt");
                System.out.print("Variantes por fita: ");
                int var = Integer.parseInt(sc.nextLine().trim());
                List<DnaSequencia> entradas = model.LeitorFasta.lerTodos(arquivo);
                controle.runForAll(entradas, var);
                break;
                
            default:
                System.out.println("Opção inválida.");
        }
    }
}
