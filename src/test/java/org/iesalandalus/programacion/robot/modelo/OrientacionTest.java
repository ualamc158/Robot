package org.iesalandalus.programacion.robot.modelo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrientacionTest {

    @ParameterizedTest(name = "Cuando llamamos al método toString para la instancia: {0} devuelve la cadena esperada: {1}")
    @CsvSource({"NORTE, Norte", "NOROESTE, Noroeste", "OESTE, Oeste", "SUROESTE, Suroeste", "SUR, Sur", "SURESTE, Sureste", "ESTE, Este", "NORESTE, Noreste"})
    void toStringDevuelveLaCadenaEsperada(Orientacion orientacion, String nombre) {
        assertEquals(nombre, orientacion.toString());
    }

}