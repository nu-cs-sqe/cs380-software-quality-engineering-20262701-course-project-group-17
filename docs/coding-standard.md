# Team Coding Standard

We follow Robert C. Martin's *Clean Code*. Checkstyle enforces the parts a tool can check ([config/checkstyle/checkstyle.xml](../config/checkstyle/checkstyle.xml)), and `./gradlew check` fails on any violation, both locally and in CI. Code review covers the rest. Chapter numbers below refer to the book.

## Names (Ch. 2)
- Names should say what a thing is for. `requireOnBoard(index, axis)`, not `check(i, s)`.
- No encodings or prefixes: no `m_board`, no `IPiece`.
- Classes are nouns (`Board`, `Position`). Methods are verbs or questions (`placePiece`, `isEmpty`, `isSameColorAs`).
- Use one word per concept across the codebase. A board square is always a `Position`, never sometimes a `Square` and sometimes a `Location`.

## Functions (Ch. 3)
- Keep functions small: 20 lines at most (enforced), and usually much shorter.
- A function does one thing at one level of abstraction.
- Three parameters at most (enforced). If you need more, the arguments probably belong in an object.
- Wrap complicated conditions in a well-named method (G28), e.g. `if (isOffBoard(index))`.
- No flag arguments, and no output arguments.

## Comments (Ch. 4)
- Let the code explain itself first. If a comment is needed to explain a block, extract that block into a well-named method instead.
- Don't leave commented-out code, banner or position-marker comments, or comments that just repeat the code.
- Comments are fine for explaining *why* something unusual was done.

## Formatting (Ch. 5)
- Indent with 4 spaces, never tabs. Lines are at most 120 characters.
- Always use braces, even around one-line `if` bodies.
- Order each class top-down: constants, fields, constructors, then public methods, each followed by the private helpers it calls (the stepdown rule).

## Objects and errors (Ch. 6 and 7)
- Fields are private. Value classes (`Position`, `Piece`) are immutable and `final`.
- Throw unchecked exceptions with a message that gives context, e.g. "row must be between 0 and 7, but was 8".
- Don't return `null`; return `Optional` or an empty object instead. Don't accept `null`; reject it at the constructor.
- Name your numbers (G25): `BOARD_SIZE` instead of `8`. Tests are the exception, since the BVA values are what the test is about.

## Imports (J1)
- Prefer wildcard imports when you use two or more classes from a package, e.g. `import static org.junit.jupiter.api.Assertions.*;`.

## Tests (Ch. 9)
- Follow the three laws of TDD: no production code without a failing test first.
- Each test checks a single concept and reads as build, operate, check.
- Name tests `methodUnderTest_condition_expectedResult`. Each BVA file in `docs/bva/` lists the test method for every test case, so the code needs no `// TC` comments.
- Test both sides of every boundary (T5). Coverage and mutation testing confirm that we did.

## Quality gates (all run in CI)
- Every non-view, non-enum class has a BVA file in `docs/bva/`, and every test case in it has a test.
- `domain` has 100% line and branch coverage (JaCoCo).
- `domain` has 100% of mutants killed (PIT). Any equivalent mutant goes in the README under Exceptions.

## Git workflow
- Nobody commits directly to `main`. Branch protection requires a pull request with one approval and passing CI.
- Branch names are `feature/<thing>`, `chore/<thing>`, or `docs/<thing>`.
- TDD commits come in pairs: `test: TC3-TC4 <behavior> (red)` followed by `feat: TC3-TC4 <behavior> (green)`. Add `refactor: <what>` commits when you clean up.
