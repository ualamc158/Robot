package org.iesalandalus.programacion.robot;

import org.iesalandalus.programacion.robot.modelo.ControladorRobot;
import org.iesalandalus.programacion.robot.modelo.Robot;
import org.iesalandalus.programacion.robot.vista.Consola;

import javax.naming.OperationNotSupportedException;

public class Main {

    private static ControladorRobot controladorRobot;

    public static void main(String[] args) {
        int opcion;
        do {
            opcion = Consola.elegitOpcion();
            ejecutarOpcion(opcion);
            if (opcion != 0) {
                Consola.mostrarRobot(controladorRobot);
            }
        } while (opcion != 0);
        Consola.despedirse();
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> controlarRobotDefecto();
            case 2 -> controlarRobotZona();
            case 3 -> controlarRobotZonaOrientacion();
            case 4 -> controlarRobotZonaOrientacionCoordenada();
            case 5 -> ejecutarComando();
            default -> {}
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
        if (controladorRobot != null) {
            try {
                controladorRobot.ejecutar(Consola.elegirComando());
            } catch (OperationNotSupportedException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
