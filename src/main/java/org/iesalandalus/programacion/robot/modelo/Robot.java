package org.iesalandalus.programacion.robot.modelo;

import java.util.Objects;

public class Robot {
    private Coordenada coordenada;
    private Orientacion orientacion;
    private Zona zona;

    public Robot() {
        zona = new Zona();
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

    public Robot( Robot robot) {
        Objects.requireNonNull(robot, "El robot no puede ser nulo.");
        zona = robot.zona;
        orientacion = robot.orientacion;
        coordenada = robot.coordenada;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = Objects.requireNonNull(zona, "La zona no puede ser nula.");
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = Objects.requireNonNull(orientacion, "La orientación no puede ser nula.");
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = Objects.requireNonNull(coordenada, "La coordenada no puede ser nula.");
        if(!zona.pertenece(coordenada)) {
            throw new IllegalArgumentException("La coordenada no pertenece a la zona.");
        }
    }

    public void avanzar() {
        int nuevaX = coordenada.x();
        int nuevaY = coordenada.y();
        switch (orientacion) {
            case NORTE -> nuevaY++;
            case NOROESTE -> {
                nuevaY++;
                nuevaX--;
            }
            case OESTE -> nuevaX--;
            case SUROESTE -> {
                nuevaY--;
                nuevaX--;
            }
            case SUR -> nuevaY--;
            case SURESTE -> {
                nuevaY--;
                nuevaX++;
            }
            case ESTE -> nuevaX++;
            case NORESTE -> {
                nuevaY++;
                nuevaX++;
            }
        }
        try {
            setCoordenada(new Coordenada(nuevaX, nuevaY));
        } catch(IllegalArgumentException e) {
            throw new RobotExcepcion("No se puede avanzar, ya que se sale de la zona.");
        }
    }

    public void girarALaDerecha() {
        switch (orientacion) {
            case NORTE -> orientacion = Orientacion.NORESTE;
            case NOROESTE -> orientacion = Orientacion.NORTE;
            case OESTE -> orientacion = Orientacion.NOROESTE;
            case SUROESTE -> orientacion = Orientacion.OESTE;
            case SUR -> orientacion = Orientacion.SUROESTE;
            case SURESTE -> orientacion = Orientacion.SUR;
            case ESTE -> orientacion = Orientacion.SURESTE;
            case NORESTE -> orientacion = Orientacion.ESTE;
        }
    }

    public void girarALaIzquierda() {
            switch (orientacion) {
                case NORTE -> orientacion = Orientacion.NOROESTE;
                case NOROESTE -> orientacion = Orientacion.OESTE;
                case OESTE -> orientacion = Orientacion.SUROESTE;
                case SUROESTE -> orientacion = Orientacion.SUR;
                case SUR -> orientacion = Orientacion.SURESTE;
                case SURESTE -> orientacion = Orientacion.ESTE;
                case ESTE -> orientacion = Orientacion.NORESTE;
                case NORESTE -> orientacion = Orientacion.NORTE;
            }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Robot robot = (Robot) o;
        return Objects.equals(coordenada, robot.coordenada) && orientacion == robot.orientacion && Objects.equals(zona, robot.zona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordenada, orientacion, zona);
    }

    @Override
    public String toString() {
        return String.format("[coordenada=%s, orientación=%s, zona=%s]", coordenada, orientacion, zona);
    }
}
