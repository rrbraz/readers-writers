package readers_writers.database;

import readers_writers.database.policies.AccessPolicy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Classe que gerencia os dados a serem lidos ou escritos
 */
public class Database {

    private static final int INITIAL_CAPACITY = 36243;
    private final AccessPolicy policy;
    private ArrayList<String> data;

    /**
     * Inicializa o Database a partir de um arquivo
     *
     * @param fileName nome do arquivo a ser carregado
     * @param policy   política de acesso ao database
     * @throws FileNotFoundException caso não tenha sido encontrado o arquivo
     */
    public Database(String fileName, AccessPolicy policy) throws FileNotFoundException {
        this.data = new ArrayList<String>(INITIAL_CAPACITY);
        this.policy = policy;

        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }
    }

    /**
     * Le uma posição do Database
     *
     * @param position posição a ser lida
     * @return dados lidos
     */
    public String read(int position) {
        return this.data.get(position);
    }

    /**
     * Escreve em uma posição do Database
     *
     * @param position posição em que os dados serão escritos
     * @param content  dados a serem escritos
     */
    public void write(int position, String content) {
        this.data.set(position, content);
    }

    /**
     * Obtém o tamanho do database
     *
     * @return tamanho do database
     */
    public int size() {
        return this.data.size();
    }

    /**
     * Lê uma posição aleatória do database
     *
     * @return os dados em uma posição aleatória
     */
    public String readRandom() {
        return read(randomPos());
    }

    /**
     * Escreve em uma posição aleatória do database
     *
     * @param content conteúdo a ser escrito
     */
    public void writeRandom(String content) {
        write(randomPos(), content);
    }

    /**
     * retorna uma posição aleatória do Database
     *
     * @return retorna uma posição aleatória do Database
     */
    private int randomPos() {
        return (int) (Math.random() * this.size());
    }

    /**
     * Obtém permissão de leitura no database
     */
    public void getReadPermission() {
        this.policy.getReadPermission();
    }

    /**
     * Libera permissão de leitura no database
     */
    public void releaseReadPermission() {
        this.policy.releaseReadPermission();
    }

    /**
     * Obtém permissão de escrita no database
     */
    public void getWritePermission() {
        this.policy.getWritePermission();
    }

    /**
     * Libera permissão de escrita no database
     */
    public void releaseWritePermission() {
        this.policy.releaseWritePermission();
    }
}
