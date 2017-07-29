/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Owner
 */
public class Conexciones {

    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "org.firebirdsql.jdbc.FBDriver";
    public static final String DB_URL = "jdbc:firebirdsql:localhost/3050:c:/bzrecords/BZRECORDS2.FBD";
    

    //  Database credentials
    public static final String USER = "sysdba";
    public static final String PASS = "masterkey";

    // Table ints
    public static final int ARTISTA = 0,
            CANCION = 1,
            CONTRATO = 2,
            DISCO = 3,
            PRODUCTOR = 4;

    //  Database info
    static Connection conn;

    private static void getConn() {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
            conn.setAutoCommit(true);
        } catch (SQLException se) {
            //Handle errors for JDBC
            JOptionPane.showMessageDialog(null, se.getMessage());
        } catch (Exception e) {
            //Handle errors for Class.forName
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void closeConn() {
        try {
            if (conn != null) {
                conn.close();//se tiene que cerrar la conexcion no sucedan errores
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            JOptionPane.showMessageDialog(null, se.getMessage());
        }
    }

    public static ResultSet getRS(String sql) throws SQLException {
        getConn();
        ResultSet rs;
        Statement stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        return rs;
    }

    public static String getID(int tabla, String nombre) throws SQLException {
        ResultSet rs;
        String aux;
        switch (tabla) {
            case ARTISTA:
                rs = getRS("SELECT idartista from artista WHERE nombre = '" + nombre + "'");
                if (rs.next()) {
                    aux = rs.getString("idartista");
                } else {
                    aux = "";
                }
                break;
            case CANCION:
                rs = getRS("SELECT idcancion from cancion WHERE titulo = '" + nombre + "'");
                if (rs.next()) {
                    aux = rs.getString("idcancion");
                } else {
                    aux = "";
                }
                break;
            case CONTRATO:
                rs = getRS("SELECT nocontrato from contrato WHERE nocontrato = '" + nombre + "'");
                if (rs.next()) {
                    aux = rs.getString("nocontrato");
                } else {
                    aux = "";
                }
                break;
            case DISCO:
                rs = getRS("SELECT iddisco from disco WHERE titulo = '" + nombre + "'");
                if (rs.next()) {
                    aux = rs.getString("iddisco");
                } else {
                    aux = "";
                }
                break;
            case PRODUCTOR:
                rs = getRS("SELECT idproductor from productor WHERE nombre = '" + nombre + "'");
                if (rs.next()) {
                    aux = rs.getString("idproductor");
                } else {
                    aux = "";
                }
                break;
            default:
                throw new IllegalArgumentException("los datos no son correctos. Conexiones.getID()");
        }
        closeConn();
        return aux;
    }

    public static String[] getData(int tabla) throws SQLException {
        //en lista se guardaran los registras de la columna que rpresenta la tabla

        ArrayList<String> lista = new ArrayList<>();
        ResultSet rs;
        switch (tabla) {
            case ARTISTA:
                lista.add("Seleccion artista");
                rs = getRS("SELECT nombre FROM artista");//se proyecta el nombre en artita
                while (rs.next()) {
                    lista.add(rs.getString("nombre"));//se agrgan todos los nombres a la lista
                }
                break;
            case CANCION:
                lista.add("Seleccion cancion");
                rs = getRS("SELECT titulo FROM cancion");
                while (rs.next()) {
                    lista.add(rs.getString("titulo"));
                }
                break;
            case CONTRATO:
                lista.add("Seleccion contrato");
                rs = getRS("SELECT nocontrato FROM contrato");
                while (rs.next()) {
                    lista.add(rs.getString("nocontrato"));
                }
                break;
            case DISCO:
                lista.add("Seleccion disco");
                rs = getRS("SELECT titulo FROM disco");
                while (rs.next()) {
                    lista.add(rs.getString("titulo"));
                }
                break;
            case PRODUCTOR:
                lista.add("Seleccion productor");
                rs = getRS("SELECT nombre FROM productor");
                while (rs.next()) {
                    lista.add(rs.getString("nombre"));
                }
                break;
            default:
                throw new IllegalArgumentException("la tabla no existe. Conexciones.getData()");
        }
        closeConn();
        //este pequeño codigo comvierte el arraylist en un String[]
        String[] data = new String[lista.size()];
        int aux = 0;
        for (String dato : lista) {
            data[aux++] = dato;
        }
        return data;//se regresa el String[]
    }

    public static boolean validate(int tabla) {
        boolean foo = false;
        switch (tabla) {
            case ARTISTA:
                try {
                    ResultSet rs = getRS("SELECT idartista FROM artista");
                    foo = rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case CANCION:
                try {
                    ResultSet rs = getRS("SELECT idcancion FROM cancion");
                    foo = rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case CONTRATO:
                try {
                    ResultSet rs = getRS("SELECT nocontrato FROM contrato");
                    foo = rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case DISCO:
                try {
                    ResultSet rs = getRS("SELECT iddisco FROM disco");
                    foo = rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case PRODUCTOR:
                try {
                    ResultSet rs = getRS("SELECT idproductor FROM productor");
                    foo = rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("la tabla no existe. Conexciones.validate()");
        }
        closeConn();
        return foo;
    }

    private static boolean validateInsert(int tabla, String nombre) {
        boolean foo = false;
        switch (tabla) {
            case ARTISTA:
                try {
                    ResultSet rs = getRS("SELECT idartista FROM artista "
                            + "WHERE nombre = '" + nombre + "'");
                    foo = !rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case CANCION:
                try {
                    ResultSet rs = getRS("SELECT idcancion FROM cancion "
                            + "WHERE titulo = '" + nombre + "'");
                    foo = !rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case DISCO:
                try {
                    ResultSet rs = getRS("SELECT iddisco FROM disco "
                            + "WHERE titulo = '" + nombre + "'");
                    foo = !rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            case PRODUCTOR:
                try {
                    ResultSet rs = getRS("SELECT idproductor FROM productor "
                            + "WHERE nombre = '" + nombre + "'");
                    foo = !rs.next();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("la tabla no existe. Conexciones.validateInsert()");
        }
        return foo;
    }

    public static ResultSet searchData(int tabla, String valor) throws IllegalArgumentException, SQLException {
        ResultSet rs;
        switch (tabla) {
            case ARTISTA: 
                rs = getRS("SELECT * FROM artista WHERE "
                        + "idartista LIKE '%" + valor + "%' OR "
                        + "nombre LIKE '%" + valor + "%' OR "
                        + "genero LIKE '%" + valor + "%'");
                break;
            case CANCION:
                rs = getRS("SELECT "
                        + "idcancion, "
                        + "posicion, "
                        + "cancion.titulo as titulo, "
                        + "cancion.genero as genero, "
                        + "disco.titulo as titulod "
                        + "FROM cancion, disco WHERE "
                        + "(idcancion LIKE '%" + valor + "%' OR "
                        + "posicion LIKE '%" + valor + "%' OR "
                        + "cancion.titulo LIKE '%" + valor + "%' OR "
                        + "cancion.genero LIKE '%" + valor + "%' OR "
                        + "disco.titulo LIKE '%" + valor + "%')"
                        + "AND disco = iddisco");
                break;
            case CONTRATO:
                rs = getRS("SELECT "
                        + "nocontrato, "
                        + "fecha, "
                        + "artista.nombre AS nombrea, "
                        + "productor.nombre AS nombrep, "
                        + "cantidaddiscos, "
                        + "duracion, "
                        + "artista, "
                        + "productor, "
                        + "idartista, "
                        + "idproductor "
                        + "FROM contrato, artista, productor WHERE "
                        + "(nocontrato LIKE '%" + valor + "%' OR "
                        + "fecha LIKE '%" + valor + "%' OR "
                        + "artista.nombre LIKE '%" + valor + "%' OR "
                        + "productor.nombre LIKE '%" + valor + "%' OR "
                        + "cantidaddiscos LIKE '%" + valor + "%' OR "
                        + "duracion LIKE '%" + valor + "%')"
                        + "AND contrato.artista = artista.idartista "
                        + "AND contrato.productor = productor.idproductor");
                break;
            case DISCO:
                //iddisco,' ',titulo,' ',tipo,' ',contrato,' ',genero,' ',cantidadcanciones
                rs = getRS("SELECT * FROM disco WHERE "
                        + "iddisco LIKE '%" + valor + "%' OR "
                        + "titulo LIKE '%" + valor + "%' OR "
                        + "tipo LIKE '%" + valor + "%' OR "
                        + "contrato LIKE '%" + valor + "%' OR "
                        + "genero LIKE '%" + valor + "%' OR "
                        + "cantidadcanciones LIKE '%" + valor + "%'");
                break;
            case PRODUCTOR:
                //IDproductor,' ',nombre
                rs = getRS("SELECT * FROM productor WHERE "
                        + "idproductor LIKE '%" + valor + "%' OR "
                        + "nombre LIKE '%" + valor + "%'");
                break;
            default:
                throw new IllegalArgumentException("no se encontro la tabla. Conexciones.searchData()");
        }
        return rs;
    }

    public static void insert(int table, String... datos) throws IllegalArgumentException, SQLException {
        getConn();
        Statement stmt;
        String sql;
        switch (table) {//determines the sql querry
            case ARTISTA:
                if (datos.length != 2) {
                    throw new IllegalArgumentException("los datos proveidos no son correctos");
                }
                if (validateInsert(ARTISTA, datos[0])) {
                    sql = "INSERT INTO artista "
                            + "VALUES (0, '" + datos[0] + "', '" + datos[1] + "')";
                    System.out.println("getin' the sql statement for artista");
                } else {
                    throw new IllegalArgumentException("ya existe ese artista");
                }

                break;
            case CANCION:
                if (datos.length != 4) {
                    throw new IllegalArgumentException("error...");
                }
                if (validateInsert(table, datos[0])) {
                    sql = "INSERT INTO cancion "
                            + "VALUES (0, '" + datos[0] + "', '" + datos[1] + "', '" + datos[2] + "',"
                            + " '" + datos[3] + "')";
                    System.out.println("getin' the sql statement for cancion");
                } else {
                    throw new IllegalArgumentException("ya existe esa cancion");
                }
                break;
            case CONTRATO:
                if (datos.length != 5) {
                    throw new IllegalArgumentException("error...");
                }
                sql = "INSERT INTO contrato "
                        + "VALUES (0, '" + datos[0] + "', '" + datos[1] + "', '" + datos[2] + "',"
                        + " '" + datos[3] + "', '" + datos[4] + "')";
                System.out.println("getin' the sql statement for contrato");
                break;
            case DISCO:
                if (datos.length != 5) {
                    throw new IllegalArgumentException("error...");
                }
                if (validateInsert(table, datos[1])) {
                    sql = "INSERT INTO disco "
                            + "VALUES (0, '" + datos[0] + "', '" + datos[1] + "', '" + datos[2] + "',"
                            + " '" + datos[3] + "', '" + datos[4] + "')";
                    System.out.println("getin' the sql statement for disco");
                } else {
                    throw new IllegalArgumentException("ya existe ese disco");
                }
                break;
            case PRODUCTOR:
                if (datos.length != 1) {
                    throw new IllegalArgumentException("error...");
                }
                if (validateInsert(table, datos[0])) {
                    sql = "INSERT INTO productor "
                            + "VALUES (0, '" + datos[0] + "')";
                    System.out.println("getin' the sql statement for productor");
                } else {
                    throw new IllegalArgumentException("ya existe ese productor");
                }
                break;
            default:
                throw new IllegalArgumentException();
        }//end switch

        stmt = conn.createStatement();
        System.out.println("excecuting the statement");
        stmt.executeUpdate(sql);
        System.out.println("success");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {

        }
    }//end insert

    public static void delete(int tabla, String id) throws SQLException {
        getConn();
        Statement stmt;
        String sql;
        switch (tabla) {
            case ARTISTA:
                sql = "DELETE FROM artista WHERE idartista = " + id;
                break;
            case CANCION:
                sql = "DELETE FROM cancion WHERE idcancion = " + id;
                break;
            case CONTRATO:
                sql = "DELETE FROM contrato WHERE nocontrato = " + id;
                break;
            case DISCO:
                sql = "DELETE FROM disco WHERE iddisco = " + id;
                break;
            case PRODUCTOR:
                sql = "DELETE FROM productor WHERE idproductor = " + id;
                break;
            default:
                throw new IllegalArgumentException("no se encontro la tabla");
        }

        stmt = conn.createStatement();
        System.out.println("excecuting the statement");
        stmt.executeUpdate(sql);
        System.out.println("success");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {

        }
    }

    public static ResultSet search(int tabla, String column, String dato) throws SQLException {
        ResultSet rs;
        switch (tabla) {
            case ARTISTA:
                rs = getRS("SELECT * FROM artista "
                        + "WHERE " + column + " "
                        + "LIKE '%" + dato + "%'");
                break;
            case CANCION:
                rs = getRS("SELECT " 
                        + "idcancion, " 
                        + "posicion, " 
                        + "cancion.titulo as titulo, " 
                        + "cancion.genero as genero, " 
                        + "disco.titulo as titulod "
                        + "FROM cancion, disco "
                        + "WHERE " + column + " "
                        + "LIKE '%" + dato + "%'"
                        + "AND cancion.disco = disco.iddisco");
                break;
            case CONTRATO:
                rs = getRS("SELECT "
                        + "nocontrato, "
                        + "fecha, "
                        + "artista.nombre AS nombrea, "
                        + "productor.nombre AS nombrep, "
                        + "cantidaddiscos, "
                        + "duracion, "
                        + "artista, "
                        + "productor, "
                        + "idartista, "
                        + "idproductor "
                        + "FROM contrato, artista, productor "
                        + "WHERE " + column + " "
                        + "LIKE '%" + dato + "%'"
                        + "AND contrato.artista = artista.idartista "
                        + "AND contrato.productor = productor.idproductor");
                break;
            case DISCO:
                rs = getRS("SELECT * FROM disco "
                        + "WHERE " + column + " "
                        + "LIKE '%" + dato + "%'");
                break;
            case PRODUCTOR:
                rs = getRS("SELECT * FROM productor "
                        + "WHERE " + column + " "
                        + "LIKE '%" + dato + "%'");
                break;
            default:
                throw new IllegalArgumentException("no se encontro la tabla. Conexciones.searchData()");
        }
        return rs;
    }

    public static void update(int table, String id, String... datos) throws SQLException {
        getConn();
        Statement stmt;
        String sql;
        switch (table) {//determines the sql querry
            case ARTISTA:
                if (datos.length != 2) {
                    throw new IllegalArgumentException("los datos proveidos no son correctos");
                }
                sql = "UPDATE artista "
                        + "SET nombre = '" + datos[0] + "',"
                        + "genero = '" + datos[1] + "'"
                        + "WHERE idartista = " + id;

                break;
            case CANCION:
                if (datos.length != 5) {
                    throw new IllegalArgumentException("error...");
                }
                sql = "UPDATE cancion "
                        + "SET posicion = '" + datos[0] + "',"
                        + "titulo = '" + datos[1] + "',"
                        + "genero = '" + datos[2] + "',"
                        + "disco = '" + datos[3] + "'"
                        + "WHERE idcancion = " + id;
                break;
            case CONTRATO:
                if (datos.length != 5) {
                    throw new IllegalArgumentException("error...");
                }
                sql = "UPDATE contrato "
                        + "SET fecha = '" + datos[0] + "',"
                        + "artista = '" + datos[1] + "',"
                        + "productor = '" + datos[2] + "',"
                        + "cantidaddiscos = '" + datos[3] + "',"
                        + "duracion = '" + datos[4] + "'"
                        + "WHERE nocontrato = " + id;
                break;
            case DISCO:
                if (datos.length != 5) {
                    throw new IllegalArgumentException("error...");
                }
                sql = "UPDATE disco "
                        + "SET titulo = '" + datos[0] + "',"
                        + "tipo = '" + datos[1] + "',"
                        + "contrato = '" + datos[2] + "',"
                        + "genero = '" + datos[3] + "',"
                        + "cantidadcanciones = '" + datos[4] + "'"
                        + "WHERE iddisco = " + id;
                break;
            case PRODUCTOR:
                if (datos.length != 1) {
                    throw new IllegalArgumentException("error...");
                }
                sql = "UPDATE productor "
                        + "SET nombre = '" + datos[0] + "'"
                        + "WHERE idproductor = " + id;
                break;
            default:
                throw new IllegalArgumentException();
        }//end switch

        stmt = conn.createStatement();
        System.out.println("excecuting the statement");
        stmt.executeUpdate(sql);
        System.out.println("success");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {

        }
    }

    public static String getLetra(String id) {
        ResultSet rs = null;
        try {
            rs = getRS("Select letra form cancion where idcancion = " + id);
            if (rs != null) {
                if (rs.next()) {
                    return rs.getString("letra");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se encontro la letra de la cancion", "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
           return "null";
    }

}
