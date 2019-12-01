package readers_writers.database.policies;

import java.util.concurrent.Semaphore;

/**
 * Política de acesso que dá prioridade aos Writers
 * baseado em Samuel Tardieu - The third readers-writers problem (2011)
 * https://rfc1149.net/blog/2011/01/07/the-third-readers-writers-problem/
 */
public class WriterPriorityPolicy implements AccessPolicy {

    /** Semáforo para controlar o recurso ao database **/
    private Semaphore accessMutex = new Semaphore(1);

    /** Semáforo para controlar o acesso ao readCount **/
    private Semaphore readersMutex = new Semaphore(1);

    /** semaforo para controlar a ordem de espera **/
    private Semaphore orderMutex = new Semaphore(1);

    /** Conta os readers na região crítica **/
    int readCount = 0;


    @Override
    public void getReadPermission() {
        try {
            // Lembra a ordem de chegada
            orderMutex.acquire();

            // Vamos alterar o readCount
            readersMutex.acquire();
            readCount++;
            if (readCount == 1)
                accessMutex.acquire();

            readersMutex.release();
            orderMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseReadPermission() {
        try {
            // Vamos alterar o readCount
            readersMutex.acquire();
            readCount--;
            if (readCount == 0)
                // libera o database
                accessMutex.release();
            readersMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWritePermission() {
        try {
            // Lembra a ordem de chegada
            orderMutex.acquire();

            // Obtém acesso ao database
            accessMutex.acquire();
            orderMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseWritePermission() {
        // libera o database
        accessMutex.release();
    }
}
