# BVA: `domain.Move`

A `Move` is an immutable `(from, to)` pair. En passant uses it to look at the last move that was played (issue [#23](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/23)).

## Steps 1-3: Input analysis
| Input | Kind | Valid range | Values tested |
|-------|------|-------------|---------------|
| `from` | `Position` reference | any square; not `null` | (1,3), (3,3), `null` |
| `to` | `Position` reference | any square other than `from`; not `null` | (3,3), the same instance as `from`, a separate but equal `Position`, `null` |

A move that stays on the same square isn't a move, so `from` equal to `to` is rejected. It's tested with the same instance and with a separate but equal `Position`, so the check has to use `equals` and not `==`.

## Step 4: Test cases
All tests are in `src/test/java/domain/MoveTest.java`.

### Method under test: `Move(Position from, Position to)`, `getFrom()`, `getTo()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | from (1,3), to (3,3) | `getFrom()` is (1,3) and `getTo()` is (3,3) | `constructor_twoSquares_keepsFromAndTo` | :white_check_mark: |
| TC2 | `from` is `null` | `NullPointerException`: "from must not be null" | `constructor_nullFrom_throwsNullPointerException` | :white_check_mark: |
| TC3 | `to` is `null` | `NullPointerException`: "to must not be null" | `constructor_nullTo_throwsNullPointerException` | :white_check_mark: |
| TC4 | from and to are the same (3,3) instance | `IllegalArgumentException`: "a move must change square" | `constructor_sameSquare_throwsIllegalArgumentException` | Not yet |
| TC5 | from (3,3) and a separate `Position(3,3)` as to | `IllegalArgumentException`: "a move must change square" | `constructor_sameSquare_throwsIllegalArgumentException` | Not yet |
