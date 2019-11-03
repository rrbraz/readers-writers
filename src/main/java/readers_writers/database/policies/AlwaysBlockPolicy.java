package readers_writers.database.policies;

import java.util.concurrent.Semaphore;

/**
 * Política de acesso que sempre bloqueia acessos simultâneos, seja leitura ou escritura
 */
public class AlwaysBlockPolicy implements AccessPolicy {

    private Semaphore accessMutex = new Semaphore(1);

    @Override
    public void getReadPermission() {
        try {
            accessMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseReadPermission() {
        accessMutex.release();
    }

    @Override
    public void getWritePermission() {
        try {
            accessMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseWritePermission() {
        accessMutex.release();
    }
}
