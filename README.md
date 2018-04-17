# ChessGame
We're having fun.  I want to learn the stuff I never had much chance to.  It is easy to miss out.  

This should be run-of-the-mill but portable.  Even though I will not be spending much time on the renderer, I will just render in Console, the engine piece down to the pieces should be portable.  In such a design, I am working on how to design it correctly and decouple it from rendering surface and logic/classes.  Console Rendering will be designed for Linux.

Logical Rules:
https://www.chessusa.com/chess-rules.html

Java doc:
https://bradguru2.github.io/ChessGame/

Logical Entities:
- PlayerColor -> Upper, and Lower
- Direction -> Forward, Back, Left, Right, DiagonalUpLeft, DiagonalUpRight,
DiagonalDownLeft, DiagonalDownRight, LUpLeft, LUpRight, LLeftUp, LRightUp,
LDownLeft, LDownRight, LLeftDown, and LRightDown 
- Player -> PlayerColor, Name, and PlayerType
- Ability -> Pawn, Knight, Bishop, Rook, Queen, and King
- Piece -> Ability, History : Move[0..*], and Player
- Location -> xIndex, and yIndex
- CellColor-> Black, and White
- Cell -> CellColor, Location, and Piece[0..1]
- Move -> From:Cell, and To:Cell 
- Board -> Cells[64], Players[2]
- Rule -> ID, Ability, Direction, and NumberOf
- RuleProcessor -> Board, and Move
- PawnProcessor : RuleProcessor -> Rule[1..*]
- KnightProcessor : RuleProcessor -> Rule[1..*]
- BishopProcessor : RuleProcessor -> Rule[1..*]
- RookProcessor : RuleProcessor -> Rule[1..*]
- QueenProcessor : RuleProcessor -> Rule[1..*]
- KingProcessor : RuleProcessor -> Rule[1..*]
- RuleResult -> Matched:Rule[0..1] and CapturedPiece:Piece[0..1]

