package readers_writers;

import readers_writers.database.Database;

/**
 * Classe que faz leituras no Database
 */
public class Reader extends Thread {

    /**
     * Número de leituros do Reader
     **/
    private static final int N_READS = 100;
    /**
     * Database a ser lido
     **/
    private final Database db;
    /**
     * Variável que armazena os dados lidos
     **/
    private String[] data = new String[N_READS];


    /**
     * Constrói um reader a partir de um Database
     **/
    public Reader(Database db) {
        this.db = db;
    }

    /**
     * Executa o leitor.
     * Lê {@code N_READS} posições aleatórias no readers_writers.database
     */
    @Override
    public void run() {
        db.getReadPermission();
        for (int i = 0; i < N_READS; i++) {
            data[i] = db.readRandom();
        }

        // dorme 1ms
        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        db.releaseReadPermission();
    }

}
