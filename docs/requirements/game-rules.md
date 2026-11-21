# Chess Rules

The authoritative rule set is the FIDE Laws of Chess:
https://handbook.fide.com/chapter/E012023

We use the summary below as the reference for our BVA. Anything marked *(later)* belongs to a future feature.

## Board and coordinates
- 8 × 8 board. Squares are addressed in code as `Position(row, col)`, both in `0..7`.
- `row 0` is Black's back rank (rank 8); `row 7` is White's back rank (rank 1).
- `col 0` is file **a**; `col 7` is file **h**.
- The square at `row 7, col 7` (h1) is light, which is where the saying "light on the right" comes from.

## Starting position
| Row | Rank | Contents |
|-----|------|----------|
| 0 | 8 | Black: Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook |
| 1 | 7 | 8 Black Pawns |
| 2–5 | 6–3 | empty |
| 6 | 2 | 8 White Pawns |
| 7 | 1 | White: Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook |

- Queens sit on their own color: white queen on d1 (`7,3`), black queen on d8 (`0,3`).
- Kings are on the e-file: white king e1 (`7,4`), black king e8 (`0,4`).
- 16 pieces per side, 32 total.

## Turn order
- Two players. Player 1 plays White, Player 2 plays Black.
- White moves first.

## Later features *(later)*
Piece movement, capture, check, checkmate, stalemate and other draws, castling, en passant, pawn promotion, and our team's invented feature.
