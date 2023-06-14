package View;

import Model.Paciente;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Entrada {
    public List<Paciente> receberInformacoes() {
        List<Paciente> pacientes = new ArrayList<>();

        boolean inserirNovoPaciente = true;

        JButton novoPacienteButton = new JButton("Adicionar Novo Paciente");
        novoPacienteButton.addActionListener(e -> {
            adicionarNovoPaciente(pacientes);
        });

        JButton consultarPosicaoButton = new JButton("Consultar Minha Posição");
        consultarPosicaoButton.addActionListener(e -> {
            consultarPosicao(pacientes);
        });

        JButton historicoButton = new JButton("Histórico");
        historicoButton.addActionListener(e -> {
            exibirHistorico(pacientes);
        });

        JButton ultimoPacienteButton = new JButton("Ver Último Paciente");
        ultimoPacienteButton.addActionListener(e -> {
            exibirUltimoPaciente(pacientes);
        });

        JButton acessoMedicoButton = new JButton("Acesso Médico");
        acessoMedicoButton.addActionListener(e -> {
            acessarTelaMedico(pacientes);
        });

        while (inserirNovoPaciente) {
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.add(novoPacienteButton);
            buttonsPanel.add(consultarPosicaoButton);
            buttonsPanel.add(historicoButton);
            buttonsPanel.add(ultimoPacienteButton);
            buttonsPanel.add(acessoMedicoButton);

            int opcao = JOptionPane.showOptionDialog(null, buttonsPanel, "Opção", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

            if (opcao == JOptionPane.CLOSED_OPTION) {
                break;
            }

            if (opcao == 0) {
                adicionarNovoPaciente(pacientes);
            } else if (opcao == 1) {
                consultarPosicao(pacientes);
            } else if (opcao == 2) {
                exibirHistorico(pacientes);
            } else if (opcao == 3) {
                exibirUltimoPaciente(pacientes);
            } else if (opcao == 4) {
                acessarTelaMedico(pacientes);
            }
        }

        return pacientes;
    }


    public void adicionarNovoPaciente(List<Paciente> pacientes) {
        String nome = JOptionPane.showInputDialog(null, "Digite o nome do paciente:");
        String especialidade = JOptionPane.showInputDialog(null, "Digite a especialidade:");
        int idade = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a idade do paciente:"));

        boolean preferencial = (idade >= 60);

        Paciente paciente = new Paciente(nome, especialidade, idade, preferencial);
        paciente.gerarSenha();

        exibirSenha(paciente.getSenha());

        pacientes.add(paciente);
    }

    public void exibirSenha(int senha) {
        JOptionPane.showMessageDialog(null, "Senha: " + senha);
    }

    public void consultarPosicao(List<Paciente> pacientes) {
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente na fila.");
            return;
        }

        String senhaStr = JOptionPane.showInputDialog(null, "Digite sua senha:");
        int senha = Integer.parseInt(senhaStr);

        int posicao = -1;
        int contadorNormal = 0;
        int contadorPreferencial = 0;

        for (int i = 0; i < pacientes.size(); i++) {
            Paciente paciente = pacientes.get(i);
            if (paciente.getSenha() == senha) {
                posicao = i + 1;
                break;
            }

            if (paciente.isPreferencial()) {
                contadorPreferencial++;
            } else {
                contadorNormal++;
                if (contadorNormal % 3 == 0) {
                    contadorPreferencial++;
                }
            }
        }

        if (posicao != -1) {
            JOptionPane.showMessageDialog(null, "Sua posição na fila é: " + posicao);
        } else {
            JOptionPane.showMessageDialog(null, "Senha não encontrada na fila.");
        }
    }


    public void exibirHistorico(List<Paciente> pacientes) {
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente atendido ainda.");
        } else {
            StringBuilder historico = new StringBuilder("Histórico de atendimento\n");
            for (Paciente paciente : pacientes) {
            	 historico.append("Senha: ").append(paciente.getSenha()).append("\n");
                historico.append("Nome: ").append(paciente.getNome()).append("\n");
                historico.append("Especialidade: ").append(paciente.getEspecialidade()).append("\n");
                historico.append("Preferencial: ").append(paciente.isPreferencial() ? "Sim" : "Não").append("\n");
                historico.append("---------------------\n");
            
            }
            JOptionPane.showMessageDialog(null, historico.toString());
        }
    }

    public void exibirUltimoPaciente(List<Paciente> pacientes) {
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente atendido ainda.");
        } else {
            Paciente ultimoPaciente = pacientes.get(0); // Obtém o primeiro paciente da lista
            StringBuilder info = new StringBuilder();
            info.append("Último paciente atendido\n");
            info.append("Senha: ").append(ultimoPaciente.getSenha()).append("\n");
            info.append("Nome: ").append(ultimoPaciente.getNome()).append("\n");
            info.append("Especialidade: ").append(ultimoPaciente.getEspecialidade()).append("\n");
            info.append("Preferencial: ").append(ultimoPaciente.isPreferencial() ? "Sim" : "Não").append("\n");
            JOptionPane.showMessageDialog(null, info.toString());
        }
    }


    public void acessarTelaMedico(List<Paciente> pacientes) {
        Medico medico = new Medico(pacientes);

        JButton btnChamarPaciente = new JButton("Chamar próximo paciente");
        JButton btnSair = new JButton("Sair");

        btnChamarPaciente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                medico.chamarProximoPaciente();

                // Verificar se é necessário chamar um paciente preferencial
                if (medico.getIndiceAtual() % 3 == 0) {
                    chamarPacientePreferencial(pacientes);
                }
            }
        });

        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para sair do programa
                System.exit(0);
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(btnChamarPaciente);
        buttonsPanel.add(btnSair);

        JOptionPane.showOptionDialog(null, buttonsPanel, "Acesso Médico", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
    }

    private void chamarPacientePreferencial(List<Paciente> pacientes) {
        boolean pacientePreferencialChamado = false;

        for (int i = 0; i < pacientes.size(); i++) {
            Paciente paciente = pacientes.get(i);
            if (paciente.isPreferencial() && !paciente.isChamado()) {
                chamarPaciente(paciente);
                pacientePreferencialChamado = true;
                break;
            }
        }

        if (!pacientePreferencialChamado) {
            JOptionPane.showMessageDialog(null, "Não há mais pacientes preferenciais a serem chamados.");
        }
    }

    private void chamarPaciente(Paciente paciente) {
        paciente.setChamado(true);
        String mensagem = "Nome: " + paciente.getNome() + "\n"
                + "Senha: " + paciente.getSenha() + "\n"
                + "Especialidade: " + paciente.getEspecialidade() + "\n"
                + "Preferencial: " + (paciente.isPreferencial() ? "Sim" : "Não");
        JOptionPane.showMessageDialog(null, mensagem);
    }
}