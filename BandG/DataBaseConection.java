package LapCounter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DataBaseConection {
    public DataBaseConection(){};
    
    private static final String URL = "jdbc:mysql://localhost:3306/b&g";
    
    public static Connection Connect(){
        Connection cn = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection(URL, "root", "");
            
            String sql = "INSERT INTO lapcounter(TEAM, MEMBERS, LAP_ONE, LAP_TWO, AVAREGE_TIME) VALUES(?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = cn.prepareStatement(sql);
            preparedStatement.setString(1, "B and G");
            preparedStatement.setString(2, "Bruno e Gabriela");
            preparedStatement.setFloat(3, 12.3f);
            preparedStatement.setFloat(4, 11.8f);
            preparedStatement.setFloat(5, (12.3f + 11.8f) / 2);      
            int rowAffected = preparedStatement.executeUpdate();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "SQLException: " + e);
            
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, "Class not found: " + ex);
            
        }
        return cn;
    }
    
    public static void main(String[] args){
        Connect();
    }
}
