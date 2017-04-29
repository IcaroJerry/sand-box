package ifba.ads.abstractfactory.persistence.mysql;

public class MYSQLConnection {

     public java.sql.Connection getConnection() throws ClassNotFoundException, java.sql.SQLException{
        Class.forName(bdClassName);
        java.sql.Connection conn= java.sql.DriverManager.getConnection(bdAdress);
        
        return conn;
    }
     
    public static MYSQLConnection getInstance(){
        if (instance == null){ instance = new MYSQLConnection(); }
        return instance;
    }
        
    private static final String bdAdress = "";
    private static final String bdClassName = "com.mysql.jdbc.Driver";
    private static MYSQLConnection instance;
    
    private MYSQLConnection(){}
}
