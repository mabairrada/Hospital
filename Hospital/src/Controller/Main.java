package Controller;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Paciente;
import View.Entrada;
import View.InfoPacientes;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        iniciarPaciente(frame);
    }
    public static void iniciarPaciente(JFrame frame) {
        Entrada entrada = new Entrada();
        InfoPacientes info = new InfoPacientes();
        Fila atendimento = new Fila();

        CustomDialog customDialog = new CustomDialog(atendimento, entrada, info);
        customDialog.setVisible(true);

        customDialog.ultimoPacienteButton.addActionListener(e -> {
            Paciente ultimoPaciente = atendimento.ultimoPacienteChamado();
            if (ultimoPaciente == null) {
                JOptionPane.showMessageDialog(null, "Nenhum paciente chamado anteriormente.");
            } else {
                info.exibirPaciente(ultimoPaciente);
            }
        });

        customDialog.historicoButton.addActionListener(e -> {
            List<Paciente> historico = (List<Paciente>) atendimento.getHistoricoSenhasChamadas();
            if (historico.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum paciente chamado anteriormente.");
            } else {
                StringBuilder historicoString = new StringBuilder();
                for (Paciente paciente : historico) {
                    historicoString.append("Nome: ").append(paciente.getNome()).append("\n");
                    historicoString.append("Senha: ").append(paciente.getSenha()).append("\n");
                    historicoString.append("---------------------\n");
                }
                info.exibirHistorico();
            }
        });

        customDialog.consultarPosicaoButton.addActionListener(e -> {
            List<Paciente> pacientes = entrada.receberInformacoes();
            if (!pacientes.isEmpty()) {
                Paciente paciente = pacientes.get(0);
                int posicao = atendimento.consultarPosicao(paciente);
                JOptionPane.showMessageDialog(null, "Sua posição na fila é: " + posicao);
            }
        });

        List<Paciente> pacientes = entrada.receberInformacoes();

        for (Paciente paciente : pacientes) {
            atendimento.adicionarPaciente(paciente);
        }

        frame.setVisible(false);
    }
}
