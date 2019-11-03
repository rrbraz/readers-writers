package readers_writers.database.policies;

/**
 * Política de acesso que nunca bloqueia acessos simultâneos, seja leitura ou escritura
 */
public class NeverBlockPolicy implements AccessPolicy {

    @Override
    public void getReadPermission() {
    }

    @Override
    public void releaseReadPermission() {
    }

    @Override
    public void getWritePermission() {
    }

    @Override
    public void releaseWritePermission() {
    }
}
