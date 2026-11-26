# Pawn Promotion

Issue: [#24 feature: promote a pawn](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-17/issues/24)

## User Story

As a player, I want my pawn to be promoted when it reaches the far rank, so that I can turn it into a stronger piece.

## Acceptance Criteria

- A white pawn that reaches row 0, or a black pawn that reaches row 7, must be promoted before the turn ends.
- The player chooses a queen, rook, bishop, or knight. A pawn or a king is never offered, and choosing one is rejected.
- The pawn is replaced by the chosen piece, in the same color, on the same square.
- Promotion also works when the pawn captures onto the last rank, because only the pawn's final square matters.

## Use Case: Promote a Pawn

### Actor

The player whose pawn just reached the far rank.

### Preconditions

- The player's pawn has just moved onto its last row (row 0 for White, row 7 for Black).

### Main Flow

1. The game asks whether a promotion is due on the pawn's square, and the system says yes.
2. The game shows the four choices in order: queen, rook, bishop, knight. The labels come from the resource bundles like every other UI string.
3. The player picks one.
4. The game asks the system to promote the pawn to the chosen type.
5. The system replaces the pawn with a piece of the chosen type and the same color.
6. The turn ends.

### Alternate Flow 4.a: A Choice That Isn't Allowed

1. The game passes a king or a pawn as the choice.
2. The system rejects it and leaves the board unchanged.

### Postconditions

- The square holds the new piece, and no pawn is left on either last row.

## Out of scope

The dialog itself (the Swing UI) and ending the turn (the "move a piece" feature).
