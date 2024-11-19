package org.iesalandalus.programacion.robot.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RobotTest {

    private Zona zonaDefecto;
    private Coordenada centroDefecto;

    @BeforeEach
    void setUp() {
        centroDefecto = getCoordenada(5, 5);
        zonaDefecto = getZona(10, 10, centroDefecto);
    }

    private Coordenada getCoordenada(int x, int y) {
        Coordenada coordenada = mock(Coordenada.class);
        when(coordenada.x()).thenReturn(x);
        when(coordenada.y()).thenReturn(y);
        return coordenada;
    }

    private Zona getZona(int ancho, int alto, Coordenada centro) {
        Zona zona = mock(Zona.class);
        when(zona.ancho()).thenReturn(ancho);
        when(zona.alto()).thenReturn(alto);
        when(zona.getCentro()).thenReturn(centro);
        when(zona.pertenece(centro)).thenReturn(true);
        return zona;
    }

    @Test
    void constructorPorDefectoCreaRobotConZonaPorDefectoOrientadoAlNorteYSituadoEnElCentroDeLaZona() {
        Robot robot = new Robot();
        assertEquals(zonaDefecto.ancho(), robot.getZona().ancho());
        assertEquals(zonaDefecto.alto(), robot.getZona().alto());
        assertEquals(zonaDefecto.getCentro().x(), robot.getCoordenada().x());
        assertEquals(zonaDefecto.getCentro().y(), robot.getCoordenada().y());
        assertEquals(Orientacion.NORTE, robot.getOrientacion());
    }

    @ParameterizedTest(name = "Cuando llamamos al constructor con una zona válida como [{0}, {1}] crea el robot con esa zona, orientado al norte y situado en el centro de la zona => [{2}, {3}].")
    @CsvSource({"20, 20, 10, 10", "20, 10, 10, 5", "10, 20, 5, 10", "10, 10, 5, 5"})
    void constructorConZonaValidaCreaRobotOrientadoAlNorteYSituadoEnElCentroDeLaZona(int ancho, int alto, int x, int y) {
        Coordenada coordenada = getCoordenada(x, y);
        Zona zona = getZona(ancho, alto, coordenada);
        Robot robot = new Robot(zona);
        assertEquals(zona, robot.getZona());
        assertEquals(coordenada.x(), robot.getCoordenada().x());
        assertEquals(coordenada.y(), robot.getCoordenada().y());
        assertEquals(Orientacion.NORTE, robot.getOrientacion());
    }

    @Test
    void constructorConZonaNulaLanzaExcepcion() {
        Zona zona = null;
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Robot(zona));
        assertEquals("La zona no puede ser nula.", npe.getMessage());
    }

    @ParameterizedTest(name = "Cuando llamamos al constructor con una zona válida como [{0}, {1}] y una orientación {2} crea el robot con esa zona, orientado al {2} y situado en el centro de la zona => [{3}, {4}].")
    @CsvSource({"10, 10, NORTE, 5, 5", "20, 10, ESTE, 10, 5", "10, 20, SURESTE, 5, 10", "20, 20, NOROESTE, 10, 10"})
    void constructorConZonaValidaYOrientacionValidaCreaRobotConDichaOrientacionYSituadoEnElCentroDeLaZona(int ancho, int alto, Orientacion orientacion, int x, int y) {
        Coordenada coordenada = getCoordenada(x, y);
        Zona zona = getZona(ancho, alto, coordenada);
        Robot robot = new Robot(zona, orientacion);
        assertEquals(zona, robot.getZona());
        assertEquals(coordenada.x(), robot.getCoordenada().x());
        assertEquals(coordenada.y(), robot.getCoordenada().y());
        assertEquals(orientacion, robot.getOrientacion());
    }

    @Test
    void constructorConZonaNulaYOrientacionCorrectaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Robot(null, Orientacion.NORTE));
        assertEquals("La zona no puede ser nula.", npe.getMessage());
    }

    @Test
    void constructorConZonaValidaYOrientacionNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Robot(zonaDefecto, null));
        assertEquals("La orientación no puede ser nula.", npe.getMessage());
    }

    @ParameterizedTest(name = "Cuando llamamos al constructor con una zona válida como [{0}, {1}], una orientación {2} y las coordenadas [{3}, {4}] crea el robot con esa zona, orientado al {2} y situado en dicha coordenada => [{3}, {4}].")
    @CsvSource({"10, 10, NORTE, 0, 0", "10, 5, ESTE, 10, 5", "5, 10, SURESTE, 0, 10", "5, 5, NOROESTE, 5, 5"})
    void constructorConZonaValidaOrientacionValidaYCoordernadaValidaCreaRobotConDichaZonaOrientacionYSituadoDichaCoordenada(int ancho, int alto, Orientacion orientacion, int x, int y) {
        Coordenada coordenada = getCoordenada(x, y);
        Zona zona = getZona(ancho, alto, coordenada);
        Robot robot = new Robot(zona, orientacion, coordenada);
        assertEquals(zona, robot.getZona());
        assertEquals(coordenada.x(), robot.getCoordenada().x());
        assertEquals(coordenada.y(), robot.getCoordenada().y());
        assertEquals(orientacion, robot.getOrientacion());
    }

    @Test
    void constructorConZonaNulaOrientacionCorrectaYCoordenadaCorrectaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Robot(null, Orientacion.NORTE, centroDefecto));
        assertEquals("La zona no puede ser nula.", npe.getMessage());
    }

    @Test
    void constructorConZonaValidaOrientacionNulaYCoordenadaCorrectaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Robot(zonaDefecto, null, centroDefecto));
        assertEquals("La orientación no puede ser nula.", npe.getMessage());
    }

    @Test
    void constructorConZonaValidaOrientacionValidaYCoordenadaNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Robot(zonaDefecto, Orientacion.NORTE, null));
        assertEquals("La coordenada no puede ser nula.", npe.getMessage());
    }

    @ParameterizedTest(name = "Cuando llamamos al constructor con una zona [{0}, {1}], una orientación {2} y una coordenada que no pertenece a la zona [{3}, {4}] lanza una excepción")
    @CsvSource({"10, 10, SUR, -1, 0", "10, 10, NORTE, 0, -1", "20, 20, SURESTE, 21, 0", "20, 20, SUROESTE, 20, -1", "20, 20, OESTE, 21, 20", "20, 20, SUR, 20, 21", "20, 20, NORTE, -1, 20", "20, 20, NORTE, 0, 21"})
    void constructorConZonaValidaOrientacionValidaYCoordenadaNoPerteneceALaZonaLanzaExcepcion(int ancho, int alto, Orientacion orientacion, int x, int y) {
        Zona zona = new Zona(ancho, alto);
        Coordenada coordenada = new Coordenada(x, y);
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,() -> new Robot(zona, orientacion, coordenada));
        assertEquals("La coordenada no pertenece a la zona.", iae.getMessage());
    }

    @Test
    void constructorCopiaConRobotValidoCopiaCorrectamente() {
        Robot robot = new Robot();
        Robot robotCopia = new Robot(robot);
        assertEquals(robot, robotCopia);
    }

    @Test
    void constructorCopiaConRobotNullLanzaExcepcion() {
        Robot robot = null;
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Robot(robot));
        assertEquals("El robot no puede ser nulo.", npe.getMessage());
    }

    @ParameterizedTest(name = "Cuando avanzamos partiendo del centro de la zona por defecto y con orientación {0} nos movemos a la coordenada [{1}, {2}]")
    @CsvSource({"NORTE, 5, 6", "NORESTE, 6, 6", "ESTE, 6, 5", "SURESTE, 6, 4", "SUR, 5, 4", "SUROESTE, 4, 4", "OESTE, 4, 5", "NOROESTE, 4, 6"})
    void avanazarAvanzaCorrectamente(Orientacion orientacion, int coordenadaX, int coordenadaY) {
        Zona zona = new Zona();
        Robot robot = new Robot(zona, orientacion);
        assertDoesNotThrow(robot::avanzar);
        Coordenada coordenadaEsperada = getCoordenada(coordenadaX, coordenadaY);
        assertEquals(coordenadaEsperada.x(), robot.getCoordenada().x());
        assertEquals(coordenadaEsperada.y(), robot.getCoordenada().y());
    }

    @ParameterizedTest(name = "Cuando avanzamos partiendo de [{0}, {1}] con orientación {2} lanza una excepción, ya que nos saldríamos de la zona (por defecto).")
    @CsvSource({"0, 0, SURESTE", "0, 0, SUR", "0, 0, SUROESTE", "9, 0, SUR", "9, 0, SURESTE", "9, 0, SUROESTE", "9, 9, ESTE", "9, 9, NORESTE", "9, 9, NORTE", "0, 9, NORTE", "0, 9, NORESTE", "0, 9, OESTE"})
    void avanazarSaleDelLimiteDeLaZonaLanzaExcepcion(int x, int y, Orientacion orientacion) {
        Zona zona = new Zona();
        Coordenada coordenada = getCoordenada(x, y);
        Robot robot = new Robot(zona, orientacion, coordenada);
        RobotExcepcion re = assertThrows(RobotExcepcion.class, robot::avanzar);
        assertEquals("No se puede avanzar, ya que se sale de la zona.", re.getMessage());
    }

    @ParameterizedTest(name = "Cuando giramos el robot a la derecha partiendo de una orientación {0} obtenemos la orientación girada correctamente {1}")
    @CsvSource({"NORTE, NORESTE", "NORESTE, ESTE", "ESTE, SURESTE", "SURESTE, SUR", "SUR, SUROESTE", "SUROESTE, OESTE", "OESTE, NOROESTE", "NOROESTE, NORTE"})
    void girarALaDerechaGiraCorrectamente(Orientacion orientacionInicial, Orientacion orientacionEsperada) {
        Robot robot = new Robot(zonaDefecto, orientacionInicial);
        robot.girarALaDerecha();
        assertEquals(orientacionEsperada, robot.getOrientacion());
    }

    @ParameterizedTest(name = "Cuando giramos el robot a la izquierda partiendo de una orientación {0} obtenemos la orientación girada correctamente {1}")
    @CsvSource({"NORTE, NOROESTE", "NORESTE, NORTE", "ESTE, NORESTE", "SURESTE, ESTE", "SUR, SURESTE", "SUROESTE, SUR", "OESTE, SUROESTE", "NOROESTE, OESTE"})
    void girarALaIzquierdaGiraCorrectamente(Orientacion orientacionInicial, Orientacion orientacionEsperada) {
        Robot robot = new Robot(zonaDefecto, orientacionInicial);
        robot.girarALaIzquierda();
        assertEquals(orientacionEsperada, robot.getOrientacion());
    }

}