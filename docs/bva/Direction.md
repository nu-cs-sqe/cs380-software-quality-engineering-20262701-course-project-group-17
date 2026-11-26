# BVA: `domain.Direction`

A `Direction` is one step on the board, `(rowDelta, colDelta)`. Movement rules use it with `Position.translate` (see [Position.md](Position.md)). The design is in [design/piece-movement.md](../design/piece-movement.md).

## Steps 1-3: Input analysis
| Input | Kind | Valid range | Values tested |
|-------|------|-------------|---------------|
| `rowDelta` | interval | any `int`, as long as the step isn't `(0, 0)` | -2, 0, 1 |
| `colDelta` | interval | any `int`, as long as the step isn't `(0, 0)` | -1, 0, 2 |

The only invalid step is `(0, 0)`, because it never leaves the square. A sliding piece would loop forever on it. Each delta on its own may be 0, so `(0, 2)` and `(1, 0)` sit right next to the invalid case on each axis. There's no upper limit: a step that's too long simply lands off the board, and `translate` handles that.

## Step 4: Test cases
All tests are in `src/test/java/domain/DirectionTest.java`.

### Method under test: `Direction(int rowDelta, int colDelta)`, `getRowDelta()`, `getColDelta()`
| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC1 | rowDelta -2, colDelta 1 (row and col differ) | `getRowDelta()` is -2 and `getColDelta()` is 1 | `constructor_knightJump_keepsRowAndColDeltas` | :white_check_mark: |
| TC2 | rowDelta 0, colDelta 2 (row delta at 0) | `getRowDelta()` is 0 and `getColDelta()` is 2 | `constructor_oneDeltaZero_isAllowed` | :white_check_mark: |
| TC3 | rowDelta 1, colDelta 0 (col delta at 0) | `getRowDelta()` is 1 and `getColDelta()` is 0 | `constructor_oneDeltaZero_isAllowed` | :white_check_mark: |
| TC4 | rowDelta 0, colDelta 0 | `IllegalArgumentException`: "a direction must move at least one square" | `constructor_zeroStep_throwsIllegalArgumentException` | :white_check_mark: |

### Constants: `ORTHOGONAL`, `DIAGONAL`, `ALL`
Added for king movement (issue [#22](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/22)), and reused by the sliding pieces. `Direction` has no `equals`, so each list is checked by translating the center square (3,3) by every direction in it and comparing the squares it lands on.

| ID | State / Input | Expected output | Test method | Implemented? |
|----|---------------|-----------------|-------------|--------------|
| TC5 | (3,3) translated by every direction in `ORTHOGONAL` | (2,3), (4,3), (3,2), (3,4) | `orthogonal_fromCenter_reachesTheFourStraightNeighbours` | :white_check_mark: |
| TC6 | (3,3) translated by every direction in `DIAGONAL` | (2,2), (2,4), (4,2), (4,4) | `diagonal_fromCenter_reachesTheFourDiagonalNeighbours` | :white_check_mark: |
| TC7 | (3,3) translated by every direction in `ALL` | all 8 neighbours of (3,3), and no square twice (the list has 8 entries) | `all_fromCenter_reachesAllEightNeighbours` | :white_check_mark: |
