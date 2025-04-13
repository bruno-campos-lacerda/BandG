package LapCounter;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DataBaseConection {
    public DataBaseConection(){};
    
    private static final String URL = "jdbc:mysql://localhost:3306/b&g";
    
    public static Connection Connect(int opt){
        Connection cn = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection(URL, "root", "");
            
            if(opt == 1){
                String sql = "INSERT INTO lapcounter(TEAM, MEMBERS, LAP_ONE, LAP_TWO, AVAREGE_TIME) VALUES(?, ?, ?, ?, ?)";
            
                PreparedStatement preparedStatement = cn.prepareStatement(sql);
                preparedStatement.setString(1, "B and G");
                preparedStatement.setString(2, "Bruno e Gabriela");
                preparedStatement.setFloat(3, 12.3f);
                preparedStatement.setFloat(4, 11.8f);
                preparedStatement.setFloat(5, (12.3f + 11.8f) / 2);   
                preparedStatement.executeUpdate();
                
                preparedStatement.close();
            }else if(opt == 2){
                Statement statement = cn.createStatement();
                String query = "SELECT * FROM lapcounter";
            
                ResultSet executeSet = statement.executeQuery(query); 
                while(executeSet.next()){
                    JOptionPane.showMessageDialog(null, executeSet.getString("TEAM"));
            }
                statement.close();
                executeSet.close();
            }else{
                JOptionPane.showMessageDialog(null, "Opção inválida");
            }
            cn.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "SQLException: " + e);
            
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, "Class not found: " + ex);
            
        }
        return cn;
    }
    
    public static void main(String[] args){
        String option = JOptionPane.showInputDialog("Select an option\n"
                + "(1): Insert data\n"
                + "(2): Select data");
        int opt = 0;
        try{
            opt = Integer.parseInt(option);
            Connect(opt);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Opção inválida");
        }
        
    }
}
