# BVA: `domain.move.SlideRule`

A `SlideRule` moves a piece any number of squares in each of its directions until it reaches the edge or another piece. `SlideRule.rook()`, `bishop()` and `queen()` cover issue [#21](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/21). The requirements are in [sliding-movement.md](../requirements/sliding-movement.md).

The method under test is `destinations(Board board, Position from, PieceColor color)`. Like the other rules, it's package-private and trusts its caller, so there are no `null` cases here (see [design/piece-movement.md](../design/piece-movement.md), decision 2).

## Steps 1-3: Input analysis
| Input | Kind | Values tested |
|-------|------|---------------|
| `from` row and col | interval 0 to 7 | the corner (0,0) and the center (3,3) |
| Distance to the first piece along a direction | interval 1 to 7 | 1 (right next to the piece), 2, and no piece at all (the slide runs to the edge) |
| What the first piece is | own or enemy | own piece, enemy piece |
| `color` | `PieceColor` | `WHITE`, `BLACK` |

The boundaries along each line are the edge of the board and the first occupied square. For the first occupied square, the interesting values are the square itself (included for an enemy, excluded for an own piece) and the square right after it (always excluded, since a slide can't jump). A piece right next to the slider (distance 1) is the shortest possible slide.

## Step 4: Test cases
All tests are in `src/test/java/domain/move/SlideRuleTest.java`.

### Method under test: `destinations(...)` on an empty board
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | white rook on (3,3) | the 14 squares of row 3 and col 3, except (3,3) | `rook_centerOfEmptyBoard_hasFourteenMoves` | Not yet |
| TC2 | white rook on (0,0) | the 14 squares of row 0 and col 0, except (0,0) | `rook_cornerOfEmptyBoard_hasFourteenMoves` | Not yet |
| TC3 | white bishop on (3,3) | the 13 squares on its two diagonals | `bishop_centerOfEmptyBoard_hasThirteenMoves` | Not yet |
| TC4 | white bishop on (0,0) | the 7 squares (1,1) to (7,7) | `bishop_cornerOfEmptyBoard_hasSevenMoves` | Not yet |
| TC5 | white queen on (3,3) | the 27 squares from TC1 and TC3 together | `queen_centerOfEmptyBoard_hasTwentySevenMoves` | Not yet |

### Method under test: `destinations(...)` with pieces in the way
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC6 | white rook on (3,3), white pawn on (3,5) (distance 2) | TC1 without (3,5), (3,6), (3,7): 11 squares, and (3,4) is the last one to the right | `rook_ownPieceInTheWay_stopsOneSquareBefore` | Not yet |
| TC7 | white rook on (3,3), black pawn on (3,5) (distance 2) | TC1 without (3,6), (3,7): 12 squares, with (3,5) as a capture | `rook_enemyInTheWay_capturesAndStops` | Not yet |
| TC8 | white rook on (3,3), white pawns on (2,3), (4,3), (3,2), (3,4) (distance 1 on every side) | no moves | `rook_boxedInByOwnPieces_hasNoMoves` | Not yet |
| TC9 | black bishop on (3,3), black pawn on (1,1), white pawn on (5,5) | 10 squares: (2,2), (4,4), (5,5), (2,4), (1,5), (0,6), (4,2), (5,1), (6,0), and nothing past (1,1) or (5,5) | `bishop_blackBishop_stopsAtOwnAndCapturesWhite` | Not yet |
| TC10 | white queen on (3,3), black pawn on (2,3) (distance 1) | TC5 without (1,3) and (0,3): 25 squares, with (2,3) as a capture | `queen_enemyNextToIt_capturesAndStops` | Not yet |
