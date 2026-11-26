# Design: Piece Movement

This design covers Kris's movement issues: pawn [#19](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/19), knight [#20](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/20), bishop, rook and queen [#21](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/21), king [#22](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/22), en passant [#23](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/23) and promotion [#24](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/24). The class diagram source is [piece-movement.puml](piece-movement.puml). The requirements for each piece are in [requirements/](../requirements).

## What "moves" means here
A piece's moves are the squares it could move to by its own movement rule: empty squares it can reach, plus squares holding an opponent's piece it can capture. Two rules sit on top of this and belong to other features:
- A move that leaves your own king in check isn't legal. The check feature (#9 on the board, Cindy) filters these out, so the king's "can't move into an attacked square" rule and en passant's "only if it doesn't leave the king in check" rule are both handled there, once, for every piece.
- Castling is its own feature (#12, Cindy).

## Classes
| Class | Package | Responsibility | Issue |
|-------|---------|----------------|-------|
| `Direction` | `domain` | Immutable step of `(rowDelta, colDelta)`. Rejects `(0, 0)`, which would never leave the square. Holds the shared `ORTHOGONAL` and `DIAGONAL` direction lists. | #20, #22 |
| `Position.translate(Direction)` | `domain` | Returns the square one step away in that direction, or `Optional.empty()` if the step leaves the board. | #20 |
| `MoveRule` (interface) | `domain.move` | `destinations(Board, Position, PieceColor)` returns where a piece of that color standing on that square can move. | #20 |
| `Squares` | `domain.move` | Shared questions about a target square: `isEnemy` and `isEmptyOrEnemy`. | #20 |
| `JumpRule` | `domain.move` | A piece that moves one fixed step and ignores anything in between. `JumpRule.knight()` and `JumpRule.king()`. | #20, #22 |
| `SlideRule` | `domain.move` | A piece that slides any number of squares until it is blocked. `SlideRule.rook()`, `bishop()` and `queen()`. | #21 |
| `PawnRule` | `domain.move` | Single and double advance, and diagonal captures. | #19 |
| `PieceMoves` | `domain.move` | The public entry point. `from(Board, Position)` looks up the piece on the square and asks the rule for its type. | #19 to #22 |
| `Move` | `domain` | Immutable `(from, to)` pair, used to remember the last move. | #23 |
| `EnPassant` | `domain.move` | Works out the en passant destination from the last move, and which square's pawn gets captured. | #23 |
| `Promotion` | `domain.move` | Says when a pawn must be promoted and replaces it with the chosen piece. | #24 |

## Design decisions
1. **One rule per way of moving, not one class per piece.** Knights and kings both jump one step, and rooks, bishops and queens all slide; they only differ in their directions. So there are three rule classes, and the static factories (`JumpRule.knight()`, `SlideRule.queen()`) give each piece its directions. That keeps the sliding loop in one place, as issue #21 asks.
2. **Rules are package-private.** Only `PieceMoves`, `EnPassant` and `Promotion` are public. The rule classes trust their caller, so `PieceMoves` is the one place that rejects `null` arguments and an empty square, following *Clean Code* Ch. 7.
3. **`PieceMoves.from(Board, Position)` has the same signature as Max's `LegalMoves` interface** (issue #25). Selection can use it directly as `pieceMoves::from`, so neither branch has to wait on the other.
4. **`translate` returns `Optional`.** Rules ask for the next square and get nothing back at the edge, so no rule repeats the bounds check that `Position` already owns.
5. **`Board` isn't changed.** Moving and removing pieces belongs to the "move a piece" and "capture" features (Max). `EnPassant.capturedPawn` and `Promotion.promote` only tell the game which square to clear or use `Board.placePiece`, which already replaces a piece.
6. **Pawn rows are named constants** (`WHITE_START_ROW = 6`, and so on), taken from the coordinate rules in [game-rules.md](../requirements/game-rules.md): White moves toward row 0 and Black toward row 7.
7. Every rule returns an unmodifiable set, so callers can't change it by accident.
