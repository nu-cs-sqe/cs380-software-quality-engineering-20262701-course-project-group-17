# Design: Game Setup Phase

This design implements the requirements in [requirements/game-setup.md](../requirements/game-setup.md). The class diagram source is [game-setup.puml](game-setup.puml); you can render it with PlantUML or the IntelliJ PlantUML plugin.

## Layers
The code is split into two packages.

- `domain` holds all of the game logic and never imports Swing. Everything in it is built with TDD and covered by BVA, coverage, and mutation testing.
- `ui` holds the Swing views and a thin controller. Views aren't held to the coverage and mutation targets, which is exactly why logic shouldn't live there.

## Domain classes
| Class | Responsibility | Owner |
|-------|----------------|-------|
| `Position` | Immutable square on the board (`row`, `col` in 0..7). Rejects off-board values. Value equality. | Kris |
| `PieceType` (enum) | KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN | Kris |
| `PieceColor` (enum) | WHITE, BLACK | Kris |
| `Piece` | Immutable (type, color) pair with value equality and `isSameColorAs`. | Kris |
| `Board` | Owns the 8×8 grid: `getPiece`, `isEmpty`, `placePiece`, plus static factory `Board.createStandard()` for the starting position. | Max |
| `Player` | Name (trimmed, must contain between 1 and 30 characters) and color. | Cindy |
| `Game` | Built from two names: creates the players (White/Black), creates the standard board, sets the current player to White. Exposes `getBoard()`, `getCurrentPlayer()`, `getWhitePlayer()`, `getBlackPlayer()`. | Cindy |

## Design decisions
1. For now, `Piece` is a single class rather than six subclasses. Setup only needs to know a piece's type and color. Once we add movement, each `PieceType` will get its own move rule, so we'll add classes when there's actually behavior to put in them. Until then, six empty subclasses would just be clutter.
2. `Position` validates itself. Every other class can assume a `Position` is on the board, so `Board` never needs its own bounds checks.
3. `Board.createStandard()` is a static factory. Tests can still create an empty `new Board()` and place pieces wherever they like, which we'll need later for check, castling, and en passant scenarios.
4. `Board.getPiece` returns `Optional<Piece>` instead of `null`, following *Clean Code* Ch. 7.
5. The UI never changes domain objects directly. `BoardController` reads the board through `getPiece(Position)`, and later it will pass clicks on to `Game`.
6. Locale support (an A-grade requirement) is built into the UI from the start. Every string the user sees comes from a `ResourceBundle` (`messages_en.properties`, `messages_zh.properties`, and so on), so adding a language means adding one properties file and changing no code.

## Dependency order
`Position` and `Piece` come first, then `Board`, then `Player` and `Game`, and finally the UI. Kris's PRs should merge first so that Max can build `Board` on top of them. Nobody has to sit and wait, though. Following the Week 5 guidance, anyone whose class depends on unfinished work can first agree on the method signatures above, then add an empty placeholder version of that class to their own feature branch and stub it in tests. The placeholder gets deleted when the real class is pulled in from `main`.

# Design: Select a Piece

This design implements issue #25. The class diagram source is [select-piece.puml](select-piece.puml).

| Class | Responsibility | Owner |
|-------|----------------|-------|
| `LegalMoves` (interface) | `from(Board, Position)` returns the squares the piece on that position can move to. The movement rules (#19 to #22) will implement it. | Max |
| `PieceSelection` | Remembers the selected square and its highlighted moves. `select(Position, PieceColor)` selects an own piece, deselects the selected piece, switches to another own piece, and ignores every other click. It only reads the board. | Max |

## Design decisions
1. The turn is passed into `select` as a `PieceColor`, so `PieceSelection` doesn't depend on `Game` and can't change the turn.
2. Legal moves sit behind the `LegalMoves` interface, so selection can be built and tested with an EasyMock mock before any movement rule exists.
3. `getHighlights` returns an unmodifiable copy (`Set.copyOf`), so nothing outside can change the highlights.

# Design: Move a Piece and Switch Turns

This design implements issue #26. The class diagram source is [move-piece.puml](move-piece.puml).

| Class | Responsibility | Owner |
|-------|----------------|-------|
| `Board` | New `movePiece(from, to)`: puts the piece on `to` and empties `from`. | Max |
| `PieceSelection` | New `clear()`: empties the selection after a move. | Max |
| `Turn` | Holds the current color (White first). `click(Position)` moves the selected piece when the square is highlighted, then clears the selection and passes the turn. Every other click is a selection. | Max |

## Design decisions
1. A move is only allowed onto a highlighted square, so `Turn` never checks movement rules itself. The rules stay behind `LegalMoves`.
2. `Turn` is the one place that changes the current color. When `Game` (Cindy) is built, it can keep a `Turn` and map `getCurrentColor()` to the white or black `Player`.
3. Showing the new turn on the game screen is UI wiring and will be done when the Swing views are merged.
