# King Movement

Issue: [#22 feature: move a king](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/22)

## User Story

As a player, I want my king to move one square in any direction, so that king play follows the rules.

## Acceptance Criteria

- A king moves one square in any of the 8 directions, onto an empty square or onto a square holding an opponent's piece.
- A king can't move onto one of its own pieces or off the board. In a corner it has 3 moves, on an edge it has 5, and in the center it has 8.
- A king can't move to a square the opponent attacks. The check feature enforces this for every move, so the king's rule here only lists the one-square steps.
- Castling is a separate issue.

## Use Case: List a King's Moves

### Actor

The game, asking on behalf of the player who selected their king.

### Preconditions

- A king of the current player's color stands on a square of the board.

### Main Flow

1. The game asks for the king's moves from its square.
2. The system looks at the 8 neighbouring squares.
3. The system drops every neighbour that is off the board.
4. The system drops every neighbour that holds a piece of the king's own color.
5. The system returns the remaining squares. Squares holding an opponent's piece are captures.

### Postconditions

- The board is unchanged.

## Out of scope

Attacked squares (handled by the check feature) and castling (its own issue).
