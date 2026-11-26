# BVA: `domain.move.EnPassant`

`EnPassant` covers issue [#23](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/23). The requirements are in [en-passant.md](../requirements/en-passant.md). It has two public methods: `destination(Board, Position, Move)` and `capturedPawn(Move)`.

## Steps 1-3: Input analysis
| Input | Kind | Values tested |
|-------|------|---------------|
| Piece on `from` | empty, pawn, other piece | a white pawn, a black pawn, a white knight, nothing |
| Row distance of `lastMove` | interval | 1 (a single step), 2 (a double step) |
| Col distance of `lastMove` | interval | 0 (straight), 1 (sideways, as a knight would move) |
| Piece that made `lastMove` (on `lastMove.to`) | pawn or other, own or enemy | an enemy pawn, an enemy rook, an own pawn |
| Where `lastMove` ended, compared with `from` | same row and col distance 1, 2, or different row | beside on the left, beside on the right, two columns away, one row away |
| How recent the double step is | last move or earlier | the last move, and a double step followed by another move |
| `board`, `from`, `lastMove`, `enPassantMove` | references | `null` for each |

The en passant capture only exists in one exact situation, so each condition is tested right at its edge: a row distance of 2 against 1, a col distance of 0 against 1, a column next to the pawn against two columns away, and the same row against the next row. Every invalid case changes only one condition from a valid one.

## Step 4: Test cases
All tests are in `src/test/java/domain/move/EnPassantTest.java`.

### Method under test: `destination(Board board, Position from, Move lastMove)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | white pawn on (3,4); black pawn just moved (1,3) to (3,3) | `Optional.of(Position(2,3))` | `destination_enemyPawnDoubleStepBeside_returnsSkippedSquare` | :white_check_mark: |
| TC2 | white pawn on (3,2); black pawn just moved (1,3) to (3,3) | `Optional.of(Position(2,3))` | `destination_enemyPawnDoubleStepBeside_returnsSkippedSquare` | :white_check_mark: |
| TC3 | black pawn on (4,4); white pawn just moved (6,3) to (4,3) | `Optional.of(Position(5,3))` | `destination_enemyPawnDoubleStepBeside_returnsSkippedSquare` | :white_check_mark: |
| TC4 | white pawn on (3,4); black pawn just moved (2,3) to (3,3) (single step) | `Optional.empty()` | `destination_singleStep_returnsEmpty` | :white_check_mark: |
| TC5 | white pawn on (3,5); black pawn just moved (1,3) to (3,3) (two columns away) | `Optional.empty()` | `destination_pawnNotBeside_returnsEmpty` | :white_check_mark: |
| TC6 | white pawn on (4,4); black pawn just moved (1,3) to (3,3) (next row) | `Optional.empty()` | `destination_pawnNotBeside_returnsEmpty` | :white_check_mark: |
| TC7 | white pawn on (3,4); black rook just moved (1,3) to (3,3) | `Optional.empty()` | `destination_lastMoveNotByAPawn_returnsEmpty` | Not yet |
| TC8 | white pawn on (3,4); a black pawn on (3,3) got there by the last move (1,2) to (3,3), which isn't straight | `Optional.empty()` | `destination_lastMoveNotStraight_returnsEmpty` | Not yet |
| TC9 | white pawn on (3,4); white pawn on (3,3) after the last move (1,3) to (3,3) | `Optional.empty()` | `destination_ownPawnMoved_returnsEmpty` | Not yet |
| TC10 | white knight on (3,4); black pawn just moved (1,3) to (3,3) | `Optional.empty()` | `destination_notAPawn_returnsEmpty` | Not yet |
| TC11 | nothing on (3,4); black pawn just moved (1,3) to (3,3) | `Optional.empty()` | `destination_emptySquare_returnsEmpty` | Not yet |
| TC12 | white pawn on (3,4); black pawn on (3,3) moved there earlier, and the last move was a black knight (0,1) to (2,2) | `Optional.empty()` | `destination_doubleStepNotTheLastMove_returnsEmpty` | Not yet |
| TC13 | `board`, `from`, or `lastMove` is `null` | `NullPointerException` naming the argument: "board must not be null", "from must not be null", "lastMove must not be null" | `destination_nullArgument_throwsNullPointerException` | Not yet |

### Method under test: `capturedPawn(Move enPassantMove)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC14 | white capture (3,4) to (2,3) | (3,3) | `capturedPawn_enPassantMove_isBesideTheStartSquare` | Not yet |
| TC15 | black capture (4,4) to (5,5) | (4,5) | `capturedPawn_enPassantMove_isBesideTheStartSquare` | Not yet |
| TC16 | `null` | `NullPointerException`: "enPassantMove must not be null" | `capturedPawn_nullMove_throwsNullPointerException` | Not yet |
