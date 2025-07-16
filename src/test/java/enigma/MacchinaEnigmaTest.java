
package enigma;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MacchinaEnigmaTest {

    MacchinaEnigma macchina = new MacchinaEnigma();

    @Test
    void testCifra() {
        assertEquals("HeLLO", macchina.cifra("hello"));
    }
}
