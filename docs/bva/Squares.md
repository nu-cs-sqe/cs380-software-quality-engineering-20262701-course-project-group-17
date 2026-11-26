# BVA: `domain.move.Squares`

`Squares` answers the two questions every movement rule asks about a target square. It's package-private and only used by the rules in `domain.move`, which trust their caller (see [design/piece-movement.md](../design/piece-movement.md), decision 2), so it has no `null` cases.

## Steps 1-3: Input analysis
| Input | Kind | Values tested |
|-------|------|---------------|
| What stands on `position` | empty or one piece | empty, a piece of the same color, a piece of the other color |
| `color` | `PieceColor` | `WHITE`, `BLACK` |

The three square states are the whole input space. Both colors are tested so that "enemy" can't be hard-coded to one side.

## Step 4: Test cases
All tests are in `src/test/java/domain/move/SquaresTest.java`.

### Method under test: `isEnemy(Board board, Position position, PieceColor color)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | empty square, `WHITE` | `false` | `isEnemy_emptySquare_returnsFalse` | Not yet |
| TC2 | black piece on the square, `WHITE` | `true` | `isEnemy_otherColor_returnsTrue` | Not yet |
| TC3 | white piece on the square, `BLACK` | `true` | `isEnemy_otherColor_returnsTrue` | Not yet |
| TC4 | white piece on the square, `WHITE` | `false` | `isEnemy_sameColor_returnsFalse` | Not yet |

### Method under test: `isEmptyOrEnemy(Board board, Position position, PieceColor color)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC5 | empty square, `WHITE` | `true` | `isEmptyOrEnemy_emptySquare_returnsTrue` | Not yet |
| TC6 | black piece on the square, `WHITE` | `true` | `isEmptyOrEnemy_otherColor_returnsTrue` | Not yet |
| TC7 | black piece on the square, `BLACK` | `false` | `isEmptyOrEnemy_sameColor_returnsFalse` | Not yet |
