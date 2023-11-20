package org.iesalandalus.programacion.robot.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ZonaTest {

    @Test
    void constructorPorDefectoCreaZonaConAndhoYAltoMinimos() {
        Zona zona = new Zona();
        assertEquals(10, zona.ancho());
        assertEquals(10, zona.alto());
    }

    @ParameterizedTest(name = "Cuando llamamos al constructor con valores {0}, {1} crea la zona correctamente")
    @CsvSource({"10, 10", "30, 20", "20, 30", "10, 100", "100, 10", "100, 100"})
    void constructorConParmetrosValidosCreaZona(int ancho, int alto) {
        Zona zona = new Zona(ancho, alto);
        assertEquals(ancho, zona.ancho());
        assertEquals(alto, zona.alto());
    }

    @ParameterizedTest(name = "Cuando llamamos al constructor con ancho inválido: {0}, lanza una excepción")
    @ValueSource(ints = {9, 101, -50, 150})
    void constructorConAnchoInvalidoLanzaExcepcion(int ancho) {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Zona(ancho, 50));
        assertEquals("Ancho no válido.", iae.getMessage());
    }

    @ParameterizedTest(name = "Cuando llamamos al constructor con alto inválido: {0}, lanza una excepción")
    @ValueSource(ints = {9, 101, -50, 150})
    void constructorConAltoInvalidoLanzaExcepcion(int alto) {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Zona(50, alto));
        assertEquals("Alto no válido.", iae.getMessage());
    }

    @ParameterizedTest(name = "Cuando llamamos al método getCentro para la zona [{0}, {1}] nos devuelve su centro: [{2}, {3}]")
    @CsvSource({"20, 20, 10, 10", "20, 15, 10, 7", "15, 20, 7, 10", "15, 15, 7, 7"})
    void getCentroDevuelveElCentroDeLaZona(int ancho, int alto, int centroX, int centroY) {
        Zona zona = new Zona(ancho, alto);
        assertEquals(centroX, zona.getCentro().x());
        assertEquals(centroY, zona.getCentro().y());
    }

    @ParameterizedTest(name = "Cuando llamamos al método pertenece con coordenadas pertenecientes a la zona: [5, 5] como: [{0}, {1}], devuelve true")
    @CsvSource({"0, 0", "0, 9", "9, 0", "9, 9", "2, 3", "3, 2"})
    void perteneceConCoordenadasPertenecientesALaZonaDevuelveTrue(int x, int y) {
        Zona zona = new Zona(10, 10);
        Coordenada coordenada = getCoordenada(x, y);
        assertTrue(zona.pertenece(coordenada));
    }

    private Coordenada getCoordenada(int x, int y) {
        Coordenada coordenada = mock(Coordenada.class);
        when(coordenada.x()).thenReturn(x);
        when(coordenada.y()).thenReturn(y);
        return coordenada;
    }

    @ParameterizedTest(name = "Cuando llamamos al método pertenece con coordenadas NO pertenecientes a la zona: [5, 5], como: [{0}, {1}], devuelve false")
    @CsvSource({"-1, 0", "0, -1", "-1, -1", "10, 5", "5, 10", "10, 10", "-5, -7", "-7, -5", "15 , 7", "7, 15"})
    void perteneceConCoordenadasNoPertenecientesALaZonaDevuelveFalse(int x, int y) {
        Zona zona = new Zona(10, 10);
        Coordenada coordenada = getCoordenada(x, y);
        assertFalse(zona.pertenece(coordenada));
    }

    @Test
    @DisplayName("Cuando llamamos al método pertenece con una coordenada null lanza una excepción")
    void perteneceConPuntoNullLanzaException() {
        Zona zona = new Zona(10, 10);
        NullPointerException npe = assertThrows(NullPointerException.class, () -> zona.pertenece(null));
        assertEquals("La coordenada no puede ser nula.", npe.getMessage());
    }

}