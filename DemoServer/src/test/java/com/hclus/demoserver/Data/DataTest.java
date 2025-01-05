package com.hclus.demoserver.Data;

import com.hclus.demoserver.data.Data;
import com.hclus.demoserver.data.NoDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataTest {
    /** Oggetto Data per i test.*/
    private static Data data;

    /**
     * Metodo che inizializza l'oggetto Data per i test.
     */
    @BeforeEach
    public void setUpBeforeClass() {
        try {
            data = new Data("exampleTab");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    /**
     * Metodo che testa il metodo getNumberOfExample() verificando che restituisca il numero di esempi presenti nel dataset.
     */
    @Test
    public void testGetNumberOfExample() {
        assertEquals(5, data.getNumberOfExample(), "Il numero degli esempi dovrebbe essere 5.");
    }

    /**
     * Metodo che testa il metodo getExample() verificando che l'esempio non sia nullo.
     */
    @Test
    public void testGetExample() {
        assertNotNull(data.getExample(0), "The first example should not be null.");
    }

    /**
     * Metodo che testa il metodo getExample() verificando che l'indice dell'esempio non sia oltre al numero degli esempi.
     */
    @Test
    public void testGetExampleException() {
        assertThrows(IndexOutOfBoundsException.class, () -> data.getExample(6), "Should throw an IndexOutOfBoundsException.");
    }

    /**
     * Metodo che testa il metodo getExample() verificando che l'indice dell'esempio sia valido.
     */
    @Test
    public void testGetExampleNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> data.getExample(-1), "Should throw an IndexOutOfBoundsException.");
    }

    /**
     * Metodo che testa il metodo getExample() verificando che l'indice dell'esempio sia valido.
     */
    @Test
    public void testGetExampleNullData() {
        assertThrows(NoDataException.class, () -> new Data(null), "Should throw a NoDataException.");
    }

    /**
     * Metodo che testa il metodo iterator() verificando che non sia nullo.
     */
    @Test
    public void testIterator() {
        assertNotNull(data.iterator(), "The iterator should not be null.");
    }

    /**
     * Metodo che testa il metodo toString() verificando che restituisca una stringa non nulla.
     */
    @Test
    public void testToString() {
        assertNotNull(data.toString());
    }


}