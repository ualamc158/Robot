package org.iesalandalus.programacion.robot;

import org.iesalandalus.programacion.robot.modelo.ControladorRobot;
import org.iesalandalus.programacion.robot.modelo.Robot;
import org.iesalandalus.programacion.robot.modelo.RobotExcepcion;
import org.iesalandalus.programacion.robot.vista.Consola;

public class Main {
    private static ControladorRobot controladorRobot;

    public static void main(String[] args) {
        int opcion;
        do {
            Consola.mostrarMenuPrincipal();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
            if(controladorRobot == null) {
                System.out.println("El robot no puede ser nulo");
                System.out.println();
            } else {
                System.out.println(controladorRobot.getRobot());
            }
            System.out.println();
        } while (opcion != 6);

    }
    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> Main.controlarRobotDefecto();
            case 2 -> Main.controlarRobotZona();
            case 3 -> Main.controlarRobotZonaOrientacion();
            case 4 -> Main.controlarRobotZonaOrientacionCoordenada();
            case 5 -> Main.ejecutarComando();
            case 6 -> Consola.despedirse();
            default -> System.out.println("Debe elegir una opción válida");
        }
    }

    private static void controlarRobotDefecto() {
        controladorRobot = new ControladorRobot(new Robot());
    }

    private static void controlarRobotZona() {
        controladorRobot = new ControladorRobot(new Robot(Consola.elegirZona()));
    }

    private static void controlarRobotZonaOrientacion() {
        controladorRobot = new ControladorRobot(new Robot(Consola.elegirZona(), Consola.elegirOrientacion()));
    }

    private static void controlarRobotZonaOrientacionCoordenada() {
        controladorRobot = new ControladorRobot(new Robot(Consola.elegirZona(), Consola.elegirOrientacion(), Consola.elegirCoordenada()));
    }


    private static void ejecutarComando() {
        if(controladorRobot == null) {
            System.out.println("Aún no has creado ningún robot");
        } else {
            try {
                controladorRobot.ejecutar(Consola.elegirComando());
            } catch (RobotExcepcion | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
