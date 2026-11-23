# BVA: `domain.piece.Piece`

`PieceType` and `PieceColor` are enums with no logic, so they don't get their own BVA files.

## Steps 1-3: Input analysis
| Input | Kind | Values tested |
|-------|------|---------------|
| `type` | enum reference | a valid `PieceType`, or `null` |
| `color` | enum reference | a valid `PieceColor`, or `null` |
| `other` in `isSameColorAs` | Piece reference | same color, different color, `null` |
| `other` in `equals` | object reference | a separate instance with the same type and color, different type, different color, null, a different class |
| `type`, `color` in `hashCode` | enum order | first type and first color, first type and last color, second type, last type and last color |

## Step 4: Test cases
All tests are in `src/test/java/domain/piece/PieceTest.java`.

### Method under test: `Piece(PieceType type, PieceColor color)`, `getType()`, `getColor()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | KING, WHITE | `getType()` is KING and `getColor()` is WHITE | `constructor_validTypeAndColor_storesBoth` | :white_check_mark: |
| TC2 | `null`, WHITE | `NullPointerException`: "type must not be null" | `constructor_nullType_throwsNullPointerException` | :white_check_mark: |
| TC3 | KING, `null` | `NullPointerException`: "color must not be null" | `constructor_nullColor_throwsNullPointerException` | :white_check_mark: |

### Method under test: `isSameColorAs(Piece other)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC4 | white king compared with a white pawn | `true` | `isSameColorAs_sameColor_returnsTrue` | :white_check_mark: |
| TC5 | white king compared with a black king | `false` | `isSameColorAs_differentColor_returnsFalse` | :white_check_mark: |
| TC13 | white king compared with `null` | `NullPointerException`: "other must not be null" | `isSameColorAs_null_throwsNullPointerException` | :white_check_mark: |

### Method under test: `equals(Object other)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC6 | two separate `Piece` instances with the same type and color (black rook) | `true` | `equals_sameTypeAndColor_returnsTrue` | :white_check_mark: |
| TC7 | black rook compared with a black bishop | `false` | `equals_differentType_returnsFalse` | :white_check_mark: |
| TC8 | black rook compared with a white rook | `false` | `equals_differentColor_returnsFalse` | :white_check_mark: |
| TC9 | black rook compared with `null` | `false` | `equals_null_returnsFalse` | :white_check_mark: |
| TC10 | black rook compared with `PieceType.ROOK` | `false` | `equals_differentClass_returnsFalse` | :white_check_mark: |

### Method under test: `hashCode()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC11 | two separate white queens | the same hash code | `hashCode_equalPieces_returnsSameValue` | :white_check_mark: |
| TC12 | (KING, WHITE), (KING, BLACK), (QUEEN, WHITE), (PAWN, BLACK) | 0, 1, 2, 11 (the piece index, `type.ordinal() * 2 + color.ordinal()`) | `hashCode_anyPiece_returnsPieceIndex` | :white_check_mark: |

TC13 was added during review, so it sits with the other `isSameColorAs` cases rather than in numeric order. TC12 checks exact values instead of only "different hash codes". Enum hash codes can change from run to run, so a test built on them could fail at random. A fixed formula based on enum order gives each of the 12 pieces its own index, from 0 to 11, the same way `Position` numbers its 64 squares.
