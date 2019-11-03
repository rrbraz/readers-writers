package readers_writers;


import readers_writers.database.policies.AccessPolicy;
import readers_writers.database.policies.AlwaysBlockPolicy;
import readers_writers.database.policies.ReaderPriorityPolicy;
import readers_writers.database.policies.WriterPriorityPolicy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    /* numero de vezes que cada problema é executado, para minimizar variações aleatórias */
    public static final int N_EXECS = 50;

    private static void runProblem(AccessPolicy policy, java.io.Writer writer) throws IOException {
        writer.write("readers;writers;time (ms)\n");
        System.out.println();
        try {
            for (int i = 0; i <= 100; i++) {
                long t = 0;
                for (int j = 0; j < N_EXECS; j++) {
                    ReadersWritersProblem problem = new ReadersWritersProblem(policy, i, 100 - i);
                    t += problem.run();
                }
                writer.write(i + ";" + (100 - i) + ";" + t / N_EXECS + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo bd.txt não encontrado");
        }
    }

    public static void main(String[] args) throws IOException {
        geraCSV(new ReaderPriorityPolicy(), "saida/readers-priority.csv");
        geraCSV(new WriterPriorityPolicy(), "saida/writers-priority.csv");
        geraCSV(new AlwaysBlockPolicy(), "saida/always-block.csv");

        System.out.println("Execução terminada com sucesso");
    }

    private static void geraCSV(AccessPolicy policy, String filename) throws IOException {
        File readersFile = new File(filename);
        readersFile.createNewFile();
        FileWriter writer = new FileWriter(readersFile);
        runProblem(policy, writer);
        writer.close();
    }
}
