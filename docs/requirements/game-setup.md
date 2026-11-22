# Game Setup Phase

## User Story

As a chess player, I want a new chess game to be set up automatically with the correct players, board, pieces, and starting turn, so that the game is ready for the first move without requiring any manual setup.

## Acceptance Criteria

- The game must be configured for exactly two players.
- After leading and trailing whitespace is removed, each player name must contain between 1 and 30 characters inclusive; otherwise, the game must not start. 
- Player 1 must be assigned the White pieces.
- Player 2 must be assigned the Black pieces.
- The system must create an 8-by-8 chessboard.
- The board must contain exactly 32 pieces: 16 White pieces and 16 Black pieces.
- Each player must begin with exactly:
  - 8 Pawns
  - 2 Rooks
  - 2 Knights
  - 2 Bishops
  - 1 Queen
  - 1 King
- All pieces must be placed in their standard starting positions.
- All squares between the two sides' starting pieces must initially be empty.
- White must be assigned the first turn.
- The game screen must display:
  - The chessboard
  - All 32 pieces
  - Both player names
  - Each player's assigned color
  - The current turn
- No pieces may be captured, selected, or moved when setup is complete.
- Once setup is complete, the game must be ready for White to make the first move.

## Use Case 1: Start New Chess Game

### Actor

Player

### Preconditions

- The chess application has been launched.
- The welcome screen is displayed.
- No game is currently in progress.

### Main Flow

1. The system displays input fields for Player 1 and Player 2.
2. The players enter their names.
3. A player clicks the `Start Game` button.
4. The system removes leading and trailing whitespace from both player names and validates that each trimmed name contains between 1 and 30 characters.
5. The system assigns the White pieces to Player 1.
6. The system assigns the Black pieces to Player 2.
7. The system creates an 8-by-8 chessboard.
8. The system creates 16 White pieces and 16 Black pieces.
9. The system places all pieces in their standard starting positions.
10. The system sets White as the current turn.
11. The system displays the game screen.
12. The game screen displays the board, all pieces, both player names, their assigned colors, and the current turn.
13. The game becomes ready for White to make the first move.

### Alternate Flow 4.a: One or Both Player Names Are Blank

1. The system determines that one or both player names are blank after leading and trailing whitespace is removed.
2. The system displays an error message asking the players to enter both names.
3. The game is not initialized.
4. The welcome screen remains open.
5. The use case resumes at Step 2 of the Main Flow.

### Alternate Flow 4.b: One or Both Player Names Are Too Long

1. The system determines that, after leading and trailing whitespace is removed, one or both player names contain more than 30 characters.
2. The system displays an error message asking the players to enter names containing no more than 30 characters.
3. The game is not initialized.
4. The welcome screen remains open.
5. The use case resumes at Step 2 of the Main Flow.

### Postconditions

- An 8-by-8 chessboard has been created.
- The board contains exactly 32 pieces in their standard starting positions.
- Player 1 controls the White pieces.
- Player 2 controls the Black pieces.
- White is the current player.
- No moves have been made.
- No pieces have been captured.
- The game is ready for the first turn.