package org.iesalandalus.programacion.robot.modelo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordenadaTest {

    @ParameterizedTest(name = "Cuando llamamos al constructor con valores {0}, {1} crea coordenada [{0}, {1}].")
    @CsvSource({"1, 1", "5, 5", "-1, -1", "-5 ,3", "3, -5"})
    void constructorConParametrosValidosCreaCoordenada(int x, int y) {
        Coordenada coordenada = new Coordenada(x, y);
        assertEquals(x, coordenada.x());
        assertEquals(y, coordenada.y());
    }

}