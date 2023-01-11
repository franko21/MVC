/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Andrea
 */
public class Conexion {
    private String jdbc="jdbc:postgresql://localhost:5432/MVC";
    private String usuario="postgres";
    private String clave="1234";
    private Connection con;
    public Conexion(){
//        try{
//            Class.forName("org.postgresql.Drive");
//        }catch (ClassNotFoundException ex) {
//            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,ex);
//        }
        try{
            con=DriverManager.getConnection(jdbc,usuario,clave);
        }catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    public ResultSet consulta(String sql){
        try{
            Statement st=con.createStatement();
            return st.executeQuery(sql);
        }catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }
    public SQLException accion(String sql){
        try{
            Statement st=con.createStatement();
            st.execute(sql);
            st.close();
            return null;
        }catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,ex);
            return ex;
        }
    }

    public Connection getCon() {
        return con;
    }
}
