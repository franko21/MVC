/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.*;
/**
 *
 * @author Andrea
 */
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Conexion;
import modelo.ModeloPersona;
import modelo.Persona;

import vista.VistaPersona;
public class ControlPersona {
    private ModeloPersona modelo;
    private VistaPersona vista;
    

    public ControlPersona(ModeloPersona modelo, VistaPersona vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
    }
    private void cargaPersona(){
        List<Persona> lista=modelo.listaPersona();
        DefaultTableModel mTabla=(DefaultTableModel) vista.getTblPersonas().getModel();
        mTabla.setNumRows(0);   
        String[] columnas={"Cedula","Nombre","Apellidos","Telefono","Email"};
        mTabla.setColumnIdentifiers(columnas);
        lista.stream().forEach(persona->{
            String[] registro={persona.getIdPersona(),persona.getNombres(),persona.getApellidos(),persona.getTelefono(),persona.getEmail()};
            mTabla.addRow(registro);
        });
    }
    private void ingresarPersonaDialog(int ce) throws SQLException{
        String titulo;
        if (ce==0) {
            titulo="Crear Persona";
            vista.getDigPersona().setTitle(titulo);
        }else{
            titulo="Modificar Persona";
            vista.getDigPersona().setTitle(titulo);
            if (vista.getTblPersonas().getSelectedRow()<0) {
                JOptionPane.showMessageDialog(null, "DEBE DE SELECCIONAR LA PERSONA A MODIFICAR");
                return;
            }
            if (modelo.buscarPersona(vista.getTblPersonas().getValueAt(vista.getTblPersonas().getSelectedRow(), 0).toString())==false) {
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO DICHA PERSONA");
                return;
            }
            Conexion con=new Conexion();
            String sql="SELECT idpersona,nombres,apellidos,telefono,email,sexo,sueldo,cupo,fechanacimiento FROM persona WHERE idpersona='"+vista.getTblPersonas().getValueAt(vista.getTblPersonas().getSelectedRow(), 0).toString()+"'";
            ResultSet re=con.consulta(sql);re.next();
            vista.getTxtCedula().setText(re.getString("idpersona"));
            vista.getTxtNombre().setText(re.getString("nombres"));
            vista.getTxtApellidos().setText(re.getString("apellidos"));
            vista.getTxtTelefono().setText(re.getString("telefono"));
            vista.getTxtEmail().setText(re.getString("email"));
            if (re.getString("sexo").equals("M")) {
                vista.getCbxSexo().setSelectedIndex(0);
            }else{vista.getCbxSexo().setSelectedIndex(1);}
            vista.getTxtSueldo().setText(String.valueOf(re.getDouble("sueldo")));
            vista.getTxtCupo().setText(String.valueOf(re.getInt("cupo")));
            int a=re.getDate("fechanacimiento").getYear()-1900;
            int m=re.getDate("fechanacimiento").getMonth();
            int d=re.getDate("fechanacimiento").getDay();
            vista.getDchFecha().setDate(new Date(a,m,d));
            vista.getTxtCedula().setEnabled(false);
        }
        vista.getDigPersona().setSize(600,600);
        vista.getDigPersona().setLocationRelativeTo(vista);
        vista.getDigPersona().setVisible(true);
    }
    private void ingresarModificarPersona(){
        if (vista.getDigPersona().getTitle().equals("Crear Persona")) {
            if (vista.getTxtCedula().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR UNA CEDULA");
                return;
            }
            if (!vista.getTxtCedula().getText().matches("[0-9]{1,20}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR UNA CEDULA BIEN");
                return;
            }
            if (vista.getTxtNombre().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR UN NOMBRE");
                return;
            }
            if (!vista.getTxtNombre().getText().matches("[(A-Za-z)|\\s]{1,20}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR UN NOMBRE BIEN");
                return;
            }
            if (vista.getTxtApellidos().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SUS APELLIDOS");
                return;
            }
            if (!vista.getTxtApellidos().getText().matches("[(A-Za-z)|\\s]{1,20}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SUS APELLIDOS BIEN");
                return;
            }
            if (vista.getTxtTelefono().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU TELEFONO");
                return;
            }
            if (!vista.getTxtTelefono().getText().matches("[0-9]{1,20}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU TELEFONO BIEN");
                return;
            }
            if (vista.getTxtEmail().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU EMAIL");
                return;
            }
            if (!vista.getTxtEmail().getText().matches("(([a-z0-9]|\\-)+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU EMAIL BIEN");
                return;
            }
            if (vista.getTxtSueldo().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU SUELDO");
                return;
            }
            if (!vista.getTxtSueldo().getText().matches("([1-9]{1}[0-9]{0,9}|[1-9]{1}[0-9]{0,9}\\.[0-9]{1,2}){1}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU SUELDO BIEN");
                return;
            }
            if (vista.getTxtCupo().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU CUPO");
                return;
            }
            if (!vista.getTxtCupo().getText().matches("[1-9]{1}[0-9]{0,9}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU CUPO BIEN");
                return;
            }
            
            int d,m,a;
            Calendar cal=vista.getDchFecha().getCalendar();
            d=cal.get(Calendar.DAY_OF_MONTH);
            m=cal.get(Calendar.MONTH);
            a=cal.get(Calendar.YEAR);
            new ModeloPersona(vista.getTxtCedula().getText(),vista.getTxtNombre().getText(),vista.getTxtApellidos().getText(),vista.getTxtTelefono().getText(),vista.getTxtEmail().getText(),vista.getCbxSexo().getItemAt(vista.getCbxSexo().getSelectedIndex()),Double.parseDouble(vista.getTxtSueldo().getText()),Integer.parseInt(vista.getTxtCupo().getText()),new Date(a,m,d)).grabarPersona();
            cargaPersona();
        }else{
            if (vista.getTxtCedula().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR UNA CEDULA");
                return;
            }
            if (!vista.getTxtCedula().getText().matches("[0-9]{1,20}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR UNA CEDULA BIEN");
                return;
            }
            if (vista.getTxtNombre().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR UN NOMBRE");
                return;
            }
            if (!vista.getTxtNombre().getText().matches("[(A-Za-z)|\\s]{1,20}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR UN NOMBRE BIEN");
                return;
            }
            if (vista.getTxtApellidos().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SUS APELLIDOS");
                return;
            }
            if (!vista.getTxtApellidos().getText().matches("[(A-Za-z)|\\s]{1,20}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SUS APELLIDOS BIEN");
                return;
            }
            if (vista.getTxtTelefono().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU TELEFONO");
                return;
            }
            if (!vista.getTxtTelefono().getText().matches("[0-9]{1,20}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU TELEFONO BIEN");
                return;
            }
            if (vista.getTxtEmail().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU EMAIL");
                return;
            }
            if (!vista.getTxtEmail().getText().matches("(([a-z0-9]|\\-)+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU EMAIL BIEN");
                return;
            }
            if (vista.getTxtSueldo().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU SUELDO");
                return;
            }
            if (!vista.getTxtSueldo().getText().matches("([1-9]{1}[0-9]{0,9}|[1-9]{1}[0-9]{0,9}\\.[0-9]{1,2}){1}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU SUELDO BIEN");
                return;
            }
            if (vista.getTxtCupo().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU CUPO");
                return;
            }
            if (!vista.getTxtCupo().getText().matches("[1-9]{1}[0-9]{0,9}")) {
                JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR SU CUPO BIEN");
                return;
            }
            
            int d,m,a;
            Calendar cal=vista.getDchFecha().getCalendar();
            d=cal.get(Calendar.DAY_OF_MONTH);
            m=cal.get(Calendar.MONTH);
            a=cal.get(Calendar.YEAR);
            new ModeloPersona(vista.getTxtCedula().getText(),vista.getTxtNombre().getText(),vista.getTxtApellidos().getText(),vista.getTxtTelefono().getText(),vista.getTxtEmail().getText(),vista.getCbxSexo().getItemAt(vista.getCbxSexo().getSelectedIndex()),Double.parseDouble(vista.getTxtSueldo().getText()),Integer.parseInt(vista.getTxtCupo().getText()),new Date(a,m,d)).modificarPersona();
            cargaPersona();
            vista.getDigPersona().dispose();
        }
        limpiar();
    }
    private void eliminarPersona() throws SQLException{
        if (vista.getTblPersonas().getSelectedRow()<0) {
                JOptionPane.showMessageDialog(null, "DEBE DE SELECCIONAR LA PERSONA A ELIMINAR");
                return;
        }
        if (modelo.buscarPersona(vista.getTblPersonas().getValueAt(vista.getTblPersonas().getSelectedRow(), 1).toString())==false) {
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO DICHA PERSONA");
                return;
        }
        new ModeloPersona().eliminarPersona(vista.getTblPersonas().getValueAt(vista.getTblPersonas().getSelectedRow(), 0).toString());
        cargaPersona();
    };
    private void buscarPersona(){
        List<Persona> lista=modelo.buscaPersona(vista.getTxtBuscar().getText());
        DefaultTableModel mTabla=(DefaultTableModel) vista.getTblPersonas().getModel();
        mTabla.setNumRows(0);   
        String[] columnas={"Cedula","Nombre","Apellidos","Telefono","Email"};
        mTabla.setColumnIdentifiers(columnas);
        lista.stream().forEach(persona->{
            String[] registro={persona.getIdPersona(),persona.getNombres(),persona.getApellidos(),persona.getTelefono(),persona.getEmail()};
            mTabla.addRow(registro);
        });
    }
    public void iniciarControl(){
        cargaPersona();
        vista.getBtnIngresar().addActionListener(l->{
            try {
                ingresarPersonaDialog(0);
            } catch (SQLException ex) {
                Logger.getLogger(ControlPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        vista.getBtnModificar().addActionListener(l->{
            try {
                ingresarPersonaDialog(1);
            } catch (SQLException ex) {
                Logger.getLogger(ControlPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        vista.getBtnAceptar().addActionListener(l->ingresarModificarPersona());
        vista.getBtnEliminar().addActionListener(l->{
            try {
                eliminarPersona();
            } catch (SQLException ex) {
                Logger.getLogger(ControlPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        vista.getBtnBuscar().addActionListener(l->buscarPersona());
    }
    private void limpiar(){
        vista.getTxtCedula().setText(null);
        vista.getTxtNombre().setText(null);
        vista.getTxtApellidos().setText(null);
        vista.getTxtTelefono().setText(null);
        vista.getTxtEmail().setText(null);
        vista.getTxtSueldo().setText(null);
        vista.getTxtCupo().setText(null);
        vista.getDchFecha().setDate(null);
        vista.getTxtCedula().setEnabled(true);
        
    }
}
