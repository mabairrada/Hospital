package Model;
import java.util.concurrent.atomic.AtomicInteger;

public class Paciente {
    private String nome;
    private String especialidade;
    private int idade;
    private boolean preferencial;
    private int senha;
    private static AtomicInteger proximaSenha = new AtomicInteger(1);
    private boolean chamado;


    public Paciente(String nome, String especialidade, int idade, boolean preferencial) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.idade = idade;
        this.preferencial = preferencial;
        this.chamado = false;
    }
    

    public void gerarSenha() {
        senha = proximaSenha.getAndIncrement();
    }

    public int getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public int getIdade() {
        return idade;
    }

    public boolean isPreferencial() {
        return preferencial;
    }

    public boolean isChamado() {
        return chamado;
    }

    public void setChamado(boolean chamado) {
        this.chamado = chamado;
    }
}
