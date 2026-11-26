# BVA: `domain.move.JumpRule`

A `JumpRule` moves a piece by one fixed step in each of its directions and ignores anything in between. `JumpRule.knight()` covers issue [#20](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/20). The requirements are in [knight-movement.md](../requirements/knight-movement.md).

The method under test is `destinations(Board board, Position from, PieceColor color)`. It's package-private and trusts its caller (see [design/piece-movement.md](../design/piece-movement.md), decision 2), so there are no `null` cases here. `PieceMoves` tests those.

## Steps 1-3: Input analysis
| Input | Kind | Values tested |
|-------|------|---------------|
| `from` row and col | interval 0 to 7 | each corner (0,0) and (7,7), one square in from the corner (1,1), one edge square (0,3), and the center (3,3) |
| What stands on each destination | empty, own piece, enemy piece | all empty, one own piece, one enemy piece |
| What stands between `from` and a destination | empty or full | an empty board, and a knight completely surrounded by its own pieces |
| `color` | `PieceColor` | `WHITE`, `BLACK` |

The number of moves is the output that has boundaries: a knight in a corner has the fewest moves (2), and a knight in the middle has the most (8). (1,1) and (0,3) sit in between, and they catch a check that removes the wrong jumps near the edge.

## Step 4: Test cases
All tests are in `src/test/java/domain/move/JumpRuleTest.java`.

### Method under test: `JumpRule.knight().destinations(...)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | white knight on (3,3), empty board | the 8 squares (1,2), (1,4), (2,1), (2,5), (4,1), (4,5), (5,2), (5,4) | `knight_center_hasEightMoves` | Not yet |
| TC2 | white knight on (0,0), empty board | (1,2), (2,1) | `knight_corner_hasTwoMoves` | Not yet |
| TC3 | white knight on (7,7), empty board | (5,6), (6,5) | `knight_corner_hasTwoMoves` | Not yet |
| TC4 | white knight on (1,1), empty board | (0,3), (2,3), (3,0), (3,2) | `knight_nextToCorner_hasFourMoves` | Not yet |
| TC5 | white knight on (0,3), empty board | (1,1), (1,5), (2,2), (2,4) | `knight_edge_hasFourMoves` | Not yet |
| TC6 | white knight on (3,3), white pawn on (1,2) | the 7 other squares from TC1; (1,2) is missing | `knight_ownPieceOnTarget_cannotLandThere` | Not yet |
| TC7 | white knight on (3,3), black pawn on (1,2) | all 8 squares from TC1, including (1,2) | `knight_enemyOnTarget_canCapture` | Not yet |
| TC8 | black knight on (3,3), white pawn on (1,2) and black pawn on (5,4) | the 8 squares from TC1 without (5,4) | `knight_blackKnight_capturesWhiteAndAvoidsBlack` | Not yet |
| TC9 | white knight on (3,3) with white pawns on all 8 squares around it | all 8 squares from TC1 | `knight_surroundedByOwnPieces_jumpsOver` | Not yet |
