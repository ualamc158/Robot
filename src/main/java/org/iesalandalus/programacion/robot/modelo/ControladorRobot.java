package org.iesalandalus.programacion.robot.modelo;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public class ControladorRobot {

    private Robot robot;

    public ControladorRobot(Robot robot) {
        this.robot = new Robot(Objects.requireNonNull(robot, "El robot no puede ser nulo."));
    }

    public Robot getRobot() {
        return new Robot(robot);
    }

    public void ejecutar(char comando) throws OperationNotSupportedException {
        switch (comando) {
            case 'A', 'a' -> robot.avanzar();
            case 'D', 'd' -> robot.girarALaDerecha();
            case 'I', 'i' -> robot.girarALaIzquierda();
            default -> throw new OperationNotSupportedException("Comando desconocido.");
        }
    }
}
