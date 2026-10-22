# Bank Account — TDD with JUnit 5 & AssertJ

![Java](https://img.shields.io/badge/Java-21-orange) ![JUnit5](https://img.shields.io/badge/JUnit-5.11-green) ![AssertJ](https://img.shields.io/badge/AssertJ-3.27-blue) ![Maven](https://img.shields.io/badge/Maven-3.9-red) ![Tests](https://img.shields.io/badge/tests-21%20passing-brightgreen)

A small banking library built strictly with **Test-Driven Development**: all 21 tests were written first, then the code was added one method at a time until every test was green.
Part of my 52-week QA Automation roadmap — **Week 1**.

## Red → Green → Refactor, visible in the commit history

| Commit | Step | Passing tests |
|---|---|---|
| `test: add failing tests` | 🔴 Red — tests only, every method throws `UnsupportedOperationException` | 1 / 21 |
| `feat: implement deposit` | 🟢 Green | 8 / 21 |
| `feat: implement withdraw` | 🟢 Green | 13 / 21 |
| `feat: validate account number and initial balance` | 🟢 Green | 17 / 21 |
| `feat: implement transfer` | 🟢 Green | **21 / 21** |
| `refactor: extract amount validation` | 🔵 Refactor — no behaviour change | 21 / 21 |

## Features
- Create an account (account number must not be blank, initial balance must not be negative)
- Deposit / withdraw with validation (amount must be positive)
- Transfer between accounts — a failed transfer changes nothing
- Custom `InsufficientFundsException`

## Experiment: why the order inside `transfer` matters

```java
withdraw(amount);        // 1. take the money
target.deposit(amount);  // 2. then give it
```

I swapped the two lines and ran the tests. One test turned red:

```
BankAccountTest$Transfer.failedTransferChangesNothing
expected: 0.0
 but was: 1000.0
```

With the wrong order, the target account receives the money **before** the withdrawal fails for insufficient funds — 1,000 € appear out of nowhere. Only the test that checks the *failed* path catches this; every happy-path test stays green.

## Testing techniques used

| Technique | Where |
|---|---|
| `@Nested` test grouping | Creation / Deposit / Withdraw / Transfer |
| `@ParameterizedTest` + `@CsvSource`, `@ValueSource`, `@NullAndEmptySource` | Boundary and invalid values (`0`, `-0.01`, whole balance, blank account number) |
| AssertJ fluent and exception assertions | All tests |
| JUnit Platform Launcher (`TestRunner`) and `@Suite` (`RegressionSuite`) | Running test classes from a `main` method or a suite |

## Run

```bash
mvn test
```

```
Tests run: 21, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## What I learned
- _TODO: 2–3 sentences in your own words_
