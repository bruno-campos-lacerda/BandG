package LapCounter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DataBaseConection {
    public DataBaseConection(){};
    
    private static final String URL = "jdbc:mysql://localhost:3306/b&g";
    private static final String userName = "Bruno";
    private static final String password = "123";
    
    
    public static void main(String[] args){
        String team = "Defalt";
        String[] members = {"Empth", "Empth", "Empth"};
        String membersList = "Empth";
        Float lapOne = 0f;
        Float lapTwo = 0f;
        Float avaregeTime = (lapOne + lapTwo) / 2;
        
        try{
            Connection connection = DriverManager.getConnection(URL, userName, password);
            String sql = "INSERT INTO lapcounter(TEAM, MEMBERS, LAP_ONE, LAP_TWO, AVAREGE_TIME) VALUES(?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(0, team);
            preparedStatement.setString(1, membersList);
            preparedStatement.setFloat(2, lapOne);
            preparedStatement.setFloat(3, lapTwo);
            preparedStatement.setFloat(4, avaregeTime);
            
            int rowAffected = preparedStatement.executeUpdate();
            
            preparedStatement.close();
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
