package Controller;

import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import Model.Paciente;
import View.Entrada;
import View.InfoPacientes;

public class CustomDialog extends JDialog {
    JButton ultimoPacienteButton;
    JButton historicoButton;
    JButton consultarPosicaoButton;

    private Fila atendimento;
    private Entrada entrada;
    private InfoPacientes info;
	public Object ultimoPacienteButton1;

    public CustomDialog(Fila atendimento, Entrada entrada, InfoPacientes info) {
        this.atendimento = atendimento;
        this.entrada = entrada;
        this.info = info;

        setTitle("Escolha uma opção");
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(300, 25-+
        		
        		0); // Aumentar o tamanho da janela

        // Botão "Último Paciente Chamado"
        ultimoPacienteButton = new JButton("Último Paciente Chamado");
        ultimoPacienteButton.setBounds(50, 50, 200, 30);
        add(ultimoPacienteButton);

        // Botão "Histórico"
        historicoButton = new JButton("Histórico");
        historicoButton.setBounds(50, 100, 200, 30);
        add(historicoButton);

        // Botão "Consultar Minha Posição"
        consultarPosicaoButton = new JButton("Consultar Minha Posição");
        consultarPosicaoButton.setBounds(50, 150, 200, 30);
        add(consultarPosicaoButton);

        // Adicionar a ação ao botão "Último Paciente Chamado"
        ultimoPacienteButton.addActionListener(e -> {
            Paciente ultimoPaciente = atendimento.ultimoPacienteChamado();
            if (ultimoPaciente == null) {
                JOptionPane.showMessageDialog(null, "Nenhum paciente chamado anteriormente.");
            } else {
                info.exibirPaciente(ultimoPaciente);
            }
        });

        // Adicionar a ação ao botão "Histórico"
        historicoButton.addActionListener(e -> {
            Queue<Paciente> historico = atendimento.getHistoricoSenhasChamadas();
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

        // Adicionar a ação ao botão "Consultar Minha Posição"
        consultarPosicaoButton.addActionListener(e -> {
            Paciente paciente = (Paciente) entrada.receberInformacoes();
            int posicao = atendimento.consultarPosicao(paciente);
            JOptionPane.showMessageDialog(null, "Sua posição na fila é: " + posicao);
        });
    }

    public void CustomDialog(Fila atendimento2, Entrada entrada2, InfoPacientes info2) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
        Entrada entrada = new Entrada();
        InfoPacientes info = new InfoPacientes();
        Fila atendimento = new Fila();

        List<Paciente> pacientes = entrada.receberInformacoes();

        for (Paciente paciente : pacientes) {
            atendimento.adicionarPaciente(paciente);
        }

        // Criar a janela personalizada
        CustomDialog customDialog = new CustomDialog(atendimento, entrada, info);
        customDialog.setVisible(true);
    }
}