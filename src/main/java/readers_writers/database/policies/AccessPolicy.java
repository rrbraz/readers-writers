package readers_writers.database.policies;

/**
 * Representa uma política de acesso ao Database
 */
public interface AccessPolicy {

    /**
     * Obtém permissão de leitura
     */
    public void getReadPermission();

    /**
     * Libera a permissão de leitura
     */
    public void releaseReadPermission();

    /**
     * Obtém permissão de escrita
     */
    public void getWritePermission();

    /**
     * Libera a permissão de escrita
     */
    public void releaseWritePermission();
}
