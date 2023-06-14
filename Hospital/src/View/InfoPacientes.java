package View;

import javax.swing.JOptionPane;
import Model.Paciente;

public class InfoPacientes {
    private StringBuilder historico;

    public InfoPacientes() {
        historico = new StringBuilder();
    }

    public void exibirPaciente(Paciente paciente) {
        String mensagem = "Nome: " + paciente.getNome() + "\n"
                + "Senha: " + paciente.getSenha();
        JOptionPane.showMessageDialog(null, mensagem);

        historico.append("Nome: ").append(paciente.getNome()).append("\n");
        historico.append("Senha: ").append(paciente.getSenha()).append("\n");
        historico.append("Especialidade: ").append(paciente.getEspecialidade()).append("\n");
        historico.append("Idade: ").append(paciente.getIdade()).append("\n");
        historico.append("Preferencial: ").append(paciente.isPreferencial() ? "Sim" : "Não").append("\n");
        historico.append("---------------------\n");
    }

    public void exibirHistorico() {
        JOptionPane.showMessageDialog(null, "Histórico de pacientes chamados:\n" + historico.toString());
    }
}
