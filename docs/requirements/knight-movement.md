# Knight Movement

Issue: [#20 feature: move a knight](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/20)

## User Story

As a player, I want my knights to move in an L shape and jump over pieces, so that knight play follows the rules.

## Acceptance Criteria

- A knight moves two squares in one direction and one square at a right angle, which gives up to 8 destinations.
- A knight jumps over any pieces in between.
- A knight can land on an empty square or capture an opponent's piece, but it can't land on one of its own pieces.
- Destinations off the board are not offered. A knight in a corner has 2 moves, and a knight in the center of an empty board has 8.

## Use Case: List a Knight's Moves

### Actor

The game, asking on behalf of the player who selected a knight.

### Preconditions

- A knight of the current player's color stands on a square of the board.

### Main Flow

1. The game asks for the knight's moves from its square.
2. The system looks at the 8 L-shaped jumps from that square.
3. The system drops every jump that would leave the board.
4. The system drops every jump that lands on a piece of the knight's own color.
5. The system returns the remaining squares. Squares holding an opponent's piece are captures.

### Postconditions

- The board is unchanged. Listing moves never moves a piece.

## Out of scope

Whether a move leaves the player's own king in check is decided by the check feature, which filters the moves of every piece.
