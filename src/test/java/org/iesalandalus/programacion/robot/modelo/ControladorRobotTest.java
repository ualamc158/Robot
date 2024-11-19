package org.iesalandalus.programacion.robot.modelo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorRobotTest {

    @Mock
    static Robot robot;
    @InjectMocks
    static ControladorRobot controladorRobot;
    private AutoCloseable anotaciones;

    @BeforeAll
    static void setup() {
        Zona zona = mock(Zona.class);
        Coordenada coordenada = mock(Coordenada.class);
        robot = mock(Robot.class);
        when(robot.getZona()).thenReturn(zona);
        when(robot.getCoordenada()).thenReturn(coordenada);
        when(robot.getOrientacion()).thenReturn(Orientacion.NORTE);
        controladorRobot = new ControladorRobot(robot);
    }

    @BeforeEach
    void openMocks() {
        anotaciones = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeMocks() throws Exception {
        anotaciones.close();
    }

    @Test
    void constructorEvitaProblemaAliasing() {
        Robot robot = new Robot();
        ControladorRobot controladorRobot = new ControladorRobot(robot);
        assertDoesNotThrow(robot::avanzar);
        assertNotEquals(robot.getCoordenada(), controladorRobot.getRobot().getCoordenada());
    }

    @Test
    void constructorRobotNuloLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new ControladorRobot(null));
        assertEquals("El robot no puede ser nulo.", npe.getMessage());
    }

    @Test
    void getEvitaProblemaAliasing() {
        Robot robot = new Robot();
        ControladorRobot controladorRobot = new ControladorRobot(robot);
        Robot robotCopia = controladorRobot.getRobot();
        assertDoesNotThrow(robotCopia::avanzar);
        assertNotEquals(robotCopia.getCoordenada(), controladorRobot.getRobot().getCoordenada());
    }

    @ParameterizedTest(name = "Cuando llamamos a ejecutar con el comando `{0}` llama a robot avanzar")
    @CsvSource({"A", "a"})
    void ejecutarComandoALlamaRobotAvanzar(char comando)  {
        assertDoesNotThrow(() -> controladorRobot.ejecutar(comando));
        assertDoesNotThrow(() -> verify(robot).avanzar());
    }

    @ParameterizedTest(name = "Cuando llamamos a ejecutar con el comando `{0}` llama a robot girarALaDerecha")
    @CsvSource({"D", "d"})
    void ejecutarComandoDLlamaRobotGirarALaDerecha(char comando)  {
        assertDoesNotThrow(() -> controladorRobot.ejecutar(comando));
        verify(robot).girarALaDerecha();
    }

    @ParameterizedTest(name = "Cuando llamamos a ejecutar con el comando `{0}` llama a robot girarALaIzquierda")
    @CsvSource({"I", "i"})
    void ejecutarComandoILlamaRobotGirarALaIzquierda(char comando)  {
        assertDoesNotThrow(() -> controladorRobot.ejecutar(comando));
        verify(robot).girarALaIzquierda();
    }

    @ParameterizedTest(name = "Cuando llamamos a ejecutar con un comando no válido`{0}` lanza excepción")
    @CsvSource({"*", "4", "v", "F"})
    void ejecutarComandoNoValidoLanzaExcepcion(char comando)  {
        RobotExcepcion re = assertThrows(RobotExcepcion.class, () -> controladorRobot.ejecutar(comando));
        assertEquals("Comando desconocido.", re.getMessage());
    }

}