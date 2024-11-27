package org.iesalandalus.programacion.robot.vista;

import org.iesalandalus.programacion.robot.modelo.Zona;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
    private Consola(){}

    public void mostrarMenuPrincipal() {
        System.out.println("1: Controlar un robot por defecto");
        System.out.println("2: Controlar un robot indicando su zona");
        System.out.println("3: Controlar un robot indicando su zona y orientación");
        System.out.println("4: Controlar un robot indicando su zona, orientación y coordenada inicial");
        System.out.println("5: Ejecutar comando");
        System.out.println("6: Salir");
    }

    public int elegirOpcion() {
        mostrarMenuPrincipal();
        int opcionElegida = 0;
        do {
            System.out.print("Introduzca el número de la opción seleccionada: ");
            opcionElegida = Entrada.entero();
        } while (opcionElegida < 1 && opcionElegida > 6);
        return opcionElegida;
    }

    /*public Zona elegirZona() {

    }*/
}
