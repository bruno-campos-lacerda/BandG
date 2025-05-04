package byg;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.sql.*;
import javax.swing.JOptionPane;

public class Cronometro extends WindowAdapter implements AtualizadorTela {
    private Frame window;
    private Panel countdown,buttons,teamTime,teamManager, ranking;
    private TextField txtMin, txtSeg, txtMil,tTeamOne,tTeamTwo,tTeamOneMemberOne,tTeamOneMemberTwo,tTeamOneMemberThree,tTeamTwoMemberOne,tTeamTwoMemberTwo,tTeamTwoMemberThree;
    private Label lTeamOne,lTeamTwo,lTeamOneMemberOne,lTeamOneMemberTwo,lTeamOneMemberThree,lTeamTwoMemberOne,lTeamTwoMemberTwo,lTeamTwoMemberThree;
    private Button btnIniciar, btnVolta, btnPara, btnReset, btnEquipe1, btnEquipe2,btnSignUp,btnLogin,btnDelet;
    private List listaVoltasEquipe1, listaVoltasEquipe2, listaRank;
    
    private Temporizador timer;
    private int[] teamLastLap = {0,0}, lastTeamMin = {0,0}, lastTeamSeg = {0,0}, lastTeamMil = {0,0}, lastTeamTotal = {0,0};
    private ArrayList<String> temposEquipe1 = new ArrayList<>();
    private ArrayList<String> temposEquipe2 = new ArrayList<>();
    private boolean logged = false;
    
    public Cronometro() {        
        window = new Frame();
        window.setLayout(null);
        window.setTitle("Cronômetro AWT");
        window.setBackground(new Color(235,235,235));
        window.setSize(570, 480);
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
        buttons.setLocation(67, 75);
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
        
        listaVoltasEquipe1.setBounds(245,13,130,120);
        listaVoltasEquipe2.setBounds(245,141,130,120);

        teamTime.add(listaVoltasEquipe1);
        teamTime.add(listaVoltasEquipe2);

        //Botoes para gerenciamento de equipes
        teamManager = new Panel();
        teamManager.setLayout(null);
        teamManager.setBackground(new Color(80,80,80));
        teamManager.setLocation(0,430);
        teamManager.setSize(400,39);
        
        btnSignUp = new Button("Cadastar");
        btnSignUp.addActionListener(e -> CadastrarEquipes());
        btnLogin = new Button("Entrar");
        btnLogin.addActionListener(e -> ChamarEquipes());
        //btnUpdate = new Button("Atualizar");
        //btnUpdate.addActionListener(e -> AtualizarEquipes());
        btnDelet = new Button("Deletar");
        btnDelet.addActionListener(e -> DeletarEquipes());
        
        btnSignUp.setBounds(12,4,85,31);
        btnLogin.setBounds(157,4,85,31);
        //btnUpdate.setBounds(206,4,85,31);
        btnDelet.setBounds(303,4,85,31);
        
        teamManager.add(btnSignUp);
        teamManager.add(btnLogin);
        //teamManager.add(btnUpdate);
        teamManager.add(btnDelet);
        
        //Rank de melhores voltas
        ranking = new Panel();
        ranking.setLayout(null);
        ranking.setBackground(new Color(90,90,90));
        ranking.setLocation(400, 30);
        ranking.setSize(160, 440);
        
        listaRank = new List(10);
        listaRank.setBounds(5,5,150,430);
        
        ranking.add(listaRank);
        
        // Inicialização dos temporizadores
        timer = new Temporizador(this,0);

        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        window.add(countdown);
        window.add(buttons);
        window.add(teamTime);
        window.add(teamManager);
        window.add(ranking);
        
        btnEquipe1.setEnabled(false);
        btnEquipe2.setEnabled(false);
        btnVolta.setEnabled(false);
    }

    private void iniciarCronometro() {
        timer.iniciar();
        btnIniciar.setEnabled(false);
        btnEquipe1.setEnabled(true);
        btnEquipe2.setEnabled(true);
        btnVolta.setEnabled(true);
    }

    private void volta() {
        // Adiciona a volta de cada equipe (calculando o tempo da última volta)
        adicionarVolta(timer);
    }
    
    private void parar(){
        timer.parar();
        btnIniciar.setEnabled(true);
        btnEquipe1.setEnabled(false);
        btnEquipe2.setEnabled(false);
        btnVolta.setEnabled(false);
    }

    private void resetar() {
        timer.parar();
        timer.zerar();
        SetTeamOne("");
        SetTeamTwo("");
        SetTeamOneMemberOne("");
        SetTeamOneMemberTwo("");
        SetTeamOneMemberThree("");
        SetTeamTwoMemberOne("");
        SetTeamTwoMemberTwo("");
        SetTeamTwoMemberThree("");
        txtMin.setText("0");
        txtSeg.setText("0");
        txtMil.setText("0");
        listaVoltasEquipe1.removeAll();
        listaVoltasEquipe2.removeAll();
        listaRank.removeAll();
        btnIniciar.setEnabled(true);
        btnEquipe1.setEnabled(false);
        btnEquipe2.setEnabled(false);
        btnVolta.setEnabled(false);
        teamLastLap[0] = 0;
        teamLastLap[1] = 0;
        lastTeamMin[0] = 0;
        lastTeamMin[1] = 0;
        lastTeamSeg[0] = 0;
        lastTeamSeg[1] = 0;
        lastTeamMil[0] = 0;
        lastTeamMil[1] = 0;
        lastTeamTotal[0] = 0;
        lastTeamTotal[1] = 0;
        temposEquipe1.clear();
        temposEquipe2.clear();
    }
    
    private void adicionarVolta(Temporizador temporizador) {
        salvarTempoEquipe(0,timer);
        salvarTempoEquipe(1,timer);
    }
    
    private void salvarTempoEquipe(int equipe, Temporizador temporizador) {
        // Salva e exibe o tempo da equipe no momento em que o botão é pressionado
        int min = temporizador.getMin() - lastTeamMin[equipe];
        int seg = temporizador.getSeg() - lastTeamSeg[equipe];
        int mil = temporizador.getMil() - lastTeamMil[equipe];
        int tot;
        String tempoSalvo = min + ":" + seg + ":" + mil;
        
        //Reseta o tempo da volta
        lastTeamMin[equipe] += min;
        lastTeamSeg[equipe] += seg;
        lastTeamMil[equipe] += mil;
        //Adiciona mais uma volta a equipe
        teamLastLap[equipe]++;

        // Exibe os tempos no console
        String volta = "Volta " + this.teamLastLap[equipe] + " - " + tempoSalvo;
        
        if (equipe == 0) {
            // Adiciona o tempo à lista de tempos da equipe1
            temposEquipe1.add(volta);
            // Exibe o tempo da equipe1 na lista de voltas
            listaVoltasEquipe1.add(volta);
            if(teamLastLap[0] >= 2){
                tot = temporizador.getTempoTotal();
                btnEquipe1.setEnabled(false);
                btnVolta.setEnabled(false);
            }
        } else if(equipe == 1) {
            // Adiciona o tempo à lista de tempos da equipe2
            temposEquipe2.add(volta);
            // Exibe o tempo da equipe2 na lista de voltas
            listaVoltasEquipe2.add(volta);
            if(teamLastLap[1] >= 2){
                tot = temporizador.getTempoTotal();
                btnEquipe2.setEnabled(false);
                btnVolta.setEnabled(false);
            }
        }
        if(!this.btnEquipe1.isEnabled() && !this.btnEquipe2.isEnabled()){
            timer.parar();
            AtualizarEquipes();
            //btnUpdate.setEnabled(false);
        }
    }
    
    private void SetTeamOne(String name){
        tTeamOne.setText(name);
    }
    private void SetTeamOneMemberOne(String name){
        tTeamOneMemberOne.setText(name);
    }
    private void SetTeamOneMemberTwo(String name){
        tTeamOneMemberTwo.setText(name);
    }
    private void SetTeamOneMemberThree(String name){
        tTeamOneMemberThree.setText(name);
    }
    private void SetTeamTwo(String name){
        tTeamTwo.setText(name);
    }
    private void SetTeamTwoMemberOne(String name){
        tTeamTwoMemberOne.setText(name);
    }
    private void SetTeamTwoMemberTwo(String name){
        tTeamTwoMemberTwo.setText(name);
    }
    private void SetTeamTwoMemberThree(String name){
        tTeamTwoMemberThree.setText(name);
    }
    private void clearRank(){
        listaRank.removeAll();
    }
    private void setRank(String rank){
        listaRank.add(rank);
    }
    private String getTeamOne(){
        return this.tTeamOne.getText();
    }
    private String getTeamOneMemberOne(){
        return this.tTeamOneMemberOne.getText();
    }
    private String getTeamOneMemberTwo(){
        return this.tTeamOneMemberTwo.getText();
    }
    private String getTeamOneMemberThree(){
        return this.tTeamOneMemberThree.getText();
    }
    private String getTeamTwo(){
        return this.tTeamTwo.getText();
    }
    private String getTeamTwoMemberOne(){
        return this.tTeamTwoMemberOne.getText();
    }
    private String getTeamTwoMemberTwo(){
        return this.tTeamTwoMemberTwo.getText();
    }
    private String getTeamTwoMemberThree(){
        return this.tTeamTwoMemberThree.getText();
    }
    private String getTeamOneTime(){
        String time = "";
        for(int i = 0; i < this.temposEquipe1.size(); i++){
            time += this.temposEquipe1.get(i) + "\n";
        }
        return time;
    }
    private String getTeamTwoTime(){
        String time = "";
        for(int i = 0; i < this.temposEquipe2.size(); i++){
            time += this.temposEquipe2.get(i) + "\n";
        }
        return time;
    }
    private String getTeamOneRank(){
        return lastTeamMin[0]+":"+lastTeamSeg[0]+":"+lastTeamMil[0];
    }
    private String getTeamTwoRank(){
        return lastTeamMin[1]+":"+lastTeamSeg[1]+":"+lastTeamMil[1];
    }
    
    Connection Conecta(){
        //Procedure para insercao: CALL signUp(_TEAM_NAME, _MEMBER_ONE, _MEMBER_TWO, _MEMBER_THREE, _LAP);
        //Procedure para insercao de volta: CALL addLap(_TEAM_NAME, _LAP);
        //Procedure para chamar equipes: CALL call_team(_TEAM_NAME);
        //Procedure para deletar equipe: CALL delete_team(_TEAM_NAME);
        //PROCEDURE para criar rank: CALL call_laps(_TEAM_ONE, _TEAM_TWO);

        //Caminho para o banco de dados
        String url="jdbc:mysql://localhost/byg";
	Connection con;
	try{
            //Cria a conexao com o banco de dados
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,"root","");
            return con;
	}//Falha ao conectar com o banco
	catch(ClassNotFoundException cnf){
            JOptionPane.showMessageDialog(null,"Houve um erro no DRIVER: classnotfoundexcepition-"+cnf);
            return null;
	}
        catch(SQLException sql){
            JOptionPane.showMessageDialog(null,"Houve um erro no SQL:sqlexception sql-"+sql);
            return null;
        }
    }
    
    private void CadastrarEquipes(){
        Connection con = Conecta();
        try{
            Statement st = con.createStatement();
            st.executeUpdate("CALL signUp('"+getTeamOne()+"','"+getTeamOneMemberOne()+"','"+getTeamOneMemberTwo()+"','"+getTeamOneMemberThree()+"','"+getTeamOneTime()+"','"+getTeamOneRank()+"');");
            st.executeUpdate("CALL signUp('"+getTeamTwo()+"','"+getTeamTwoMemberOne()+"','"+getTeamTwoMemberTwo()+"','"+getTeamTwoMemberThree()+"','"+getTeamTwoTime()+"','"+getTeamTwoRank()+"');");
            st.close();
            con.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Não foi possivel salvar\n" + e);
        }
    }
    private void ChamarEquipes(){
        Connection con = Conecta();
        try{
            String equipe1 = getTeamOne();
            Statement stOne = con.createStatement();
            ResultSet rsOne = stOne.executeQuery("CALL call_team('"+equipe1+"','')");
            while(rsOne.next()){
                this.SetTeamOne(rsOne.getString(1));
                this.SetTeamOneMemberOne(rsOne.getString(2));
                this.SetTeamOneMemberTwo(rsOne.getString(3));
                this.SetTeamOneMemberThree(rsOne.getString(4));
                equipe1 = getTeamOne();
            }
            rsOne.close();
            
            String equipe2 = getTeamTwo();
            Statement stTwo = con.createStatement();
            ResultSet rsTwo = stTwo.executeQuery("CALL call_team('"+equipe2+"','"+equipe1+"')");
            while(rsTwo.next()){
                this.SetTeamTwo(rsTwo.getString(1));
                this.SetTeamTwoMemberOne(rsTwo.getString(2));
                this.SetTeamTwoMemberTwo(rsTwo.getString(3));
                this.SetTeamTwoMemberThree(rsTwo.getString(4));
                equipe2 = getTeamTwo();
            }
            rsTwo.close();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("CALL call_laps('"+equipe1+"','"+equipe2+"')");
            this.clearRank();
            while(rs.next()){
                String rank = rs.getString(1)+" - "+rs.getString(2);
                this.setRank(rank);
            }
            st.close();
            con.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Não foi encontrada a equipe\n"+e);
        }
    }
    private void AtualizarEquipes(){
        Connection con = Conecta();
        try{
            Statement st = con.createStatement();
            st.executeUpdate("CALL addLap('"+getTeamOne()+"','"+getTeamOneTime()+"','"+getTeamOneRank()+"');");
            st.executeUpdate("CALL addLap('"+getTeamTwo()+"','"+getTeamTwoTime()+"','"+getTeamTwoRank()+"');");
            st.close();
            con.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Não foi possivel adicionar\n" + e);
        }
    }
    private void DeletarEquipes(){
        Connection con = Conecta();
        try{
            Statement st = con.createStatement();
            st.executeUpdate("CALL delete_team('"+getTeamOne()+"');");
            st.executeUpdate("CALL delete_team('"+getTeamTwo()+"');");
            st.close();
            con.close();
            resetar();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Não foi possivel adicionar\n" + e);
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