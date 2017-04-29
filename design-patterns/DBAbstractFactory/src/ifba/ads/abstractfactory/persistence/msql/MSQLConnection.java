package ifba.ads.abstractfactory.persistence.msql;

public class MSQLConnection {

     public java.sql.Connection getConnection() throws ClassNotFoundException, java.sql.SQLException{
        Class.forName(bdClassName);
        java.sql.Connection conn= java.sql.DriverManager.getConnection(bdAdress);
        
        return conn;
    }
     
    public static MSQLConnection getInstance(){
        if (instance == null){ instance = new MSQLConnection(); }
        return instance;
    }
        
    private static final String bdAdress = "";
    private static final String bdClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static MSQLConnection instance;
    
    private MSQLConnection(){}
}
