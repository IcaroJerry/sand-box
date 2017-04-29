package ifba.ads.abstractfactory.persistence.postgres;

public class PostgresConnection {

     public java.sql.Connection getConnection() throws ClassNotFoundException, java.sql.SQLException{
        Class.forName(bdClassName);
        java.sql.Connection conn= java.sql.DriverManager.getConnection(bdAdress, "postgres", password);
        
        return conn;
    }
     
    public static PostgresConnection getInstance(){
        if (instance == null){ instance = new PostgresConnection(); }
        return instance;
    }
        
    private static final String bdAdress = "";
    private static final String bdClassName = "org.postgresql.Driver";
    private static final String password="";
         
    private static PostgresConnection instance;
    
    private PostgresConnection(){}
}
