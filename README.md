# Bank Account — TDD with JUnit 5 & AssertJ

![Java](https://img.shields.io/badge/Java-21-orange) ![JUnit5](https://img.shields.io/badge/JUnit-5-green) ![AssertJ](https://img.shields.io/badge/AssertJ-3-blue) ![Maven](https://img.shields.io/badge/Maven-3.9-red)

A small banking library built with **Test-Driven Development** (Red → Green → Refactor).
Part of my 26-week QA Automation roadmap — **Week 1**.

## Features
- Create an account (validation for account number and initial balance)
- Deposit / Withdraw with input validation
- Transfer between accounts (atomic: a failed transfer changes nothing)
- Custom `InsufficientFundsException`

## Testing techniques used
| Technique | Where |
|---|---|
| `@Nested` test grouping | Creation / Deposit / Withdraw / Transfer |
| `@ParameterizedTest` + `@CsvSource`, `@ValueSource`, `@NullAndEmptySource` | Boundary & invalid values |
| `@DisplayName` | Readable test reports |
| AssertJ fluent assertions & exception assertions | All tests |

## Run
```bash
mvn test
```

## What I learned
- _TODO: write 2–3 sentences after finishing the week_