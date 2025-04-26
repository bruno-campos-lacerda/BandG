package RemoteControlRace;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class Cronometro extends WindowAdapter implements AtualizadorTela {
    private Frame window;
    private Panel countdown,buttons,teamTime;
    private TextField txtMin, txtSeg, txtMil,tTeamOne,tTeamTwo,tTeamOneMemberOne,tTeamOneMemberTwo,tTeamOneMemberThree,tTeamTwoMemberOne,tTeamTwoMemberTwo,tTeamTwoMemberThree;
    private Label lTeamOne,lTeamTwo,lTeamOneMemberOne,lTeamOneMemberTwo,lTeamOneMemberThree,lTeamTwoMemberOne,lTeamTwoMemberTwo,lTeamTwoMemberThree;
    private Button btnIniciar, btnVolta, btnPara, btnReset, btnEquipe1, btnEquipe2;
    private List listaVoltasEquipe1, listaVoltasEquipe2;
    
    private Temporizador timer, temporizadorEquipe1, temporizadorEquipe2;
    //private int ultimaVoltaEquipe1 = 0, ultimaVoltaEquipe2 = 0;
    private int[] teamLastLap = {0,0}, lastTeamMin = {0,0}, lastTeamSeg = {0,0}, lastTeamMil = {0,0}; 
    private int lastMin=0, lastSeg=0, lastMil=0;
    private ArrayList<String> temposEquipe1 = new ArrayList<>();
    private ArrayList<String> temposEquipe2 = new ArrayList<>();
    private Vector teams;

    public Cronometro() {
        teams = new Vector();
        
        window = new Frame();
        window.setLayout(null);
        window.setTitle("Cronômetro AWT");
        window.setSize(400, 450);
        window.setVisible(true);

        // Campos para tempo
        countdown = new Panel();
        countdown.setLayout(null);
        countdown.setLocation(20, 35);
        countdown.setSize(360, 20);
        
        txtMin = new TextField("0", 5);
        txtMin.setEnabled(false);
        txtSeg = new TextField("0", 5);
        txtSeg.setEnabled(false);
        txtMil = new TextField("0", 5);
        txtMil.setEnabled(false);
        
        txtMin.setBounds(0,0,110,19);
        txtSeg.setBounds(125,0,110,19);
        txtMil.setBounds(250,0,110,19);
        
        countdown.add(txtMin);
        countdown.add(txtSeg);
        countdown.add(txtMil);

        // Botões
        buttons = new Panel();
        buttons.setLayout(null);
        //buttons.setBackground(new Color(80,80,80));
        buttons.setLocation(67, 75);
        //buttons.setSize(205, 60);
        buttons.setSize(265,70);
        
        btnIniciar = new Button("Iniciar");
        btnIniciar.addActionListener(e -> iniciarCronometro());
        btnPara = new Button("Parar");
        btnPara.addActionListener(e -> parar());
        btnReset = new Button("Resetar");
        btnReset.addActionListener(e -> resetar());
        btnVolta = new Button("Volta");
        btnVolta.addActionListener(e -> volta());
        btnEquipe1 = new Button("Equipe 1");
        btnEquipe1.addActionListener(e -> salvarTempoEquipe(0, timer));
        btnEquipe2 = new Button("Equipe 2");
        btnEquipe2.addActionListener(e -> salvarTempoEquipe(1, timer));
        
        btnIniciar.setBounds(0,5,85,31);
        btnPara.setBounds(90,5,85,31);
        btnReset.setBounds(180,5,85,31);
        btnVolta.setBounds(0,38,85,31);
        btnEquipe1.setBounds(90,38,85,31);
        btnEquipe2.setBounds(180,38,85,31);
        
        buttons.add(btnIniciar);
        buttons.add(btnPara);
        buttons.add(btnVolta);
        buttons.add(btnReset);
        buttons.add(btnEquipe1);
        buttons.add(btnEquipe2);

        // Listas de voltas e cadastro de equipes
        teamTime = new Panel();
        teamTime.setLayout(null);
        teamTime.setBackground(new Color(220,220,220));
        teamTime.setLocation(10, 150);
        teamTime.setSize(380, 275);
        
        lTeamOne = new Label("Equipe 1");
        lTeamTwo = new Label("Equipe 2");
        lTeamOneMemberOne = new Label("Membro 1");
        lTeamOneMemberTwo = new Label("Membro 2");
        lTeamOneMemberThree = new Label("Membro 3");
        lTeamTwoMemberOne = new Label("Membro 1");
        lTeamTwoMemberTwo = new Label("Membro 2");
        lTeamTwoMemberThree = new Label("Membro 3");
        
        tTeamOne = new TextField(30);
        tTeamTwo = new TextField(30);
        tTeamOneMemberOne = new TextField(30);
        tTeamOneMemberTwo = new TextField(30);
        tTeamOneMemberThree = new TextField(30);
        tTeamTwoMemberOne = new TextField(30);
        tTeamTwoMemberTwo = new TextField(30);
        tTeamTwoMemberThree = new TextField(30);
        
        lTeamOne.setBounds(0,0,200,13);
        tTeamOne.setBounds(10,13,200,19);
        lTeamOneMemberOne.setBounds(15,32,200,13);
        tTeamOneMemberOne.setBounds(25,45,200,19);
        lTeamOneMemberTwo.setBounds(15,64,200,13);
        tTeamOneMemberTwo.setBounds(25,77,200,19);
        lTeamOneMemberThree.setBounds(15,96,200,13);
        tTeamOneMemberThree.setBounds(25,109,200,19);
        lTeamTwo.setBounds(0,128,200,13);
        tTeamTwo.setBounds(10,141,200,19);
        lTeamTwoMemberOne.setBounds(15,160,200,13);
        tTeamTwoMemberOne.setBounds(25,173,200,19);
        lTeamTwoMemberTwo.setBounds(15,192,200,13);
        tTeamTwoMemberTwo.setBounds(25,205,200,19);
        lTeamTwoMemberThree.setBounds(15,224,200,13);
        tTeamTwoMemberThree.setBounds(25,237,200,19);
        
        teamTime.add(lTeamOne);
        teamTime.add(tTeamOne);
        teamTime.add(lTeamTwo);
        teamTime.add(tTeamTwo);
        teamTime.add(lTeamOneMemberOne);
        teamTime.add(tTeamOneMemberOne);
        teamTime.add(lTeamOneMemberTwo);
        teamTime.add(tTeamOneMemberTwo);
        teamTime.add(lTeamOneMemberThree);
        teamTime.add(tTeamOneMemberThree);
        teamTime.add(lTeamTwoMemberOne);
        teamTime.add(tTeamTwoMemberOne);
        teamTime.add(lTeamTwoMemberTwo);
        teamTime.add(tTeamTwoMemberTwo);
        teamTime.add(lTeamTwoMemberThree);
        teamTime.add(tTeamTwoMemberThree);
        
        listaVoltasEquipe1 = new List(10);
        listaVoltasEquipe2 = new List(10);
        
        listaVoltasEquipe1.setBounds(245,13,128,120);
        listaVoltasEquipe2.setBounds(245,141,130,120);

        teamTime.add(listaVoltasEquipe1);
        teamTime.add(listaVoltasEquipe2);

        // Inicialização dos temporizadores
        //temporizadorEquipe1 = new Temporizador(this, 1);
        //temporizadorEquipe2 = new Temporizador(this, 2);
        timer = new Temporizador(this,0);

        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //temporizadorEquipe1.parar();
                //temporizadorEquipe2.parar();
                //dispose();
                System.exit(0);
            }
        });
        window.add(countdown);
        window.add(buttons);
        window.add(teamTime);
    }

    private void iniciarCronometro() {
        //temporizadorEquipe1.iniciar();
        //temporizadorEquipe2.iniciar();
        timer.iniciar();
        btnIniciar.setEnabled(false);
    }

    private void volta() {
        // Adiciona a volta de cada equipe (calculando o tempo da última volta)
        //adicionarVolta(1, temporizadorEquipe1);
        //adicionarVolta(2, temporizadorEquipe2);
        adicionarVolta(timer);
    }
    
    private void parar(){
        //temporizadorEquipe1.parar();
        //temporizadorEquipe2.parar();
        timer.parar();
        btnIniciar.setEnabled(true);
    }

    private void resetar() {
        //temporizadorEquipe1.parar();
        //temporizadorEquipe2.parar();
        //temporizadorEquipe1.zerar();
        //temporizadorEquipe2.zerar();
        timer.zerar();
        txtMin.setText("0");
        txtSeg.setText("0");
        txtMil.setText("0");
        listaVoltasEquipe1.removeAll();
        listaVoltasEquipe2.removeAll();
        teamLastLap[0] = 0;
        teamLastLap[1] = 0;
        lastTeamMin[0] = 0;
        lastTeamMin[1] = 0;
        lastTeamSeg[0] = 0;
        lastTeamSeg[1] = 0;
        lastTeamMil[0] = 0;
        lastTeamMil[1] = 0;
        //ultimaVoltaEquipe1 = 0;
        //ultimaVoltaEquipe2 = 0;
        lastMin = 0;
        lastSeg = 0;
        lastMin = 0;
        temposEquipe1.clear();
        temposEquipe2.clear();
    }

    /*private void adicionarVolta(int equipe, Temporizador temporizador) {
        int tempoAtual = temporizador.getTempoTotal();
        int ultimaVolta = equipe == 1 ? ultimaVoltaEquipe1 : ultimaVoltaEquipe2;
        int duracao = tempoAtual - ultimaVolta;
        
        if (equipe == 1) {
            ultimaVoltaEquipe1 = tempoAtual;
        } else {
            ultimaVoltaEquipe2 = tempoAtual;
        }

        // Calcula tempo em minutos, segundos e milissegundos
        int min = duracao / 60000;
        int seg = (duracao % 60000) / 1000;
        int mil = duracao % 1000;

        // Registra na lista da equipe correspondente
        String volta = "Volta " + (equipe == 1 ? listaVoltasEquipe1.getItemCount() + 1 : listaVoltasEquipe2.getItemCount() + 1) + " - " + min + ":" + seg + ":" + mil;
        if (equipe == 1) {
            listaVoltasEquipe1.add(volta);
        } else {
            listaVoltasEquipe2.add(volta);
        }

        // Salva no ArrayList de tempos
        if (equipe == 1) {
            temposEquipe1.add(volta);
        } else {
            temposEquipe2.add(volta);
        }
    }*/
    private void adicionarVolta(Temporizador temporizador) {
        int min = temporizador.getMin() - lastMin;
        int seg = temporizador.getSeg() - lastSeg;
        int mil = temporizador.getMil() - lastMil;
        for(int i = 0; i < 2; i++){
            this.lastTeamMin[i] += min;
            this.lastTeamSeg[i] += seg;
            this.lastTeamMil[i] += mil;
            this.teamLastLap[i]++;
        }
        String volta = min + ":" + seg + ":" + mil;
        
        lastMin += min;
        lastSeg += seg;
        lastMil += mil;
        
        listaVoltasEquipe1.add("Volta " + this.teamLastLap[0] + " - " + volta);
        listaVoltasEquipe2.add("Volta " + this.teamLastLap[1] + " - " + volta);
        temposEquipe1.add("Volta " + this.teamLastLap[0] + " - " + volta);
        temposEquipe2.add("Volta " + this.teamLastLap[1] + " - " + volta);
    }

    /*private void salvarTempoEquipe(int equipe) {
        // Salva e exibe o tempo da equipe no momento em que o botão é pressionado
        int tempoAtual = (equipe == 1) ? temporizadorEquipe1.getTempoTotal() : temporizadorEquipe2.getTempoTotal();
        int min = tempoAtual / 60000;
        int seg = (tempoAtual % 60000) / 1000;
        int mil = tempoAtual % 1000;
        String tempoSalvo = min + ":" + seg + ":" + mil;

        // Exibe os tempos no console
        System.out.println("Tempo da Equipe " + equipe + ": " + tempoSalvo);

        // Adiciona o tempo à lista de tempos da equipe
        if (equipe == 1) {
            temposEquipe1.add(tempoSalvo);
        } else {
            temposEquipe2.add(tempoSalvo);
        }

        // Exibe o tempo da equipe na lista de voltas
        if (equipe == 1) {
            listaVoltasEquipe1.add("Equipe 1 - Tempo: " + tempoSalvo);
        } else {
            listaVoltasEquipe2.add("Equipe 2 - Tempo: " + tempoSalvo);
        }
    }*/
    private void salvarTempoEquipe(int equipe, Temporizador temporizador) {
        // Salva e exibe o tempo da equipe no momento em que o botão é pressionado
        //int tempoAtual = (equipe == 1) ? temporizadorEquipe1.getTempoTotal() : temporizadorEquipe2.getTempoTotal();
        int min = temporizador.getMin() - lastTeamMin[equipe];
        int seg = temporizador.getSeg() - lastTeamSeg[equipe];
        int mil = temporizador.getMil() - lastTeamMil[equipe];
        String tempoSalvo = min + ":" + seg + ":" + mil;
        
        //Reseta o tempo da volta
        lastTeamMin[equipe] += min;
        lastTeamSeg[equipe] += seg;
        lastTeamMil[equipe] += mil;
        //Adiciona mais uma volta a equipe
        teamLastLap[equipe]++;

        // Exibe os tempos no console
        String volta = "Volta " + this.teamLastLap[equipe] + " - " + tempoSalvo;
        //System.out.println("Volta " + this.teamLastLap[equipe] + " - " + tempoSalvo);
        
        if (equipe == 0) {
            // Adiciona o tempo à lista de tempos da equipe1
            temposEquipe1.add(tempoSalvo);
            // Exibe o tempo da equipe1 na lista de voltas
            listaVoltasEquipe1.add(volta);
        } else if(equipe == 1) {
            // Adiciona o tempo à lista de tempos da equipe2
            temposEquipe2.add(tempoSalvo);
            // Exibe o tempo da equipe2 na lista de voltas
            listaVoltasEquipe2.add(volta);
        }
    }
    
    @Override
    public void atualizarTempo(int min, int seg, int mil) {
        txtMin.setText(String.valueOf(min));
        txtSeg.setText(String.valueOf(seg));
        txtMil.setText(String.valueOf(mil));
    }

    public static void main(String[] args) {
        new Cronometro();
    }
}