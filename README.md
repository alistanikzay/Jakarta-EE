# Book API – Jakarta EE 11

## 🧾 Beskrivning
Detta projekt är ett REST:ful API byggt med Jakarta EE 11. Applikationen hanterar böcker och implementerar grundläggande CRUD-operationer: skapa, läsa, uppdatera och ta bort bokposter. Projektet fokuserar på att uppfylla G-nivåkraven för kursen.

Funktioner:
- CRUD-operationer för böcker (skapa, läsa, uppdatera, ta bort)
- Validering av indata via **Jakarta Bean Validation**
- Användning av **JPA (Jakarta Persistence)** för entitetsmappning
- DTO-klasser och en mapper för att separera entiteter från externa representationer
- Enkel felhantering med egna undantag
- Enhetstester för service- och mapper-lager

## 🧪 Testning

Enhetstester har implementerats för:
- `BookMapperTest`: testar korrekt mappning mellan DTO och entitet.
- `BookServiceTest`: testar affärslogik för bokhantering med hjälp av mockade beroenden.

Båda testerna har körts och **passerat utan fel**.

Tekniskt använder projektet:
- Jakarta EE 11 med Payara Micro som applikationsserver
- PostgreSQL som databas
- Maven som byggverktyg
- Docker och Docker Compose för containerizing och enklare distribution

## 🛠️ Bygga och köra applikationen

### 🔹 Bygga och köra med Maven (lokalt)
1. Bygg projektet:
```bash
mvn clean install
