package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class LeitorFasta {

    private LeitorFasta() {}

    public static List<DnaSequencia> lerTodos(Path fastaFile) throws IOException {
        List<DnaSequencia> out = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(fastaFile, StandardCharsets.UTF_8)) {
            String linha;
            String id = null;
            StringBuilder seq = new StringBuilder();
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;
                if (linha.startsWith(">")) {
                    if (id != null) {
                        out.add(new DnaSequencia(id, seq.toString()));
                    }
                    id = linha.substring(1).trim().replaceAll("\\s+", "_");
                    seq.setLength(0);
                } else {
                    seq.append(linha.replaceAll("\\s+", ""));
                }
            }
            if (id != null) out.add(new DnaSequencia(id, seq.toString()));
        }
        return out;
    }
}
