# BVA: `domain.move.PawnRule`

`PawnRule` lists a pawn's forward moves and diagonal captures. It covers issue [#19](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/19). The requirements are in [pawn-movement.md](../requirements/pawn-movement.md).

The method under test is `destinations(Board board, Position from, PieceColor color)`. Like the other rules, it's package-private and trusts its caller, so there are no `null` cases here (see [design/piece-movement.md](../design/piece-movement.md), decision 2).

## Steps 1-3: Input analysis
| Input | Kind | Values tested |
|-------|------|---------------|
| `color` | `PieceColor` | `WHITE` (moves toward row 0), `BLACK` (moves toward row 7) |
| `from` row | interval 0 to 7 | its own starting row (6 for White, 1 for Black), the row right after it (5 / 2), the middle (4 / 3), the other side's starting row (1 for White, 6 for Black), and the last row (0 / 7) |
| `from` col | interval 0 to 7 | col 0 and col 7 (one capture square is off the board), and col 3 |
| One square ahead | empty, own, enemy | empty, own piece, enemy piece |
| Two squares ahead | empty or occupied | empty, occupied |
| The two diagonal squares ahead | empty, own, enemy | empty, own pieces, enemy pieces |

The starting row is a boundary for the two-step move: one row later the pawn can only move one square. The other side's starting row is a trap for code that checks "row 1 or row 6" without looking at the color. On the last row the pawn has nowhere to go, which only happens before promotion replaces it.

## Step 4: Test cases
All tests are in `src/test/java/domain/move/PawnRuleTest.java`.

### Forward moves
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | white pawn on (5,3) (one row after its start), empty board | (4,3) | `pawn_afterStartRow_movesOneSquare` | :white_check_mark: |
| TC2 | black pawn on (2,3) (one row after its start), empty board | (3,3) | `pawn_afterStartRow_movesOneSquare` | :white_check_mark: |
| TC3 | white pawn on (6,3) (its start row), empty board | (5,3), (4,3) | `pawn_onStartRow_canMoveOneOrTwoSquares` | :white_check_mark: |
| TC4 | black pawn on (1,3) (its start row), empty board | (2,3), (3,3) | `pawn_onStartRow_canMoveOneOrTwoSquares` | :white_check_mark: |
| TC5 | white pawn on (1,3) (Black's start row) | (0,3) only | `pawn_onOtherSidesStartRow_movesOneSquare` | Not yet |
| TC6 | black pawn on (6,3) (White's start row) | (7,3) only | `pawn_onOtherSidesStartRow_movesOneSquare` | Not yet |
| TC7 | white pawn on (6,3), any piece on (5,3) | no moves | `pawn_squareAheadTaken_cannotMoveForward` | Not yet |
| TC8 | white pawn on (6,3), any piece on (4,3) | (5,3) only | `pawn_twoAheadTaken_movesOneSquare` | Not yet |
| TC9 | white pawn on (0,3) (last row) | no moves | `pawn_onLastRow_hasNoMoves` | Not yet |
| TC10 | black pawn on (7,3) (last row) | no moves | `pawn_onLastRow_hasNoMoves` | Not yet |

### Captures
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC11 | white pawn on (4,3), black pawns on (3,2) and (3,4) | (3,3), (3,2), (3,4) | `pawn_enemiesDiagonallyAhead_canCaptureBoth` | Not yet |
| TC12 | black pawn on (3,3), white pawns on (4,2) and (4,4) | (4,3), (4,2), (4,4) | `pawn_enemiesDiagonallyAhead_canCaptureBoth` | Not yet |
| TC13 | white pawn on (4,3), white pawns on (3,2) and (3,4) | (3,3) only | `pawn_ownPiecesDiagonallyAhead_cannotCapture` | Not yet |
| TC14 | white pawn on (4,3), black pawn on (3,3) straight ahead | no moves | `pawn_enemyStraightAhead_cannotCapture` | Not yet |
| TC15 | white pawn on (4,0), black pawn on (3,1) | (3,0), (3,1) | `pawn_onEdgeCol_capturesOnlyInward` | Not yet |
| TC16 | white pawn on (4,7), black pawn on (3,6) | (3,7), (3,6) | `pawn_onEdgeCol_capturesOnlyInward` | Not yet |
| TC17 | white pawn on (4,3), black pawns on (5,2) and (5,4) (diagonally behind) | (3,3) only | `pawn_enemiesBehind_cannotCaptureBackward` | Not yet |
