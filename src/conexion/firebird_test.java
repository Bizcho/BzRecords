
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;




public final class firebird_test{
    
  static public void main (String args[]) throws Exception{

      String databaseURL = 
              "jdbc:firebirdsql:localhost/3050:c:/bzrecords/BZRECORDS2.FBD";
      String user = "sysdba";
      String pass = "masterkey";
      
      String driver = "org.firebirdsql.jdbc.FBDriver";
      
      Connection conexion;
      Statement consulta;
      ResultSet resultado;
      
      try {
          Class.forName(driver);
          
            System.out.println("Connecting to a selected database...");
            conexion = DriverManager.getConnection(databaseURL, user, pass);
            System.out.println("Connected database successfully...");
          
          consulta = conexion.createStatement();
          
          String consultaSQL = "select * from artista";
          resultado = consulta.executeQuery(consultaSQL);
          
          while (resultado.next()){
              System.out.println("plop");
          }
      } catch (Exception e) {
          e.printStackTrace();
      }

      
  }
      
}

			  
