package pl.compo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    SudokuField obj1 = new SudokuField();
    SudokuField obj2 = new SudokuField();

    @Test
    void get_and_set() {
        obj1.setValue(1);
        assertEquals(1, obj1.getValue());
        obj1.setValue(10);
        assertEquals(1, obj1.getValue());
        obj1.setValue(-1);
        assertEquals(1, obj1.getValue());
    }

    @Test
    void areFieldsTheSame() {
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, new Object());
    }

    @Test
    void equalsHashCodeIntegrity() {
        obj1.setValue(1);
        obj2.setValue(2);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1, obj2);
        obj2.setValue(obj1.getValue());
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertEquals(obj1, obj2);
    }

    @Test
    void compareToTestEqual() {
        obj1.setValue(1);
        obj2.setValue(1);
        assertEquals(0, obj1.compareTo(obj2));
    }

    @Test
    void compareToTestBigger() {
        obj1.setValue(7);
        obj2.setValue(2);
        assertTrue(obj1.compareTo(obj2) > 0);
    }

    @Test
    void compareToTestSmaller() {
        obj1.setValue(1);
        obj2.setValue(5);
        assertTrue(obj1.compareTo(obj2) < 0);
    }

    @Test
    void compareToTestException() {
        assertThrows(NullPointerException.class, () -> obj1.compareTo(null));
    }

    @Test
    void cloneTest() {
        obj1.setValue(5);
        obj2 = obj1.clone();
        assertEquals(obj2, obj1);
    }
}