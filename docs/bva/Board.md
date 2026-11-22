# BVA: `domain.Board`

`Board` owns the 8×8 grid. Its public methods come from the design in [design/README.md](../design/README.md): `Board()`, `Board.createStandard()`, `getPiece(Position)`, `isEmpty(Position)` and `placePiece(Position, Piece)`.

## Steps 1-3: Input analysis
| Input | Kind | Valid range | Values tested |
|-------|------|-------------|---------------|
| `position` in `getPiece`, `isEmpty`, `placePiece` | `Position` reference | any on-board square, or `null` | the four corners (0,0), (0,7), (7,0), (7,7); an inner square (3,4) and its four neighbours; a separate but equal `Position`; `null` |
| `piece` in `placePiece` | `Piece` reference | any piece, or `null` | a white king, a black queen, `null` |
| Square state | collection (empty / occupied) | empty or holding one piece | empty, occupied, occupied and then replaced |
| Board returned by `createStandard()` | collection (64 squares) | the FIDE starting position | every row 0 to 7, including both edges of the empty middle (rows 2 and 5) and both pawn rows (1 and 6) |

Why there are no off-board cases here: `Position` rejects any row or col outside 0 to 7 in its constructor (see [Position.md](Position.md), TC3 to TC6). So `Board` can never receive an off-board square, and its boundaries are the corners of the grid. Testing (0,7) and (7,0) separately also catches a row/col swap in the grid indexing.

Null handling: following *Clean Code* Ch. 7 ("Don't pass null"), every public method rejects a `null` argument with `Objects.requireNonNull`, which throws a `NullPointerException` whose message names the argument. `getPiece` never returns `null`; it returns `Optional.empty()` for an empty square.

Placing a piece on an occupied square replaces the old piece. Captures and move legality belong to later features, not to `Board`.

## Step 4: Test cases
All tests are in `src/test/java/domain/BoardTest.java`.

### Method under test: `Board()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | a new empty board; check all 64 squares from (0,0) to (7,7) | `isEmpty` is `true` for every square | `constructor_newBoard_allSquaresAreEmpty` | ✅ |
| TC2 | a new empty board; check all 64 squares from (0,0) to (7,7) | `getPiece` returns `Optional.empty()` for every square | `constructor_newBoard_getPieceReturnsEmptyForAllSquares` | ✅ |

### Method under test: `placePiece(Position position, Piece piece)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC3 | empty board; place a white king on (0,0) (lower corner) | `getPiece((0,0))` contains a white king | `placePiece_lowerCorner_pieceIsStored` | ✅ |
| TC4 | empty board; place a white king on (7,7) (upper corner) | `getPiece((7,7))` contains a white king | `placePiece_upperCorner_pieceIsStored` | ✅ |
| TC5 | empty board; place a white king on (0,7) | `getPiece((0,7))` contains a white king, and `isEmpty((7,0))` is `true` | `placePiece_rowZeroColSeven_doesNotSwapRowAndCol` | ✅ |
| TC6 | empty board; place a white king on (7,0) | `getPiece((7,0))` contains a white king, and `isEmpty((0,7))` is `true` | `placePiece_rowSevenColZero_doesNotSwapRowAndCol` | ✅ |
| TC7 | empty board; place a white king on (3,4) | (3,4) holds the white king; neighbours (2,4), (4,4), (3,3), (3,5) are all empty | `placePiece_innerSquare_onlyThatSquareChanges` | ✅ |
| TC8 | (3,4) holds a white king; place a black queen on (3,4) | `getPiece((3,4))` contains a black queen | `placePiece_occupiedSquare_replacesPiece` | ✅ |
| TC9 | empty board; place a white king using one `new Position(3,4)`, then read with a separate `new Position(3,4)` | `getPiece` contains a white king | `placePiece_separateEqualPosition_findsPiece` | ✅ |
| TC10 | empty board; `placePiece(null, white king)` | `NullPointerException`: "position must not be null" | `placePiece_nullPosition_throwsNullPointerException` | ✅ |
| TC11 | empty board; `placePiece((3,4), null)` | `NullPointerException`: "piece must not be null" | `placePiece_nullPiece_throwsNullPointerException` | ✅ |

### Method under test: `getPiece(Position position)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC12 | (3,4) holds a white king; read (3,4) | `Optional` containing a piece with type KING and color WHITE | `getPiece_occupiedSquare_returnsPiece` | ✅ |
| TC13 | (3,4) holds a white king; read the neighbour (3,5) | `Optional.empty()` | `getPiece_emptySquare_returnsEmptyOptional` | ✅ |
| TC14 | empty board; `getPiece(null)` | `NullPointerException`: "position must not be null" | `getPiece_nullPosition_throwsNullPointerException` | ✅ |

### Method under test: `isEmpty(Position position)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC15 | (3,4) holds a white king; check the neighbour (3,5) | `true` | `isEmpty_emptySquare_returnsTrue` | ✅ |
| TC16 | (3,4) holds a white king; check (3,4) | `false` | `isEmpty_occupiedSquare_returnsFalse` | ✅ |
| TC17 | empty board; `isEmpty(null)` | `NullPointerException`: "position must not be null" | `isEmpty_nullPosition_throwsNullPointerException` | ✅ |

### Method under test: `createStandard()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC18 | standard board; read row 0, cols 0 to 7 | black Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook (black queen on (0,3), black king on (0,4)) | `createStandard_rowZero_hasBlackBackRank` | ✅ |
| TC19 | standard board; read row 1, cols 0 to 7 | 8 black pawns | `createStandard_rowOne_hasBlackPawns` | ✅ |
| TC20 | standard board; read rows 2 to 5 (edges: row 2 and row 5), cols 0 to 7 | all 32 squares are empty | `createStandard_rowsTwoToFive_areEmpty` | ✅ |
| TC21 | standard board; read row 6, cols 0 to 7 | 8 white pawns | `createStandard_rowSix_hasWhitePawns` | ✅ |
| TC22 | standard board; read row 7, cols 0 to 7 | white Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook (white queen on (7,3), white king on (7,4)) | `createStandard_rowSeven_hasWhiteBackRank` | ✅ |
| TC23 | standard board; count pieces by color and type over all 64 squares | 32 pieces in total; each color has 8 pawns, 2 rooks, 2 knights, 2 bishops, 1 queen, 1 king | `createStandard_allSquares_hasSixteenPiecesPerColor` | Not yet |
| TC24 | call `createStandard()` twice; place a white king on (4,4) of the first board | (4,4) on the second board is still empty | `createStandard_calledTwice_returnsIndependentBoards` | Not yet |
