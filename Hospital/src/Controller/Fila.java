package Controller;

import java.util.LinkedList;
import java.util.Queue;

import Model.Paciente;

public class Fila {
    private Queue<Paciente> filaEspera;
    private Queue<Paciente> filaAtendimento;
    private int atendimentosRealizados;
    private int contadorPreferencial;

    public Fila() {
        filaEspera = new LinkedList<>();
        filaAtendimento = new LinkedList<>();
        atendimentosRealizados = 0;
        contadorPreferencial = 0;
    }

    public void adicionarPaciente(Paciente paciente) {
        if (filaEspera.size() >= 50) {
            System.out.println("Fila cheia. Não é possível adicionar mais pacientes.");
            return;
        }

        filaEspera.add(paciente);
        System.out.println("Paciente " + paciente.getNome() + " adicionado à fila de espera.");
        System.out.println("Senha do paciente: " + paciente.getSenha());

        if (contadorPreferencial < 3 && paciente.isPreferencial()) {
            contadorPreferencial++;
            Paciente preferencial = filaEspera.remove();
            filaAtendimento.add(preferencial);
            System.out.println("Paciente preferencial " + preferencial.getNome() + " chamado para atendimento.");
            atendimentosRealizados++;
        } else if (contadorPreferencial >= 3 && !paciente.isPreferencial()) {
            Paciente normal = filaEspera.remove();
            filaAtendimento.add(normal);
            System.out.println("Paciente " + normal.getNome() + " chamado para atendimento.");
            atendimentosRealizados++;
            contadorPreferencial = 0;
        } else {
            contadorPreferencial = 0;
        }
    }

    public Paciente proximoPaciente() {
        Paciente paciente = filaEspera.poll();
        if (paciente != null) {
            filaAtendimento.add(paciente);
            System.out.println("Paciente " + paciente.getNome() + " chamado para atendimento.");
            atendimentosRealizados++;
            contadorPreferencial = 0;
        } else {
            System.out.println("Não há pacientes na fila de espera.");
        }
        return paciente;
    }

    public int consultarPosicao(Paciente paciente) {
        if (filaEspera.contains(paciente)) {
            return (int) (filaEspera.size() - filaEspera.stream().filter(p -> p.equals(paciente)).count() + 1);
        } else {
            return -1;
        }
    }

    public Paciente ultimoPacienteChamado() {
        if (filaAtendimento.isEmpty()) {
            return null;
        } else {
            return filaAtendimento.peek();
        }
    }

    public Queue<Paciente> getHistoricoSenhasChamadas() {
        return filaAtendimento;
    }
}
