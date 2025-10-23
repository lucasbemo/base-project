# Base Project

A small Spring Boot demo project that provides:

- Hexagonal-architecture dataset modules (Users, Cards, Transactions)
- CSV importers that load dataset files into a relational database
- REST CRUD endpoints for working with entities
- Audit logging for dataset imports

This README documents architecture, how to run locally, endpoints, logging, and attribution.

---

## Project Goal

**This project serves as a base for practicing spec-driven development techniques on an existing codebase.**

Spec-driven development is a methodology where:
- API specifications (OpenAPI/Swagger) are written first, defining contracts between services
- Code generation tools create interfaces, DTOs, and client stubs from the specifications
- Development follows the defined contracts, ensuring consistency between documentation and implementation
- Teams can work in parallel: frontend teams consume API specs while backend teams implement them

This project provides a realistic, working Spring Boot application with:
- Existing REST endpoints that can be documented with OpenAPI annotations
- A hexagonal architecture that separates concerns and makes refactoring easier
- Real data models (Users, Cards, Transactions) for practicing API design
- Multiple integration points (database, CSV import) to explore contract testing

**Use this project to:**
1. Practice adding OpenAPI specifications to existing REST endpoints
2. Learn to generate API documentation from annotations
3. Experiment with API-first design patterns on a realistic codebase
4. Explore contract-driven testing between layers
5. Refactor existing code to align with formal API specifications

---

## Project purpose

This project demonstrates how to:

- Organize dataset ingestion and persistence using a hexagonal (ports & adapters) architecture.
- Import large CSV datasets into a database via a small, testable loader component.
- Expose CRUD APIs for domain entities (Users, Cards, Transactions).
- Audit dataset import activity using structured logs and an audit logger.

Use this project as a starting point for data import pipelines, prototypes, or learning examples.

---

## Project structure (high level)

- `src/main/java/com/lz/base/project/dataset/entity` — JPA entities (UserEntity, CardEntity, TransactionEntity)
- `src/main/java/com/lz/base/project/dataset/repository` — Spring Data JPA repositories
- `src/main/java/com/lz/base/project/dataset/port` — Port interfaces (UserPort, CardPort, TransactionPort)
- `src/main/java/com/lz/base/project/dataset/adapter/jpa` — JPA adapters implementing the ports
- `src/main/java/com/lz/base/project/dataset/service` — Business services (UserService, CardService, TransactionService)
- `src/main/java/com/lz/base/project/dataset/controller` — REST controllers for CRUD endpoints
- `src/main/java/com/lz/base/project/dataset/loader` — CSV loaders and CSV utilities (CsvUtils, loader components)
- `dataset/` — example CSV data files (users_data.csv, cards_data.csv, transactions_data.csv)

---

## Key concepts implemented

- Hexagonal (ports & adapters) architecture: service layer depends on ports, adapters implement persistence.
- CSV parsing: `CsvUtils` provides a simple parser for quoted CSV fields and helpers for numeric/money parsing.
- Loaders: Each dataset has a loader component implementing `CommandLineRunner` for optional startup import. Loaders also expose `loadFromFile(Path)` so imports can be triggered manually.
- Manual import endpoints: POST `/api/dataset/users/import` and POST `/api/dataset/transactions/import` and POST `/api/dataset/cards/import` are available to trigger imports at runtime.
- Audit logging: imports are recorded as structured audit messages via the `AuditLogger` into the configured logging system (console + `logs/audit.log` by default).

---

## Dataset Setup

**Important:** Dataset files are NOT included in this repository. You must download them separately.

### Quick Start - Using Makefile (Recommended)

```bash
# Download datasets automatically
make setup-dataset
```

This will:
- Download the dataset from Kaggle using curl
- Extract CSV files to the `dataset/` directory
- Show file sizes when complete

**Note:** If automatic download fails (Kaggle may require login), you can download manually and extract:

```bash
# 1. Download manually from:
#    https://www.kaggle.com/datasets/computingvictor/transactions-fraud-datasets
# 2. Place the zip file in dataset/
# 3. Extract:
make extract-dataset
```

### Available Makefile Commands

```bash
make help              # Show available commands
make download-dataset  # Download and extract CSV files from Kaggle  
make extract-dataset   # Extract if you downloaded the zip manually
make clean-dataset     # Remove downloaded dataset files
make setup-dataset     # Full setup: download + extract
```

### Dataset Files Explanation

After download, you'll have these files in the `dataset/` directory:

#### 1. `users_data.csv` (~161 KB, ~2,000 records)
User profile information including:
- Personal details: age, gender, birth year/month
- Location: address, latitude, longitude
- Financial profile: income levels, debt, credit score
- Credit card count

**Schema:**
```
id, current_age, retirement_age, birth_year, birth_month, gender, address, 
latitude, longitude, per_capita_income, yearly_income, total_debt, 
credit_score, num_credit_cards
```

#### 2. `cards_data.csv` (~498 KB, ~6,000 records)
Credit/debit card information linked to users:
- Card details: brand (Visa/Mastercard), type (Credit/Debit/Prepaid)
- Card number, expiry date, CVV
- Account info: credit limit, open date, PIN change history
- Security flags: chip status, dark web exposure

**Schema:**
```
id, client_id, card_brand, card_type, card_number, expires, cvv, has_chip, 
num_cards_issued, credit_limit, acct_open_date, year_pin_last_changed, 
card_on_dark_web
```

#### 3. `transactions_data.csv` (~1.2 GB, ~24 million records)
Transaction records for fraud detection analysis:
- Transaction metadata: date, amount, chip vs swipe
- Merchant info: ID, city, state, ZIP, MCC (Merchant Category Code)
- Links to user (client_id) and card (card_id)
- Error flags

**Schema:**
```
id, date, client_id, card_id, amount, use_chip, merchant_id, merchant_city, 
merchant_state, zip, mcc, errors
```

**Note:** This file is very large (1.2 GB). The import may take several minutes depending on your system.

---

## How to run locally (recommended)

Prerequisites
- Java 21 (or compatible)
- Maven (the project includes `mvnw` so you can use the wrapper)
- Docker & docker-compose (to run Postgres locally)

Steps

1. **Configure environment variables**

```bash
# Copy the example environment file
cp .env.example .env

# Edit .env with your preferred values (optional - defaults work fine)
# The .env file is gitignored and will not be committed
```

2. **Start Postgres** (docker-compose in repo)

```bash
# from repo root (where docker-compose.yml lives)
docker-compose up -d postgres
```

The compose file maps Postgres to a host port. Check `docker-compose ps` to see the host port (commonly 5432 or 5433). If needed set `POSTGRES_PORT` when starting compose.

3. **Build and run the app**

```bash
# build
./mvnw -DskipTests package

# run
./mvnw spring-boot:run
```

3. Trigger imports manually (recommended rather than automatic startup)

```bash
# Import users
curl -X POST http://localhost:8080/api/dataset/users/import

# Import transactions
curl -X POST http://localhost:8080/api/dataset/transactions/import

# Import cards
curl -X POST http://localhost:8080/api/dataset/cards/import
```

Notes
- By default the loaders run at application startup (they are `CommandLineRunner` beans). To prevent automatic imports during startup, set the property `dataset.import.onstartup=false` in `src/main/resources/application.properties` or supply it on the command line.
- If your Postgres container maps to a non-standard host port (e.g., 5433), export `POSTGRES_PORT` when running the app so `spring.datasource.url` uses the correct host port.

---

## REST API endpoints (dataset)

All dataset endpoints are under `/api/dataset`.

Users
- GET `/api/dataset/users` — list
- GET `/api/dataset/users/{id}` — get
- POST `/api/dataset/users` — create
- PUT `/api/dataset/users/{id}` — update
- DELETE `/api/dataset/users/{id}` — delete
- POST `/api/dataset/users/import` — trigger CSV import

Cards
- GET `/api/dataset/cards`
- GET `/api/dataset/cards/{id}`
- POST `/api/dataset/cards`
- PUT `/api/dataset/cards/{id}`
- DELETE `/api/dataset/cards/{id}`
- POST `/api/dataset/cards/import` — trigger CSV import

Transactions
- GET `/api/dataset/transactions`
- GET `/api/dataset/transactions/{id}`
- POST `/api/dataset/transactions`
- PUT `/api/dataset/transactions/{id}`
- DELETE `/api/dataset/transactions/{id}`
- POST `/api/dataset/transactions/import` — trigger CSV import

---

## Logging & audit

- Application logs (INFO/ERROR) go to console by default.
- Audit logs for dataset imports are emitted to the logger named `audit` and are routed to `${LOG_HOME:-./logs}/audit.log` by default. You can change `LOG_HOME` environment variable to write elsewhere.
- Audit message example (structured key=value):
	- `loader=UsersCsvLoader start=... end=... durationMs=123 saved=456`

---

## Data sources / attribution

The transactions dataset used in this repository is derived from publicly available data and should be credited as follows:

- "Transactions Fraud Datasets" by computingvictor on Kaggle — https://www.kaggle.com/datasets/computingvictor/transactions-fraud-datasets

Please review the original dataset's license and terms on Kaggle before redistributing or using the data in production.

---

## Next improvements (ideas)

- Add job status endpoints (async import with job ids and progress)
- Batch inserts for large CSVs and transaction batching to improve performance
- Use Flyway or Liquibase for deterministic schema migrations instead of `ddl-auto=update` for production
- Add integration tests for loaders and controllers

---

If you want I can also:
- Add a separate `CREDITS` or `NOTICE` file with licenses and attributions
- Add a docker-compose `app` service so the app runs inside the same network as Postgres
- Implement an async import job with a status API
