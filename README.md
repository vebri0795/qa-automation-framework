# QA Automation Framework

A Java test automation portfolio project covering **API**, **UI**, and
**database** testing, with rich reporting via Allure and a CI pipeline that
runs everything automatically on every push.

It tests these targets:

- **API**: [JSONPlaceholder](https://jsonplaceholder.typicode.com) (fake REST API for `/posts`)
- **UI**: [the-internet.herokuapp.com](https://the-internet.herokuapp.com/login) (login form)
- **Database**: a local MySQL instance (via Docker), seeded with test data

## Tech stack

- **Java 17** + **Maven**
- **JUnit 5** — test framework (lifecycle, parameterized tests)
- **OkHttp** + **org.json** — API client and JSON parsing
- **Selenide** — UI automation (wraps Selenium WebDriver)
- **JDBC** (`mysql-connector-j`) — database access and validation
- **Docker** / **docker-compose** — local MySQL environment
- **Allure** (`allure-junit5`, `allure-selenide`) — test reporting, steps,
  severities, and automatic screenshots on UI failures
- **GitHub Actions** — CI pipeline (MySQL service container, Chrome setup,
  test execution, Allure report publishing with history)

## Project structure

```
src/test/java/com/testautomation/
├── api/
│   ├── base/        BaseApiTest      – shared setup (provides apiClient)
│   ├── client/      PostsApiClient   – reusable HTTP client with @Step methods
│   ├── models/      Post             – POJO for /posts (immutable, with factory methods)
│   └── tests/       AllureModelBasedApiTest – parameterized GET/POST tests + Allure annotations
├── ui/
│   ├── base/         BaseUiTest      – Selenide config + Allure screenshot listener
│   ├── pages/         LoginPage      – Page Object for the login form
│   └── tests/         LoginPageTest  – valid/invalid login scenarios
└── db/
    ├── models/         User          – POJO for the "users" table
    ├── repository/     UserRepository – encapsulates JDBC queries with @Step methods
    └── tests/           UserRepositoryTest – validates query results against seed data

db/init.sql            – schema + seed data, loaded automatically by docker-compose
docker-compose.yml      – local MySQL container definition
.github/workflows/ci.yml – CI pipeline definition
```

## Requirements

- Java 17+
- Maven
- Docker (for the database tests)
- Google Chrome (used by Selenide/Selenium for UI tests)
- Internet access (API and UI tests hit real public endpoints)

## Running the tests locally

Start the database first (required for the `db` tests):

```bash
docker compose up -d
```

Then run all tests from the project root:

```bash
mvn clean test
```

Run a single class:

```bash
mvn test -Dtest=AllureModelBasedApiTest
mvn test -Dtest=LoginPageTest
mvn test -Dtest=UserRepositoryTest
```

Stop the database when done:

```bash
docker compose down
```

## Viewing the Allure report locally

Requires the [Allure CLI](https://allurereport.org/docs/install/) installed separately.

```bash
mvn clean test
allure serve target/allure-results
```

The report shows tests grouped by Epic/Feature/Story, with step-by-step
breakdowns. Failed UI tests include an automatic screenshot and page source.

## Continuous Integration

Every push to `main` (and every pull request) triggers the workflow in
`.github/workflows/ci.yml`, which:

1. Starts a MySQL service container and loads the schema/seed data.
2. Installs Chrome for the UI tests.
3. Runs the full test suite (API + UI + database) with `mvn clean test`.
4. Generates an Allure report, merging it with the history from previous
   runs, and publishes it to GitHub Pages — so trends (pass/fail over time,
   flaky test detection) accumulate across builds, not just within a single
   local run.

## What this project demonstrates

- **API automation**: a reusable HTTP client layer (`PostsApiClient`) used
  across multiple test classes, with parameterized tests
  (`@ParameterizedTest` + `@ValueSource`/`@MethodSource`).
- **UI automation**: Page Object Model with Selenide, including a known Java
  gotcha around static-import shadowing (see comments in `LoginPage`).
- **Database testing**: a repository layer (`UserRepository`) wrapping JDBC
  with `PreparedStatement` (SQL-injection-safe), `Optional` for queries that
  may not match, and `try-with-resources` for connection/statement/result
  set cleanup.
- **Containerized test infrastructure**: a Dockerized MySQL environment
  defined in `docker-compose.yml`, reused as-is in CI via a GitHub Actions
  service container.
- **CI/CD**: a GitHub Actions pipeline running the entire suite on every
  push, with Allure report history accumulated across builds.
- **Reporting**: Allure annotations (`@Epic`, `@Feature`, `@Story`,
  `@Severity`, `@Description`, `@Step`) and automatic screenshot capture on
  UI test failures via `allure-selenide`.
- **OOP fundamentals**: immutability, factory methods, encapsulation,
  inheritance (`BaseApiTest`/`BaseUiTest`) and composition (`apiClient`,
  `UserRepository`).
