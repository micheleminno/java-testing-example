

# Stato progetto

![Java CI](https://github.com/micheleminno/java-testing-example/actions/workflows/ci.yml/badge.svg)



# 🧪 Testing Java con Maven, JUnit, GitHub Actions, Report e Code Coverage

## PARTE 1 – Scrivere ed eseguire test JUnit con Maven

### Cos’è JUnit?

JUnit è una libreria Java che permette di scrivere **test automatici**, cioè piccoli programmi che controllano che il tuo codice funzioni come previsto.

Esempio di test:

```java
@Test
void testCifraturaLettera() {
    assertEquals("B", macchina.cifraLettera("A"));
}
```

### Aggiungere JUnit al progetto Maven

Nel `pom.xml`, sotto `<dependencies>`, aggiungi:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

### Scrivere i test

Crea una classe per ogni classe da testare, es. `MacchinaEnigmaTest.java`, e posizionala in `src/test/java`.

Struttura:

```java
package enigma;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MacchinaEnigmaTest {

    MacchinaEnigma macchina;

    @BeforeEach
    void setup() {
        macchina = new MacchinaEnigma();
        macchina.configura(...);
    }

    @Test
    void testCifraturaParola() {
        assertEquals("XYZAB", macchina.cifra("HELLO"));
    }
}
```

Annotazioni utili:
- `@Test` – indica un test
- `@BeforeEach` – eseguito prima di ogni test
- `@AfterEach` – eseguito dopo ogni test
- `@BeforeAll` / `@AfterAll` – una volta sola, all’inizio/fine

Asserzioni:
- `assertEquals(a, b)`
- `assertTrue(cond)`
- `assertThrows(Exception.class, () -> metodo())`

Per eseguire i test:

```bash
mvn test
```

---

## PARTE 2 – Continuous Integration con GitHub Actions + Report e Code Coverage

### Cos’è la Continuous Integration (CI)?

La CI consiste nell’eseguire automaticamente build e test del progetto ogni volta che fai un `commit` su GitHub.  
Vantaggi:
- Controlli immediati su eventuali errori
- Ambiente uniforme per tutti
- Collaborazione semplificata nei progetti di gruppo

### Configurazione di GitHub Actions

1. Crea `.github/workflows/ci.yml` con:

```yaml
name: Java CI

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4

    - name: Set up JDK 21
      uses: actions/setup-java@v4
      with:
        java-version: '21'
        distribution: 'temurin'

    - name: Build and test with Maven
      run: mvn clean verify

    - name: Upload JaCoCo report
      uses: actions/upload-artifact@v4
      with:
        name: jacoco-report
        path: target/site/jacoco/

    - name: Upload Surefire report
      uses: actions/upload-artifact@v4
      with:
        name: surefire-report
        path: target/site/surefire-report.html
```

2. Fai commit e push:
```bash
git add .github/workflows/ci.yml
git commit -m "CI configurata"
git push
```

3. Controlla la sezione “Actions” su GitHub per il risultato dei test.

### Aggiunta del badge nel README.md

Nel file `README.md`, aggiungi:

```markdown
![Java CI](https://github.com/NOME_UTENTE/NOME_REPO/actions/workflows/ci.yml/badge.svg)
```

Sostituisci `NOME_UTENTE` e `NOME_REPO` con i tuoi dati GitHub.

---

### Report dei test – Surefire

Aggiungi nel `pom.xml`:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
</plugin>
```

Per generare il report:

```bash
mvn surefire-report:report
```

Apri `target/site/surefire-report.html`.

---

### Code Coverage – JaCoCo

Aggiungi nel `pom.xml`:

```xml
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
  <version>0.8.11</version>
  <executions>
    <execution>
      <goals>
        <goal>prepare-agent</goal>
      </goals>
    </execution>
    <execution>
      <id>report</id>
      <phase>prepare-package</phase>
      <goals>
        <goal>report</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

Per generare il report:

```bash
mvn clean verify
```

Apri `target/site/jacoco/index.html`.

---

### Comandi utili

```bash
mvn test                      # solo test
mvn verify                   # test + coverage
mvn surefire-report:report   # report HTML test
mvn clean verify surefire-report:report   # tutto insieme
```

---

### Obiettivi completati

- Test automatici su GitHub (CI)
- Badge visibile nel README
- Report HTML dei test
- Report coverage JaCoCo
