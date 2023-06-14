package Model;

public class Senha {
    private static int senhaAtual = 0;

    public int getSenhaAtual() {
        return senhaAtual;
    }

    public void gerarSenha() {
        senhaAtual++;
    }

	
}
	