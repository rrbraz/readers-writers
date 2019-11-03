package readers_writers.database.policies;

import java.util.concurrent.Semaphore;

/**
 * Política de acesso que dá prioridade aos Writers
 * baseado em Courtois, P. J., Heymans, F., & Parnas, D. L. (1971). Concurrent control with “readers” and “writers.”
 */
public class WriterPriorityPolicy implements AccessPolicy {

    /* número de readers acessando o database */
    private int readcount = 0;
    /* número de writers acessando o database */
    private int writecount = 0;

    // permissao de alterar readcount
    private Semaphore mutexReadcount = new Semaphore(1);

    // permissão de alterar writecount
    private Semaphore mutexWritecount = new Semaphore(1);

    // permissao de entrada de writers
    private Semaphore w = new Semaphore(1);

    // permissão de entrada de readers
    private Semaphore r = new Semaphore(1);

    // mutex de controle para que garantir que não há readers e writers disputando o r.acquire
    private Semaphore controlMutex = new Semaphore(1);

    @Override
    public void getReadPermission() {
        try {
            // bloqueia o acesso ao bloco seguinte, garantindo que há no máximo uma thread aguardando o recurso r
            controlMutex.acquire();
            // aguarda permissao de novos reader
            r.acquire();

            // obtem permisao de alterar readcount
            mutexReadcount.acquire();
            readcount++;
            if (readcount == 1)
                // bloqueia acesso de writers ao db enquanto houver readers
                w.acquire();
            // libera permissao de alterar readcount
            mutexReadcount.release();

            // libera permissao de entrada de novo reader
            r.release();

            controlMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseReadPermission() {
        try {
            // obtem permissao de alterar readcount
            mutexReadcount.acquire();
            readcount--;
            if (readcount == 0)
                // era o ultimo reader, permite que writers acessem o recurso
                w.release();

            // libera permissao de alterar readcount
            mutexReadcount.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWritePermission() {
        try {
            // obtem permissao de alterar writecount
            mutexWritecount.acquire();
            writecount++;
            if (writecount == 1)
                // bloqueia entrada de novos readers, pois writers tem prioridade
                r.acquire();

            mutexWritecount.release();

            // espera permissao pra escrever
            w.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseWritePermission() {
        try {
            // libera permissão de escrever
            w.release();

            // obtem permissao de alterar writecount
            mutexWritecount.acquire();
            writecount--;
            if (writecount == 0)
                // não tem mais nenhum writer, então libera entrada de readers
                r.release();

            // libera permissao de alterar writecount
            mutexWritecount.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
