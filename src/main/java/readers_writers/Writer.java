package readers_writers;
import readers_writers.database.*;

/**
 * Classe que faz escritas no Database
 */
public class Writer extends Thread {

    /** Número de escritas do Writes**/
    private static final int N_READS = 100;
    /** Database a ser escrito **/
    private final Database db;


    /** Constrói um Writer a partir de um Database **/
    public Writer(Database db) {
        this.db = db;
    }

    /**
     * Executa o leitor.
     * Lê {@code N_READS} posições aleatórias no readers_writers.database
     */
    @Override
    public void run() {
        db.getWritePermission();
        for (int i=0; i<N_READS; i++) {
            db.writeRandom("MODIFICADO");
        }

        // dorme 1ms
        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        db.releaseWritePermission();
    }
}
