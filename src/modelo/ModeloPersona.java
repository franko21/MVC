/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
/**
 *
 * @author Andrea
 */
public class ModeloPersona extends Persona{
    
    public ModeloPersona() {
    }
    
    public ModeloPersona(String idPersona, String nombres, String apellidos, String telefono, String email,String sexo,double sueldo,int cupo,Date fechaNacimiento) {
        super(idPersona, nombres, apellidos, telefono, email,sexo,sueldo,cupo,fechaNacimiento);
    }
    
    public List<Persona> listaPersona(){
        try{
            List<Persona> lista=new ArrayList<>();
            String sql="SELECT idpersona,nombres,apellidos,telefono,email,sexo,sueldo,cupo,fechanacimiento FROM persona";
            Conexion con=new Conexion();
            ResultSet result=con.consulta(sql);
            while (result.next()) {
                Persona per=new Persona(result.getString("idpersona"),result.getString("nombres"),result.getString("apellidos"),result.getString("telefono"),result.getString("email"),result.getString("sexo"),result.getDouble("sueldo"),result.getInt("cupo"),result.getDate("fechanacimiento"));
                lista.add(per);
            }
            result.close();
            return lista;
        }catch (Exception ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public List<Persona> buscaPersona(String nombre){
        try{
            List<Persona> lista=new ArrayList<>();
            String sql="SELECT idpersona,nombres,apellidos,telefono,email,sexo,sueldo,cupo,fechanacimiento FROM persona WHERE nombres LIKE '%"+nombre+"%' OR apellidos LIKE '%"+nombre+"%'";
            Conexion con=new Conexion();
            ResultSet result=con.consulta(sql);
            while (result.next()) {
                Persona per=new Persona(result.getString("idpersona"),result.getString("nombres"),result.getString("apellidos"),result.getString("telefono"),result.getString("email"),result.getString("sexo"),result.getDouble("sueldo"),result.getInt("cupo"),result.getDate("fechanacimiento"));
                lista.add(per);
            }
            result.close();
            return lista;
        }catch (Exception ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public SQLException grabarPersona(){
        Conexion con=new Conexion();
        String sql="INSERT INTO persona(idpersona,nombres,apellidos,telefono,email,sexo,sueldo,cupo,fechanacimiento) "
                + "VALUES('"+getIdPersona()+"','"+getNombres()+"','"+getApellidos()+"','"+getTelefono()+"','"+getEmail()+"','"+getSexo()+"',"+getSueldo()+","+getCupo()+",'"+getFechaNacimiento()+"')";
        SQLException ex=con.accion(sql);
        return ex;
    }
    public SQLException eliminarPersona(String idpersona){
        Conexion con=new Conexion();
        String sql="DELETE FROM persona WHERE idpersona='"+idpersona+"';";
        SQLException ex=con.accion(sql);
        return ex;   
    }
    public SQLException modificarPersona(){
        Conexion con=new Conexion();
        String sql="UPDATE persona SET nombres='"+getNombres()+"',apellidos='"+getApellidos()+"',telefono='"+getTelefono()+""
                + "',email='"+getEmail()+"',sexo='"+getSexo()+"',sueldo="+getSueldo()+",cupo="+getCupo()+",fechanacimiento='"+getFechaNacimiento()+"' WHERE idpersona='"+getIdPersona()+"';";
        SQLException ex=con.accion(sql);
        
        return ex;
    }
    public boolean buscarPersona(String idpersona) throws SQLException{
        boolean test=false;
        Conexion con=new Conexion();
        String sql="SELECT idpersona FROM persona WHERE idpersona='"+idpersona+"';";
        ResultSet re=con.consulta(sql);
        if (con.consulta(sql).wasNull()) {
            test=false;
        }else{test=true;}
        return test;
    }
}
