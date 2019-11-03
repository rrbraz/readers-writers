package readers_writers.database.policies;

import java.util.concurrent.Semaphore;

/**
 * Política de acesso que dá prioridade aos Readers
 * baseado em Andrew S. Tanenbaum, Herbert Bos - Modern Operating Systems-Pearson (2014)
 */
public class ReaderPriorityPolicy implements AccessPolicy {


    private int readcount = 0;
    private Semaphore accessMutex = new Semaphore(1);
    private Semaphore mutexReadcount = new Semaphore(1);

    @Override
    public void getReadPermission() {
        try {
            // obtem permissão de alterar readcount
            mutexReadcount.acquire();

            readcount++;
            if (readcount == 1)
                // obtem acesso ao database
                accessMutex.acquire();

            // libera permissão de alterar readcount
            mutexReadcount.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseReadPermission() {
        try {
            // obtem permissão de alterar readcount
            mutexReadcount.acquire();
            readcount--;
            if (readcount == 0)
                // libera acesso ao database
                accessMutex.release();

            // libera permissão de alterar readcount
            mutexReadcount.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWritePermission() {
        try {
            // obtem acesso ao database
            accessMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseWritePermission() {
        // libera acesso ao database
        accessMutex.release();
    }
}
