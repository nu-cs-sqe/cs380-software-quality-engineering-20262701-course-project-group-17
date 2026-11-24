![Gradle Build](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/actions/workflows/main.yml/badge.svg)

# Chess (CS 380 Group 17)

A two-player desktop chess game with a Java Swing GUI.

## Contributors
- Ziqian Kris Ni
- Max Zeng
- Cindy Jiang

## Dependencies
- JDK 11
- Gradle 8.10 (wrapper included)
- JUnit 5.10, EasyMock 5.4
- Java Swing
- Checkstyle 10.12, SpotBugs 4.8, JaCoCo 0.8.11, PIT 1.15

## Commands
| What | Command |
|------|---------|
| Run the game | `./gradlew run` |
| Tests, Checkstyle, SpotBugs, and the 100% coverage gate | `./gradlew check` |
| Mutation testing | `./gradlew pitest` |

Reports: `build/reports/tests`, `build/reports/jacoco`, `build/reports/checkstyle`, `build/reports/spotbugs`, `build/reports/pitest`.

## Documentation
- Rules: [docs/requirements/game-rules.md](docs/requirements/game-rules.md)
- Feature requirements: [docs/requirements](docs/requirements)
- Design: [docs/design](docs/design)
- BVA: [docs/bva](docs/bva)
- Coding standard: [docs/coding-standard.md](docs/coding-standard.md)
- Weekly reports: [docs/weekly-reports.md](docs/weekly-reports.md)

## Exceptions
### Code Coverage
`ui.*` (View classes and `Main`) and enums are excluded, as the rubric allows.
### Mutation Testing
`ui.*` and enums are excluded. No equivalent mutants so far.
### Others
N/A
