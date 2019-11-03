package readers_writers;

import readers_writers.database.Database;
import readers_writers.database.policies.AccessPolicy;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para execução de um problema de Readers/Writers
 */
public class ReadersWritersProblem {

    Database db;
    List<Thread> threads;

    /**
     * Classe para execução de um problema de Readers/Writers
     *
     * @param policy   política de acesso ao database
     * @param nReaders número de Readers
     * @param nWriters número de Writers
     * @throws FileNotFoundException arquivo bd.txt não encontrado
     */
    public ReadersWritersProblem(AccessPolicy policy, int nReaders, int nWriters) throws FileNotFoundException {
        final Database db = new Database("bd.txt", policy);

        // Cria os leitores e escritores
        threads = new ArrayList<Thread>(nReaders + nWriters);
        for (int i = 0; i < nReaders; i++) {
            threads.add(new Reader(db));
        }
        for (int i = 0; i < nWriters; i++) {
            threads.add(new Writer(db));
        }

        // Embaralha
        for (int i = 0; i < threads.size() - 1; i++) {
            int randomPos = (int) (Math.random() * (threads.size() - i)) + i;

            Thread aux = threads.get(i);
            threads.set(i, threads.get(randomPos));
            threads.set(randomPos, aux);
        }
    }

    /**
     * Executa o problema de readers e writers
     *
     * @return tempo de execução
     */
    public long run() {
        long startTime = System.currentTimeMillis();

        // Executa os readers e writers
        for (Thread thread : threads)
            thread.start();

        // Aguarda a execução de todas threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
