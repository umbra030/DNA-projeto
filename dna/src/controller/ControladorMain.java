package controller;
import model.Mutador;
import model.ModeloNeedle;
import view.Output;
import view.OutputFile;
import java.nio.file.Paths;
import java.util.Random;

public class ControladorMain {
    public static void main(String[] args) {
        Output output = new Output();
        String caminhoSequencias = Paths.get("src","sequencias.txt").toString();
        String caminhoRelatorio = Paths.get("src","relatorio.txt").toString();
        OutputFile outputFile = new OutputFile(caminhoSequencias, caminhoRelatorio);
        ModeloNeedle alinhador = new ModeloNeedle(+1, -1, -2);
        Mutador mutador = new Mutador(new Random(), 15, 5, 5);

        ControladorDoPrograma controle = new ControladorDoPrograma(output, outputFile, alinhador, mutador);
        controle.run();
    }
}
