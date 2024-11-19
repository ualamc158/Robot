package org.iesalandalus.programacion.robot.modelo;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public class Robot {

    private Zona zona;
    private Orientacion orientacion;
    private Coordenada coordenada;

    public Robot() {
        zona = new Zona( );
        orientacion = Orientacion.NORTE;
        coordenada = zona.getCentro();
    }

    public Robot(Zona zona) {
        setZona(zona);
        orientacion = Orientacion.NORTE;
        coordenada = zona.getCentro();
    }

    public Robot(Zona zona, Orientacion orientacion) {
        setZona(zona);
        setOrientacion(orientacion);
        coordenada = zona.getCentro();
    }

    public Robot(Zona zona, Orientacion orientacion, Coordenada coordenada) {
        setZona(zona);
        setOrientacion(orientacion);
        setCoordenada(coordenada);
    }

    public Robot(Robot robot) {
        Objects.requireNonNull(robot, "El robot no puede ser nulo.");
        zona = robot.zona;
        orientacion = robot.orientacion;
        coordenada = robot.coordenada;
    }

    public Zona getZona() {
        return zona;
    }

    private void setZona(Zona zona) {
        this.zona = Objects.requireNonNull(zona, "La zona no puede ser nula.");
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    private void setOrientacion(Orientacion orientacion) {
        this.orientacion = Objects.requireNonNull(orientacion, "La orientación no puede ser nula.");
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    private void setCoordenada(Coordenada coordenada) {
        Objects.requireNonNull(coordenada, "La coordenada no puede ser nula.");
        if (!zona.pertenece(coordenada)) {
            throw new IllegalArgumentException("La coordenada no pertenece a la zona.");
        }
        this.coordenada = coordenada;
    }

    public void avanzar() throws OperationNotSupportedException {
        int nuevaX = coordenada.x();
        int nuevaY = coordenada.y();
        switch (orientacion) {
            case NORTE -> nuevaY++;
            case NORESTE -> {nuevaX++; nuevaY++;}
            case ESTE -> nuevaX++;
            case SURESTE -> {nuevaY--; nuevaX++;}
            case SUR -> nuevaY--;
            case SUROESTE -> {nuevaX--; nuevaY--;}
            case OESTE -> nuevaX--;
            case NOROESTE -> {nuevaX--; nuevaY++;}
        }
        try {
            setCoordenada(new Coordenada(nuevaX, nuevaY));
        } catch (IllegalArgumentException e) {
            throw new OperationNotSupportedException("No se puede avanzar, ya que se sale de la zona.");
        }
    }

    public void girarALaIzquierda() {
        orientacion = switch (orientacion) {
            case NORTE -> Orientacion.NOROESTE;
            case NORESTE -> Orientacion.NORTE;
            case ESTE -> Orientacion.NORESTE;
            case SURESTE -> Orientacion.ESTE;
            case SUR -> Orientacion.SURESTE;
            case SUROESTE -> Orientacion.SUR;
            case OESTE -> Orientacion.SUROESTE;
            case NOROESTE -> Orientacion.OESTE;
        };
    }

    public void girarALaDerecha() {
        orientacion = switch (orientacion) {
            case NORTE -> Orientacion.NORESTE;
            case NORESTE -> Orientacion.ESTE;
            case ESTE -> Orientacion.SURESTE;
            case SURESTE -> Orientacion.SUR;
            case SUR -> Orientacion.SUROESTE;
            case SUROESTE -> Orientacion.OESTE;
            case OESTE -> Orientacion.NOROESTE;
            case NOROESTE -> Orientacion.NORTE;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Robot robot)) return false;
        return Objects.equals(zona, robot.zona) && orientacion == robot.orientacion && Objects.equals(coordenada, robot.coordenada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zona, orientacion, coordenada);
    }

    @Override
    public String toString() {
        return String.format("Robot[zona=%s, orientacion=%s, coordenada=%s]", this.zona, this.orientacion, this.coordenada);
    }
}
