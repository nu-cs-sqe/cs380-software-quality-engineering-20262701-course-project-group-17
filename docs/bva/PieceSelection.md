# BVA: `domain.PieceSelection`

`PieceSelection` stores which piece the current player has selected and the squares that piece can move to. Its public methods are `PieceSelection(Board, LegalMoves)`, `select(Position, PieceColor)`, `getSelected()` and `getHighlights()`.

The behavior comes from the acceptance criteria in issue [#25 feature: select a piece](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/25). `LegalMoves` is an interface with no logic, so it doesn't get its own BVA file. In the tests it is an EasyMock mock.

## Steps 1-3: Input analysis
| Input | Kind | Valid range | Values tested |
|-------|------|-------------|---------------|
| `board` in `PieceSelection` | `Board` reference | any board; not `null` | the standard board, an empty board with two pieces placed, `null` |
| `legalMoves` in `PieceSelection` | `LegalMoves` reference | any implementation; not `null` | an EasyMock mock, `null` |
| `position` in `select` | `Position` reference | any on-board square; not `null` | an empty square, an own piece, an opponent's piece, the selected square, the corner (7,0), a separate but equal `Position`, `null` |
| `turn` in `select` | `PieceColor` reference | `WHITE` or `BLACK`; not `null` | `WHITE`, `BLACK`, `null` |
| Selected square | optional state | nothing selected, or one square | nothing selected, one square selected |
| Legal moves of the selected piece | collection | 0 to many squares | 0, 1, 2 |

`Position` already rejects off-board squares (see [Position.md](Position.md)), so `select` has no off-board cases. A null argument throws `NullPointerException` with a message that names the argument.

With nothing selected, clicking an empty square or an opponent's piece does nothing. With a piece selected, the same clicks also do nothing, so the piece stays selected (as in #26).

## Step 4: Test cases
All tests will be in `src/test/java/domain/PieceSelectionTest.java`.

### Methods under test: `PieceSelection(Board board, LegalMoves legalMoves)`, `getSelected()`, `getHighlights()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | new selection on the standard board | `getSelected()` is `Optional.empty()` and `getHighlights()` is empty | `constructor_newSelection_nothingSelected` | :white_check_mark: |
| TC2 | construct with `null`, mock | `NullPointerException`: "board must not be null" | `constructor_nullBoard_throwsNullPointerException` | :white_check_mark: |
| TC3 | construct with the standard board, `null` | `NullPointerException`: "legalMoves must not be null" | `constructor_nullLegalMoves_throwsNullPointerException` | :white_check_mark: |

### Method under test: `select(Position position, PieceColor turn)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC4 | standard board, nothing selected; `WHITE` selects the white pawn on (6,4); mock returns {(5,4), (4,4)} | `getSelected()` contains (6,4) and `getHighlights()` is {(5,4), (4,4)} | `select_ownPiece_selectsPieceAndHighlightsMoves` | :white_check_mark: |
| TC5 | standard board, nothing selected; `BLACK` selects the black knight on (0,6); mock returns {(2,5), (2,7)} | `getSelected()` contains (0,6) and `getHighlights()` is {(2,5), (2,7)} | `select_blackPieceOnBlackTurn_selectsPiece` | :white_check_mark: |
| TC6 | standard board, nothing selected; `WHITE` selects the white rook on (7,0); mock returns {} (0 moves) | `getSelected()` contains (7,0) and `getHighlights()` is empty | `select_pieceWithNoMoves_selectsWithNoHighlights` | :white_check_mark: |
| TC7 | empty board with a white pawn on (6,4) and a black pawn on (4,4); `WHITE` selects (6,4); mock returns {(5,4)} (1 move) | `getSelected()` contains (6,4) and `getHighlights()` is {(5,4)} | `select_pieceWithOneMove_highlightsOneSquare` | :white_check_mark: |
| TC8 | standard board, nothing selected; `WHITE` selects the empty square (4,4) | `getSelected()` is `Optional.empty()`; the mock is never called | `select_emptySquare_doesNothing` | :white_check_mark: |
| TC9 | standard board, nothing selected; `WHITE` selects the black pawn on (1,4) | `getSelected()` is `Optional.empty()`; the mock is never called | `select_opponentPiece_doesNothing` | :white_check_mark: |
| TC10 | standard board, nothing selected; `BLACK` selects the white pawn on (6,4) | `getSelected()` is `Optional.empty()`; the mock is never called | `select_whitePieceOnBlackTurn_doesNothing` | :white_check_mark: |
| TC11 | standard board; select with one `new Position(6,4)`, read with a separate `new Position(6,4)` | `getSelected()` contains (6,4) | `select_separateEqualPosition_selectsPiece` | :white_check_mark: |
| TC12 | (6,4) is selected; `WHITE` selects (6,4) again | `getSelected()` is `Optional.empty()` and `getHighlights()` is empty | `select_selectedPieceAgain_deselects` | :x: |
| TC13 | (6,4) is selected; `WHITE` selects the white knight on (7,6); mock returns {(5,5), (5,7)} | `getSelected()` contains (7,6) and `getHighlights()` is {(5,5), (5,7)} | `select_otherOwnPiece_switchesSelection` | :x: |
| TC14 | (6,4) is selected; `WHITE` selects the empty square (3,3) | `getSelected()` still contains (6,4) and `getHighlights()` is still {(5,4), (4,4)} | `select_emptySquareWhileSelected_keepsSelection` | :x: |
| TC15 | (6,4) is selected; `WHITE` selects the black pawn on (1,4) | `getSelected()` still contains (6,4) and `getHighlights()` is still {(5,4), (4,4)} | `select_opponentPieceWhileSelected_keepsSelection` | :x: |
| TC16 | (6,4) was selected and then deselected; `WHITE` selects (6,4) again | `getSelected()` contains (6,4) and `getHighlights()` is {(5,4), (4,4)} | `select_afterDeselect_selectsAgain` | :x: |
| TC17 | (6,4) is selected, then (7,6); `WHITE` selects (7,6) again | `getSelected()` is `Optional.empty()` and `getHighlights()` is empty | `select_switchedPieceAgain_deselects` | :x: |
| TC18 | standard board; select (6,4), then (7,6), then (7,6) again | all 64 squares match a fresh `Board.createStandard()` | `select_anySequence_boardIsUnchanged` | :x: |
| TC19 | construct with the standard board, mock; `select(null, WHITE)` | `NullPointerException`: "position must not be null" | `select_nullPosition_throwsNullPointerException` | :x: |
| TC20 | construct with the standard board, mock; `select((6,4), null)` | `NullPointerException`: "turn must not be null" | `select_nullTurn_throwsNullPointerException` | :x: |

### Method under test: `getHighlights()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC21 | (6,4) is selected; add (3,3) to the returned set | `UnsupportedOperationException` | `getHighlights_modifyReturnedSet_throwsUnsupportedOperationException` | :x: |
| TC22 | (6,4) is selected; add (3,3) to the set the mock returned | `getHighlights()` is still {(5,4), (4,4)} | `getHighlights_mockSetChangedLater_staysUnchanged` | :x: |

TC8 to TC10, TC14 and TC15 use a strict mock with `verify`, so they fail if `select` asks for legal moves when it shouldn't. TC21 and TC22 check that the highlights can't be changed from outside, so the class stores a copy of the legal moves.
