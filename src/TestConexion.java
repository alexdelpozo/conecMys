import java.sql.*;

public class TestConexion {
    // Atributos de la clase
    private String bd = "pi";
    private String login = "root";
    private String pwd = "";
    private String url = "jdbc:mysql://localhost/pi";
    private Connection conexion;

    // Constructor que crea la conexión
    public TestConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, login, pwd);
            if (conexion != null) {
                System.out.println(" - Conexión con Mysql establecida -");
            }

        } catch (ClassNotFoundException e) {
            System.out.println(" – Error de Conexión con mysql -");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la BD");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error general de Conexión");
            e.printStackTrace();
        }
    }
    public void Consulta(String query, String cod, int columna) {
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, cod);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next())
                System.out.println(rset.getString(columna));
            rset.close();
            pstmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void infoBaseDatos() {
        try {
            DatabaseMetaData dbmd = conexion.getMetaData();
            System.out.println("URL: " + dbmd.getURL());
            System.out.println("Usuario: " + dbmd.getUserName());
            System.out.println("Driver: " + dbmd.getDriverName());
            ResultSet misTablas = dbmd.getTables("pi",null, null, null);
            System.out.println("TABLAS");
            while (misTablas.next()) {
                System.out.println("-> " + misTablas.getString("TABLE_NAME"));
            }
            misTablas.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public static void main(String[] args) {

        TestConexion prueba = new TestConexion();
        prueba.infoBaseDatos();

    }
}
