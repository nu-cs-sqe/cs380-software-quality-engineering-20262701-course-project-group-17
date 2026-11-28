# BVA: `domain.Turn`

`Turn` knows whose turn it is and turns a click on a square into either a selection or a move. Its public methods are `Turn(Board, PieceSelection)`, `click(Position)` and `getCurrentColor()`.

The behavior comes from the acceptance criteria in issue [#26 feature: move a piece and switch turns](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/26). The tests use a real `Board` and a real `PieceSelection`. `LegalMoves` is an EasyMock mock, so the tests decide which squares are highlighted without needing the movement rules (#19 to #22).

## Steps 1-3: Input analysis
| Input | Kind | Valid range | Values tested |
|-------|------|-------------|---------------|
| `board` in `Turn` | `Board` reference | any board; not `null` | the standard board, `null` |
| `selection` in `Turn` | `PieceSelection` reference | a selection on the same board; not `null` | a new selection, `null` |
| `position` in `click` | `Position` reference | any on-board square; not `null` | an own piece, an opponent's piece, a highlighted square, a square that is not highlighted, the square a piece just left, a separate but equal `Position`, `null` |
| Current color | enum state | `WHITE` or `BLACK` | `WHITE` (new turn), `BLACK` (after one move), `WHITE` again (after two moves) |
| Selected square | optional state | nothing selected, or one square | nothing selected, one square selected |

`Position` already rejects off-board squares (see [Position.md](Position.md)), so `click` has no off-board cases. A null argument throws `NullPointerException` with a message that names the argument.

A click on a highlighted square moves the selected piece there, clears the selection and passes the turn. Any other click goes to `PieceSelection.select` with the current color, so a player can only select, and therefore only move, a piece of their own color (see [PieceSelection.md](PieceSelection.md)). Highlights only exist while a piece is selected, so a move always has a piece to move.

## Step 4: Test cases
All tests will be in `src/test/java/domain/TurnTest.java`.

### Methods under test: `Turn(Board board, PieceSelection selection)`, `getCurrentColor()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | new turn on the standard board | `getCurrentColor()` is `WHITE` | `constructor_newTurn_whiteMovesFirst` | ❌ |
| TC2 | construct with `null`, a new selection | `NullPointerException`: "board must not be null" | `constructor_nullBoard_throwsNullPointerException` | ❌ |
| TC3 | construct with the standard board, `null` | `NullPointerException`: "selection must not be null" | `constructor_nullSelection_throwsNullPointerException` | ❌ |

### Method under test: `click(Position position)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC4 | new turn; click the white pawn on (6,4); mock returns {(5,4), (4,4)} | (6,4) is selected; `getCurrentColor()` is still `WHITE` | `click_ownPiece_selectsWithoutSwitchingTurn` | ❌ |
| TC5 | (6,4) is selected; click the highlighted (4,4) | (4,4) holds a white pawn and (6,4) is empty | `click_highlightedSquare_movesPiece` | ❌ |
| TC6 | (6,4) is selected; click the highlighted (4,4) | `getSelected()` is `Optional.empty()` and `getHighlights()` is empty | `click_highlightedSquare_clearsSelection` | ❌ |
| TC7 | (6,4) is selected; click the highlighted (4,4) | `getCurrentColor()` is `BLACK` | `click_highlightedSquare_switchesTurnToBlack` | ❌ |
| TC8 | white moved (6,4) to (4,4); black selects (1,4) and clicks the highlighted (3,4) | (3,4) holds a black pawn and `getCurrentColor()` is `WHITE` | `click_blackMoves_switchesTurnBackToWhite` | ❌ |
| TC9 | (6,4) is selected; click (3,3), which is empty and not highlighted | the board is unchanged, (6,4) is still selected and `getCurrentColor()` is `WHITE` | `click_squareNotHighlighted_doesNotMove` | ❌ |
| TC10 | new turn; click the black pawn on (1,4) | nothing is selected, the board is unchanged and `getCurrentColor()` is `WHITE` | `click_blackPieceOnFirstTurn_doesNothing` | ❌ |
| TC11 | white moved (6,4) to (4,4); black clicks the white pawn on (4,4) | nothing is selected and `getCurrentColor()` is `BLACK` | `click_opponentPieceAfterMove_doesNothing` | ❌ |
| TC12 | white moved (6,4) to (4,4); black clicks the now empty (6,4) | nothing is selected, (6,4) is still empty and `getCurrentColor()` is `BLACK` | `click_squarePieceLeft_doesNothing` | ❌ |
| TC13 | (6,4) is selected; click a separate `new Position(4,4)` | (4,4) holds a white pawn and `getCurrentColor()` is `BLACK` | `click_separateEqualPosition_movesPiece` | ❌ |
| TC14 | new turn; `click(null)` | `NullPointerException`: "position must not be null" | `click_nullPosition_throwsNullPointerException` | ❌ |

TC9 to TC12 cover the acceptance criteria that a click on a square that isn't highlighted moves nothing and that nobody can move the opponent's pieces. TC1 and TC10 cover "white moves first".
