# BVA: `domain.Player`

`Player` stores a trimmed, non-blank name containing at most 30 characters and a color. Its public methods come from the design in [design/README.md](../design/README.md): `Player(String, PieceColor)`, `getName()` and `getColor()`.

## Steps 1-3: Input analysis

| Input               | Kind                                       | Valid range                                                                      | Values tested                                                                                        |
|---------------------|--------------------------------------------|----------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
| `name` in `Player`  | `String` reference / trimmed string length | non-`null`; after trimming, length must be between 1 and 30 characters inclusive | `null`, `""`, `" \t\n "`, `"A"`, `"Alice"`, `"A".repeat(30)`, `"A".repeat(31)`, `"  Alice  Smith  "` |
| `color` in `Player` | `PieceColor` reference                     | `WHITE` or `BLACK`; not `null`                                                   | `WHITE`, `BLACK`, `null`                                                                             |
| Stored name         | string state                               | leading and trailing whitespace removed; internal whitespace preserved           | `"A"`, `"Alice"`, `"Alice  Smith"`                                                                   |

The valid trimmed name length is from 1 to 30 characters inclusive. Length 0 is immediately below the lower boundary and is invalid. Length 1 is the minimum valid value, length 30 is the maximum valid value, and length 31 is immediately above the upper boundary and is invalid.

The length validation is performed after removing leading and trailing whitespace. Internal whitespace is preserved and counts toward the 30-character limit.

Following the null-handling policy, a null name throws `NullPointerException` with message `"name must not be null"`, and a null color throws `NullPointerException` with message `"color must not be null"`.

A blank name throws `IllegalArgumentException` with message `"name must not be blank"`. A trimmed name longer than 30 characters throws `IllegalArgumentException` with message `"name must not exceed 30 characters"`.

## Step 4: Test cases

All tests will be in `src/test/java/domain/PlayerTest.java`.

### Methods under test: `Player(String name, PieceColor color)`, `getName()`, `getColor()`

| ID  | State / Input                                                       | Expected output                                                                                              | Test method                                                                 | Implemented? |
|-----|---------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|--------------|
| TC1 | construct with `"A"`, `WHITE` (minimum valid length)                | `getName()` returns `"A"` and `getColor()` returns `WHITE`                                                   | `constructor_singleCharacterName_storesNameAndWhiteColor`                   | Not yet      |
| TC2 | construct with `"A".repeat(30)`, `WHITE` (maximum valid length)     | `getName()` returns the 30-character name and `getColor()` returns `WHITE`                                   | `constructor_thirtyCharacterName_storesName`                                | Not yet      |
| TC3 | construct with `"A".repeat(31)`, `WHITE` (one above maximum length) | `IllegalArgumentException`: `"name must not exceed 30 characters"`                                           | `constructor_nameLongerThanThirtyCharacters_throwsIllegalArgumentException` | Not yet      |
| TC4 | construct with `"Alice"`, `BLACK`                                   | `getName()` returns `"Alice"` and `getColor()` returns `BLACK`                                               | `constructor_blackColor_storesBlackColor`                                   | Not yet      |
| TC5 | construct with `"  Alice  Smith  "`, `WHITE`                        | `getName()` returns `"Alice  Smith"`; surrounding whitespace is removed and internal whitespace is preserved | `constructor_surroundingWhitespace_trimsNameAndPreservesInternalSpace`      | Not yet      |
| TC6 | construct with `""`, `WHITE` (immediately below minimum length)     | `IllegalArgumentException`: `"name must not be blank"`                                                       | `constructor_emptyName_throwsIllegalArgumentException`                      | Not yet      |
| TC7 | construct with `" \t\n "`, `WHITE`                                  | `IllegalArgumentException`: `"name must not be blank"`                                                       | `constructor_whitespaceOnlyName_throwsIllegalArgumentException`             | Not yet      |
| TC8 | construct with `null`, `WHITE`                                      | `NullPointerException`: `"name must not be null"`                                                            | `constructor_nullName_throwsNullPointerException`                           | Not yet      |
| TC9 | construct with `"Alice"`, `null`                                    | `NullPointerException`: `"color must not be null"`                                                           | `constructor_nullColor_throwsNullPointerException`                          | Not yet      |