# BVA: `domain.move.Promotion`

`Promotion` covers issue [#24](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/24). The requirements are in [promotion.md](../requirements/promotion.md). Its public members are the `CHOICES` list, `isDue(Board, Position)` and `promote(Board, Position, PieceType)`.

## Steps 1-3: Input analysis
| Input | Kind | Values tested |
|-------|------|---------------|
| Row of the pawn | interval 0 to 7 | its last row (0 for White, 7 for Black), the row just before it (1 / 6), and the other side's last row (7 / 0) |
| Piece on `position` | empty, pawn, other piece | a white pawn, a black pawn, a white queen, nothing |
| `choice` | enum, 6 values | all 6 `PieceType` values: the 4 allowed ones and the 2 that aren't (KING, PAWN) |
| `board`, `position`, `choice` | references | `null` for each |

The last row is the boundary: one row before it no promotion is due. The other side's last row is a trap for code that checks "row 0 or row 7" without looking at the color. `choice` has only 6 values, so all of them are tested.

## Step 4: Test cases
All tests are in `src/test/java/domain/move/PromotionTest.java`.

### Constant: `CHOICES`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | `CHOICES` | QUEEN, ROOK, BISHOP, KNIGHT, in that order | `choices_inDialogOrder_areQueenRookBishopKnight` | :white_check_mark: |

### Method under test: `isDue(Board board, Position position)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC2 | white pawn on (0,3) | `true` | `isDue_pawnOnItsLastRow_returnsTrue` | :white_check_mark: |
| TC3 | black pawn on (7,3) | `true` | `isDue_pawnOnItsLastRow_returnsTrue` | :white_check_mark: |
| TC4 | white pawn on (1,3) | `false` | `isDue_pawnOneRowShort_returnsFalse` | :white_check_mark: |
| TC5 | black pawn on (6,3) | `false` | `isDue_pawnOneRowShort_returnsFalse` | :white_check_mark: |
| TC6 | white pawn on (7,3) (Black's last row) | `false` | `isDue_pawnOnOtherSidesLastRow_returnsFalse` | :white_check_mark: |
| TC7 | black pawn on (0,3) (White's last row) | `false` | `isDue_pawnOnOtherSidesLastRow_returnsFalse` | :white_check_mark: |
| TC8 | white queen on (0,3) | `false` | `isDue_notAPawn_returnsFalse` | Not yet |
| TC9 | nothing on (0,3) | `false` | `isDue_emptySquare_returnsFalse` | :white_check_mark: |
| TC10 | `board` or `position` is `null` | `NullPointerException`: "board must not be null" / "position must not be null" | `isDue_nullArgument_throwsNullPointerException` | Not yet |

### Method under test: `promote(Board board, Position position, PieceType choice)`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC11 | white pawn on (0,3); each of QUEEN, ROOK, BISHOP, KNIGHT | (0,3) holds a white piece of the chosen type | `promote_allowedChoice_replacesThePawn` | Not yet |
| TC12 | black pawn on (7,4); KNIGHT | (7,4) holds a black knight | `promote_blackPawn_keepsItsColor` | Not yet |
| TC13 | white pawn on (0,3); KING or PAWN | `IllegalArgumentException`: "a pawn can only be promoted to a queen, rook, bishop, or knight", and (0,3) still holds the white pawn | `promote_choiceNotAllowed_throwsAndLeavesThePawn` | Not yet |
| TC14 | white pawn on (1,3) (not due); QUEEN | `IllegalArgumentException`: "position must hold a pawn on its last row", and (1,3) still holds the white pawn | `promote_noPromotionDue_throwsAndLeavesTheBoard` | Not yet |
| TC15 | `board`, `position`, or `choice` is `null` | `NullPointerException` naming the argument | `promote_nullArgument_throwsNullPointerException` | Not yet |
