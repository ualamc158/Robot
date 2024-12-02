package org.iesalandalus.programacion.robot.vista;

import org.iesalandalus.programacion.robot.modelo.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.util.Objects;

public class Consola {
    private Consola() {
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("1: Controlar un robot por defecto");
        System.out.println("2: Controlar un robot indicando su zona");
        System.out.println("3: Controlar un robot indicando su zona y orientación");
        System.out.println("4: Controlar un robot indicando su zona, orientación y coordenada inicial");
        System.out.println("5: Ejecutar comando");
        System.out.println("6: Salir");
    }

    public static int elegirOpcion() {
        mostrarMenuPrincipal();
        int opcionElegida;
        do {
            System.out.print("Introduzca el número de la opción seleccionada: ");
            opcionElegida = Entrada.entero();
        } while (opcionElegida < 1 || opcionElegida > 6);
        return opcionElegida;
    }

    public static Zona elegirZona() {
        Zona zona = null;
        do {
            System.out.print("Introduzca el ancho de la zona: ");
            int ancho = Entrada.entero();
            System.out.print("Introduzca el alto de la zona: ");
            int alto = Entrada.entero();
            try {
                zona = new Zona(ancho, alto);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (zona == null);
        return zona;
    }

    public static void mostrarMenuOrientacion() {
        System.out.println("Las posibles orientaciones son: ");
        System.out.println("1: NORTE,\n2: NORESTE,\n3: ESTE,\n4: SURESTE,\n5: SUR,\n6: SUROESTE,\n7: OESTE,\n8: NOROESTE");
    }

    public static Orientacion elegirOrientacion() {
        Consola.mostrarMenuOrientacion();
        int entrada;
        do {
            System.out.print("Introduzca la orientación del robot: ");
            entrada = Entrada.entero();
        } while (entrada < 1 || entrada > 8);
        return switch (entrada) {
            case 1 -> Orientacion.NORTE;
            case 2 -> Orientacion.NORESTE;
            case 3 -> Orientacion.ESTE;
            case 4 -> Orientacion.SURESTE;
            case 5 -> Orientacion.SUR;
            case 6 -> Orientacion.SUROESTE;
            case 7 -> Orientacion.OESTE;
            case 8 -> Orientacion.NOROESTE;
            default -> null;
        };
    }

    public static Coordenada elegirCoordenada() {
        System.out.print("Introduzca la coordenada X: ");
        int x = Entrada.entero();
        System.out.print("Introduzca la coordenada Y: ");
        int y = Entrada.entero();
        return new Coordenada(x, y);
    }

    public static char elegirComando() {
        System.out.println("Los comandos son: \n'A' para avanzar \n'D' para girar a la derecha \n'I' para girar a la izquierda");
        char c;
        System.out.print("Indique el comando a ejecutar: ");
        c = Entrada.caracter();
        return c;
    }

    public static void mostrarRobot(ControladorRobot controladorRobot) {
        Objects.requireNonNull(controladorRobot, "El robot no puede ser nulo");
    }

    public static void despedirse() {
        System.out.println("Adios");
    }

}


