# En Passant

Issue: [#23 feature: capture en passant](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/23)

## User Story

As a player, I want to capture a pawn en passant, so that pawn play follows the full rules.

## Acceptance Criteria

- If an opponent's pawn has just moved two squares and landed right beside my pawn, my pawn can capture it by moving diagonally onto the square the opponent's pawn skipped.
- The captured pawn is removed from its own square, not from the square my pawn lands on.
- En passant is only available on the very next move. After any other move it is gone.
- It's legal only if it doesn't leave my king in check. The check feature enforces this for every move.

## Use Case: Capture En Passant

### Actor

The player whose turn it is.

### Preconditions

- The opponent's last move was a pawn moving two squares forward from its starting row.
- One of the player's pawns stands on the same row as that pawn, in the next column over.

### Main Flow

1. The player selects their pawn.
2. The game asks for the en passant destination, giving it the board, the pawn's square, and the last move.
3. The system returns the square diagonally forward, in the column of the opponent's pawn.
4. The player moves their pawn there.
5. The game asks which square holds the captured pawn, and the system answers with the square in the moving pawn's starting row and the destination's column.
6. The game removes the opponent's pawn from that square.

### Alternate Flow 3.a: En Passant Isn't Available

1. The last move wasn't an opponent's pawn moving two squares, or that pawn isn't right beside the selected pawn.
2. The system returns no destination, and the pawn only has its normal moves.

### Postconditions

- Asking for the destination never changes the board. Steps 4 and 6 are done by the game (the "move a piece" and "capture" features).

## Out of scope

Moving the pawn and removing the captured pawn from the board (the "move a piece" and "capture" features, Max), and the check rule (the check feature).
