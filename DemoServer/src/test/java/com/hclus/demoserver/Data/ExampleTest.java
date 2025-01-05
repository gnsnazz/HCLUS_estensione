package com.hclus.demoserver.Data;

import com.hclus.demoserver.data.Example;
import com.hclus.demoserver.data.InvalidSizeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {
    /** Attributo della classe Example utilizzato per i test. */
    private Example example1;
    /** Attributo della classe Example utilizzato per i test. */
    private Example example2;
    /** Attributo della classe Example utilizzato per i test. */
    private Example example3;

    /**
     * Metodo che permette di inizializzare l'attributo example prima di eseguire i test.
     */
    @BeforeEach
    void setUp() {
        example1 = new Example();
        example2 = new Example();
        example3 = new Example();
    }

    /**
     * Testa il metodo add.
     */
    @Test
    void testAddAndIterator() {
        example1.add(1.0);
        example1.add(2.0);
        example1.add(3.0);

        Iterator<Double> iterator = example1.iterator();

        assertTrue(iterator.hasNext(), "L'iterator dovrebbe avere almeno un valore.");
        assertEquals(1.0, iterator.next(), "Il primo valore dovrebbe essere 1.0");
        assertEquals(2.0, iterator.next(), "Il secondo valore dovrebbe essere 2.0");
        assertEquals(3.0, iterator.next(), "Il terzo valore dovrebbe essere 3.0");
        assertFalse(iterator.hasNext(), "L'iterator non dovrebbe avere più valori.");
    }

    /**
     * Testa il metodo distance.
     */
    @Test
    void testDistanceSameSize() throws InvalidSizeException {
        example1.add(1.0);
        example1.add(2.0);
        example1.add(3.0);
        example2.add(4.0);
        example2.add(5.0);
        example2.add(6.0);

        double dist = example1.distance(example2);

        assertEquals(27.0, dist, "La distanza euclidea tra i due esempi dovrebbe essere 27.0");
    }

    /**
     * Testa il metodo distance con due esempi di dimensioni diverse.
     */
    @Test
    void testDistanceDifferentSize() {
        example1.add(1.0);
        example1.add(2.0);
        example2.add(1.0);
        example2.add(2.0);
        example2.add(3.0);

        InvalidSizeException exception = assertThrows(InvalidSizeException.class, () -> example1.distance(example2));
        assertEquals("Gli esempi hanno dimensioni diverse!", exception.getMessage(), "Il messaggio dell'eccezione non è corretto.");
    }

    /**
     * Testa il metodo toString.
     */
    @Test
    void testToString() {
        example1.add(1.0);
        example1.add(2.0);
        example1.add(3.0);

        String result = example1.toString();

        assertEquals("1.0,2.0,3.0", result, "La rappresentazione in stringa non è corretta.");
    }

    /**
     * Testa il metodo toString con example vuoto.
     */
    @Test
    void testToStringEmpty() {
        String result = example3.toString();
        assertEquals("", result, "La rappresentazione in stringa dovrebbe essere vuota.");
    }

}
