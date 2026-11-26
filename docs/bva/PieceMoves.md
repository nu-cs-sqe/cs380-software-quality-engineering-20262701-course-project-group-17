# BVA: `domain.move.PieceMoves`

`PieceMoves` is the public entry point for movement. `from(Board, Position)` looks up the piece on the square and asks the rule for its type. It ties together issues [#19](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/19) to [#22](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/22), and its signature matches Max's `LegalMoves` interface from issue #25, so selection can use `pieceMoves::from` (see [design/piece-movement.md](../design/piece-movement.md), decision 3).

The rules themselves are tested in [JumpRule.md](JumpRule.md), [SlideRule.md](SlideRule.md) and [PawnRule.md](PawnRule.md). Here the question is only whether each type reaches the right rule, plus the argument checks that the rules leave to this class.

## Steps 1-3: Input analysis
| Input | Kind | Valid range | Values tested |
|-------|------|-------------|---------------|
| `board` | `Board` reference | any board; not `null` | an empty board with one piece placed, the standard starting board, `null` |
| `position` | `Position` reference | a square that holds a piece; not `null` | each of the 6 piece types on (3,3), an empty square, `null` |
| Piece type on `position` | enum, 6 values | every `PieceType` | all 6 |

Each type is placed alone on (3,3) of an empty board, where its number of moves is different from every other type except the knight and the king (8 each). Those two are told apart because the king's moves include (2,3) and the knight's don't. A wrong mapping changes the result in every case.

## Step 4: Test cases
Unit tests are in `src/test/java/domain/move/PieceMovesTest.java`. The integration tests, which run every rule against the real starting position, are in `src/test/java/domain/move/StandardBoardMovesIntegrationTest.java`.

### Method under test: `from(Board board, Position position)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | white king alone on (3,3) | 8 moves, including (2,3) | `from_eachPieceTypeAlone_usesItsOwnRule` | Not yet |
| TC2 | white queen alone on (3,3) | 27 moves | `from_eachPieceTypeAlone_usesItsOwnRule` | Not yet |
| TC3 | white rook alone on (3,3) | 14 moves | `from_eachPieceTypeAlone_usesItsOwnRule` | Not yet |
| TC4 | white bishop alone on (3,3) | 13 moves | `from_eachPieceTypeAlone_usesItsOwnRule` | Not yet |
| TC5 | white knight alone on (3,3) | 8 moves, not including (2,3) | `from_eachPieceTypeAlone_usesItsOwnRule` | Not yet |
| TC6 | white pawn alone on (3,3) | 1 move, (2,3) | `from_eachPieceTypeAlone_usesItsOwnRule` | Not yet |
| TC7 | an empty square | `IllegalArgumentException`: "position must hold a piece" | `from_emptySquare_throwsIllegalArgumentException` | Not yet |
| TC8 | `board` is `null` | `NullPointerException`: "board must not be null" | `from_nullBoard_throwsNullPointerException` | Not yet |
| TC9 | `position` is `null` | `NullPointerException`: "position must not be null" | `from_nullPosition_throwsNullPointerException` | Not yet |
| TC10 | the returned set | adding to it throws `UnsupportedOperationException` | `from_result_cannotBeChanged` | Not yet |

### Integration: every rule on the standard starting board
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC11 | every white piece on the starting board | 20 moves in total (16 pawn moves and 4 knight moves) | `startingPosition_eachSide_hasTwentyMoves` | Not yet |
| TC12 | every black piece on the starting board | 20 moves in total | `startingPosition_eachSide_hasTwentyMoves` | Not yet |
| TC13 | the white knight on (7,1) | (5,0), (5,2) | `startingPosition_whiteKnight_jumpsOverThePawns` | Not yet |
| TC14 | the white rook (7,0), bishop (7,2), queen (7,3) and king (7,4) | no moves; their own pieces box them in | `startingPosition_backRankExceptKnights_hasNoMoves` | Not yet |
