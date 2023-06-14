package View;

import Model.Paciente;
import java.util.List;

import javax.swing.JOptionPane;

public class Medico {
    private List<Paciente> pacientes; // Lista de pacientes a serem atendidos
    private int indiceAtual; // Índice do próximo paciente a ser chamado
    private int contadorPreferencial; // Contador para pacientes preferenciais

    public Medico(List<Paciente> pacientes) {
        this.pacientes = pacientes;
        this.indiceAtual = 0;
        this.contadorPreferencial = 0;
    }

    public void chamarProximoPaciente() {
        if (indiceAtual < pacientes.size()) {
            Paciente proximoPaciente = pacientes.get(indiceAtual);

            // Verificar se é necessário chamar um paciente preferencial
            if (contadorPreferencial == 3) {
                // Encontrar o próximo paciente preferencial
                Paciente pacientePreferencial = encontrarPacientePreferencial();
                if (pacientePreferencial != null) {
                    proximoPaciente = pacientePreferencial;
                    pacientes.remove(pacientePreferencial);
                    contadorPreferencial = 0;
                } else {
                    contadorPreferencial++;
                }
            } else {
                contadorPreferencial++;
            }

            // Exibir as informações do próximo paciente
            String mensagem = "Nome: " + proximoPaciente.getNome() + "\n"
                    + "Senha: " + proximoPaciente.getSenha() + "\n"
                    + "Especialidade: " + proximoPaciente.getEspecialidade() + "\n"
                    + "Preferencial: " + (proximoPaciente.isPreferencial() ? "Sim" : "Não");
            JOptionPane.showMessageDialog(null, mensagem);

            indiceAtual++;
        } else {
            JOptionPane.showMessageDialog(null, "Não há mais pacientes na fila.");
        }
    }

    private Paciente encontrarPacientePreferencial() {
        for (Paciente paciente : pacientes) {
            if (paciente.isPreferencial()) {
                return paciente;
            }
        }
        return null;
    }


    public int getIndiceAtual() {
        return indiceAtual;
    }

}
