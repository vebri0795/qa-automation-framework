# QA Automation Framework

A Java test automation portfolio project covering both **API** and **UI**
testing, with rich reporting via Allure.

It tests two public, free, no-auth-required targets:

- **API**: [JSONPlaceholder](https://jsonplaceholder.typicode.com) (fake REST API for `/posts`)
- **UI**: [the-internet.herokuapp.com](https://the-internet.herokuapp.com/login) (login form)

## Tech stack

- **Java 17** + **Maven**
- **JUnit 5** — test framework (lifecycle, parameterized tests)
- **OkHttp** + **org.json** — API client and JSON parsing
- **Selenide** — UI automation (wraps Selenium WebDriver)
- **Allure** (`allure-junit5`, `allure-selenide`) — test reporting, steps,
  severities, and automatic screenshots on UI failures

## Project structure

```
src/test/java/com/testautomation/
├── api/
│   ├── base/      BaseApiTest      – shared setup (provides apiClient)
│   ├── client/    PostsApiClient   – reusable HTTP client with @Step methods
│   ├── models/    Post             – POJO for /posts (immutable, with factory methods)
│   └── tests/     AllureModelBasedApiTest – parameterized GET/POST tests + Allure annotations
└── ui/
    ├── base/      BaseUiTest       – Selenide config + Allure screenshot listener
    ├── pages/     LoginPage        – Page Object for the login form
    └── tests/     LoginPageTest    – valid/invalid login scenarios
```

## Requirements

- Java 17+
- Maven
- Google Chrome (used by Selenide/Selenium for UI tests)
- Internet access (tests hit real public endpoints)

## Running the tests

From the project root:

```bash
mvn clean test
```

Run a single class:

```bash
mvn test -Dtest=AllureModelBasedApiTest
mvn test -Dtest=LoginPageTest
```

## Viewing the Allure report

Requires the [Allure CLI](https://allurereport.org/docs/install/) installed separately.

```bash
mvn clean test
allure serve target/allure-results
```

The report shows tests grouped by Epic/Feature/Story, with step-by-step
breakdowns. Failed UI tests include an automatic screenshot and page source.

## What this project demonstrates

- **API automation**: a reusable HTTP client layer (`PostsApiClient`) used
  across multiple test classes, with parameterized tests
  (`@ParameterizedTest` + `@ValueSource`/`@MethodSource`).
- **UI automation**: Page Object Model with Selenide, including a known Java
  gotcha around static-import shadowing (see comments in `LoginPage`).
- **Reporting**: Allure annotations (`@Epic`, `@Feature`, `@Story`,
  `@Severity`, `@Description`, `@Step`) and automatic screenshot capture on
  UI test failures via `allure-selenide`.
- **OOP fundamentals**: immutability, factory methods, encapsulation,
  inheritance (`BaseApiTest`/`BaseUiTest`) and composition (`apiClient`).