# BVA: `domain.piece.Piece`

`PieceType` and `PieceColor` are enums with no logic, so they don't get their own BVA files.

## Steps 1-3: Input analysis
| Input | Kind | Values tested |
|-------|------|---------------|
| `type` | enum reference | a valid `PieceType`, or `null` |
| `color` | enum reference | a valid `PieceColor`, or `null` |
| `other` in `isSameColorAs` | Piece | same color, different color |
| `other` in `equals` | object reference | same type and color, different type, different color, null, a different class |

## Step 4: Test cases
All tests are in `src/test/java/domain/piece/PieceTest.java`.

### Method under test: `Piece(PieceType type, PieceColor color)`, `getType()`, `getColor()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | KING, WHITE | `getType()` is KING and `getColor()` is WHITE | `constructor_validTypeAndColor_storesBoth` | Not yet |
| TC2 | `null`, WHITE | `NullPointerException`: "type must not be null" | `constructor_nullType_throwsNullPointerException` | Not yet |
| TC3 | KING, `null` | `NullPointerException`: "color must not be null" | `constructor_nullColor_throwsNullPointerException` | Not yet |

### Method under test: `isSameColorAs(Piece other)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC4 | white king compared with a white pawn | `true` | `isSameColorAs_sameColor_returnsTrue` | Not yet |
| TC5 | white king compared with a black king | `false` | `isSameColorAs_differentColor_returnsFalse` | Not yet |

### Method under test: `equals(Object other)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC6 | black rook compared with a black rook | `true` | `equals_sameTypeAndColor_returnsTrue` | Not yet |
| TC7 | black rook compared with a black bishop | `false` | `equals_differentType_returnsFalse` | Not yet |
| TC8 | black rook compared with a white rook | `false` | `equals_differentColor_returnsFalse` | Not yet |
| TC9 | black rook compared with `null` | `false` | `equals_null_returnsFalse` | Not yet |
| TC10 | black rook compared with `PieceType.ROOK` | `false` | `equals_differentClass_returnsFalse` | Not yet |

### Method under test: `hashCode()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC11 | two separate white queens | the same hash code | `hashCode_equalPieces_returnsSameValue` | Not yet |
| TC12 | white queen compared with a black queen | different hash codes | `hashCode_differentColor_returnsDifferentValue` | Not yet |
