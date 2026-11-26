# Bishop, Rook, and Queen Movement

Issue: [#21 feature: move a bishop, rook, or queen](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/21)

## User Story

As a player, I want my bishops, rooks, and queen to slide along their lines, so that long-range pieces follow the rules.

## Acceptance Criteria

- A rook moves any number of squares along a row or column.
- A bishop moves any number of squares diagonally.
- A queen moves like a rook or a bishop.
- A sliding piece stops at the first occupied square. If that square holds an opponent's piece, the piece can capture there. If it holds one of its own pieces, the piece stops one square before it.
- Moves never go past the edge of the board. On an empty board, a rook in a corner has 14 moves, and a queen in the center has 27.
- All three pieces share one sliding-move helper, so the logic lives in one place.

## Use Case: List a Sliding Piece's Moves

### Actor

The game, asking on behalf of the player who selected a bishop, rook, or queen.

### Preconditions

- A bishop, rook, or queen of the current player's color stands on a square of the board.

### Main Flow

1. The game asks for the piece's moves from its square.
2. For each of the piece's directions (4 straight ones for a rook, 4 diagonal ones for a bishop, all 8 for a queen), the system steps one square at a time.
3. Each empty square is added, and the system keeps going.
4. At the first occupied square, the system stops. It adds that square only if it holds an opponent's piece.
5. At the edge of the board, the system stops.
6. The system returns every square it added.

### Postconditions

- The board is unchanged.

## Out of scope

Whether a move leaves the player's own king in check is decided by the check feature. Castling, which also moves a rook, is its own issue.
