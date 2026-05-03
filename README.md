# H-CLUS - Versione Estesa

Estensione della versione base con **interfaccia web** realizzata con Vaadin Flow e Spring Boot. La comunicazione client-server passa da socket a **HTTP**. Accessibile da qualsiasi browser su `localhost`.

---

## Requisiti

- JDK 22+
- MySQL 5.7+ (Community Server)
- Gradle

---

## Installazione e configurazione

### 1. Installare Java

Scaricare e installare JDK 22 o successivo.

Verificare l'installazione:
```bash
java -version
```

### 2. Installare MySQL

Scaricare e installare MySQL Community Server 5.7 o successivo.

Avviare il servizio MySQL e verificare che sia in esecuzione.

### 3. Configurare il database

Nella cartella del progetto eseguire lo script di setup:
```bash
mysql -u root -p < setup.sql
```

Questo crea il database `map` con le tabelle necessarie (es. `exampleTab`).

---

## Avvio

> Il server **deve** essere avviato prima del client.

### 1. Avviare il server

Nella cartella del progetto server, aprire `run_server.bat` con doppio click oppure da terminale:
```
run_server.bat
```
Il server è pronto quando nel terminale compare il banner di Spring Boot e la riga:
```
Tomcat started on port 8080 (http)
```

In alternativa, dalla cartella del progetto server è possibile avviarlo con:
```bash
./gradlew bootRun
```

### 2. Avviare il client

Nella cartella del progetto client, aprire `run_client.bat` con doppio click oppure da terminale:
```
run_client.bat
```
Aprire il browser e navigare su:
```
http://localhost:8081
```

---

## Utilizzo

### Home page

La pagina principale presenta due opzioni:

- **Apprendi da Database** — esegue il clustering su una tabella MySQL
- **Carica da File** — visualizza un dendrogramma salvato in precedenza

È inoltre possibile cambiare il tema dell'interfaccia (chiaro/scuro) direttamente dalla home.

### Apprendi da Database

1. Inserire il **nome della tabella** MySQL (es. `exampleTab`)
2. Inserire la **profondità** del dendrogramma (intero tra 1 e il numero di esempi)
3. Selezionare il **tipo di distanza**: Single Link o Average Link
4. Premere **Genera**
5. Dopo la visualizzazione del risultato, inserire un **nome file** con estensione e premere **Salva**

### Carica da File

1. Inserire il **nome del file** con estensione (es. `result.bin`)
2. Inserire il **nome della tabella** di riferimento
3. Premere **Mostra**

Il bottone **Indietro** riporta alla home da qualsiasi pagina.

---

## Struttura dei package

**Server**

| Package | Classi principali |
|---|---|
| `server` | `ServerController`, `ServerService` |
| `clustering` | `HierarchicalClusterMiner`, `Dendrogram`, `ClusterSet`, `Cluster` |
| `distance` | `ClusterDistance`, `SingleLinkDistance`, `AverageLinkDistance` |
| `data` | `Data`, `Example` |
| `database` | `DbAccess`, `TableData`, `TableSchema`, `Column` |

**Client (Vaadin UI)**

| Package | Classi principali |
|---|---|
| `layout` | `MainView`, `DbView`, `FileView` |
| `controller` | `DendrogramService` |

---

## Note

- Estensioni valide per il salvataggio: `.bin`, `.dat`, `.txt`, `.csv`, `.xml`, `.json`, `.ser`
- Non è possibile sovrascrivere un file già esistente
- Il nome del file non può contenere caratteri speciali
- Se il server non è avviato, l'interfaccia mostra il messaggio: *"Il server non è disponibile. Riprova più tardi."*