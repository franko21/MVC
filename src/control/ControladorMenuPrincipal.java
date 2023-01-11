package control;

import modelo.ModeloPersona;
import vista.VistaPersona;
import vista.VistaPrincipal;
import vista.VistaPuntoDeVenta;

public class ControladorMenuPrincipal {

    VistaPrincipal vistaPrincipal;

    public ControladorMenuPrincipal(VistaPrincipal vistaprincipal) {
        this.vistaPrincipal = vistaprincipal;
        vistaprincipal.setVisible(true);
    }

    public void iniciaControl() {
        vistaPrincipal.getMnuPersonas().addActionListener(l -> crudPersonas());
        vistaPrincipal.getBtnPersonas().addActionListener(l -> crudPersonas());
        vistaPrincipal.getMnItemPuntoVenta().addActionListener(l->puntoVenta());
    }

    private void crudPersonas() {
        //Instancio las clases del Modelo y la Vista.
        VistaPersona vista = new VistaPersona();
        ModeloPersona modelo = new ModeloPersona();

        //Agregar Vista Personas al Desktop Pane.
        vistaPrincipal.getEscritorio().add(vista);
        vista.setVisible(true);
        ControlPersona control = new ControlPersona(modelo, vista);
        control.iniciarControl();//Empezamos las escuchas a los eventos de la vista, Listeners.
    }
    private void puntoVenta(){
        VistaPuntoDeVenta vistaVenta=new VistaPuntoDeVenta();
        vistaVenta.setVisible(true);
    }
}
