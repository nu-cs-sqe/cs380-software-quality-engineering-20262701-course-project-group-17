# BVA: `domain.Position`

## Steps 1-3: Input analysis
| Input | Kind | Valid range | Values tested |
|-------|------|-------------|---------------|
| `row` | interval | 0 to 7 | -1, 0, 3, 7, 8 |
| `col` | interval | 0 to 7 | -1, 0, 4, 7, 8 |
| `other` in `equals` | object reference | any object or null | the same instance, a separate instance with the same values, different row, different col, null, a different type |

A position off the board is rejected with an `IllegalArgumentException`. Its message names the field and the bad value.

## Step 4: Test cases
All tests are in `src/test/java/domain/PositionTest.java`.

### Method under test: `Position(int row, int col)`, `getRow()`, `getCol()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | row 0, col 0 (lower boundary) | `getRow()` is 0 and `getCol()` is 0 | `constructor_onBoardBoundaries_storesRowAndCol` | Not yet |
| TC2 | row 7, col 7 (upper boundary) | `getRow()` is 7 and `getCol()` is 7 | `constructor_onBoardBoundaries_storesRowAndCol` | Not yet |
| TC15 | row 3, col 4 (row and col differ) | `getRow()` is 3 and `getCol()` is 4 | `constructor_differentRowAndCol_keepsThemApart` | Not yet |
| TC3 | row -1, col 0 | `IllegalArgumentException`: "row must be between 0 and 7, but was -1" | `constructor_rowOffBoard_throwsIllegalArgumentException` | Not yet |
| TC4 | row 8, col 0 | `IllegalArgumentException`: "row must be between 0 and 7, but was 8" | `constructor_rowOffBoard_throwsIllegalArgumentException` | Not yet |
| TC5 | row 0, col -1 | `IllegalArgumentException`: "col must be between 0 and 7, but was -1" | `constructor_colOffBoard_throwsIllegalArgumentException` | Not yet |
| TC6 | row 0, col 8 | `IllegalArgumentException`: "col must be between 0 and 7, but was 8" | `constructor_colOffBoard_throwsIllegalArgumentException` | Not yet |

### Method under test: `equals(Object other)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC14 | a Position(3,4) compared with itself | `true` | `equals_sameInstance_returnsTrue` | Not yet |
| TC7 | two separate Position(3,4) instances | `true` | `equals_sameRowAndCol_returnsTrue` | Not yet |
| TC8 | (3,4) compared with (2,4) | `false` | `equals_differentRow_returnsFalse` | Not yet |
| TC9 | (3,4) compared with (3,5) | `false` | `equals_differentCol_returnsFalse` | Not yet |
| TC10 | (3,4) compared with `null` | `false` | `equals_null_returnsFalse` | Not yet |
| TC11 | (3,4) compared with the string "3,4" | `false` | `equals_differentType_returnsFalse` | Not yet |

### Method under test: `hashCode()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC12 | two separate Position(3,4) instances | the same hash code | `hashCode_equalPositions_returnsSameValue` | Not yet |
| TC13 | (0,0), (0,1), (1,0), (7,7) | 0, 1, 8, 63 (the square index, `row * 8 + col`) | `hashCode_anySquare_returnsSquareIndex` | Not yet |

TC14 and TC15 were added during review, so they sit next to the cases they relate to rather than in numeric order. TC15 catches an implementation that mixes up `row` and `col`, which TC1 and TC2 can't detect because both of their coordinates are equal. TC14 checks that `equals` is reflexive. `equals` deliberately has no `this == other` shortcut: the field comparison already returns `true` for the same instance, and PIT would report a removed shortcut as a surviving equivalent mutant.
