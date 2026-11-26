# Pawn Movement

Issue: [#19 feature: move a pawn](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/19)

## User Story

As a player, I want my pawns to move the way chess pawns do, so that pawn play follows the rules.

## Acceptance Criteria

- A pawn moves one square forward onto an empty square. Forward means toward row 0 for White and toward row 7 for Black.
- From its starting row (row 6 for White, row 1 for Black), a pawn can move two squares forward if both squares are empty.
- A pawn captures one square diagonally forward, and only onto an opponent's piece.
- A pawn can't move backward or sideways, can't move forward onto an occupied square, and can't capture straight ahead.
- No move takes a pawn off the board. This includes a pawn on col 0 or col 7 capturing diagonally.
- En passant and promotion are separate issues.

## Use Case: List a Pawn's Moves

### Actor

The game, asking on behalf of the player who selected a pawn.

### Preconditions

- A pawn of the current player's color stands on a square of the board.

### Main Flow

1. The game asks for the pawn's moves from its square.
2. If the square one step forward is empty, the system adds it.
3. If step 2 added a square and the pawn is on its starting row, the system also adds the square two steps forward, as long as it's empty too.
4. For each of the two squares diagonally forward, the system adds it if it holds an opponent's piece.
5. The system returns every square it added.

### Alternate Flow 2.a: The Square Ahead Is Taken

1. The square one step forward holds a piece of either color.
2. The pawn has no forward moves, not even the two-step move. The use case continues at step 4.

### Postconditions

- The board is unchanged.

## Out of scope

En passant and promotion (their own issues), and the check rule (the check feature).
