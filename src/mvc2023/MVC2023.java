/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc2023;
import control.ControlPersona;
import control.ControladorMenuPrincipal;
import modelo.ModeloPersona;
import vista.VistaPersona;
import vista.VistaPrincipal;

public class MVC2023 {
    /**
     * @param args the command line arguments
    */
    public static void main(String[] args) {
        VistaPrincipal vista=new VistaPrincipal();
        ControladorMenuPrincipal control=new ControladorMenuPrincipal(vista);
        control.iniciaControl();
    }
}