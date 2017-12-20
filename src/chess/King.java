package chess;

//TODO: fix abs call in all piece files
//TODO: fix canCastle

public class King extends Piece {

    public King(int color, int location) {
        super(color, location);
    }

    private boolean canCastle(Board board, Move move) {
        /* 1. King and rook have not moved (king complete)
        * 2. The king is not checked (complete)
        * 3. There is no enemy piece attacking a square between the king and rook (complete)
        * 4. All squares between the king and rook are empty
        */
        int location = move.getEnd();
        boolean hasKingMoved = this.hasMoved();
        boolean hasRookMoved;
        boolean isKingChecked = board.isSafe(this.getLocation(), this.getColor());
        boolean emptySquares = true;
        boolean squaresAttacked = false;
        boolean startsMatch = this.getLocation() == move.getStart();

        if (location == 2 && this.getColor() == Utility.BLACK) {
            hasRookMoved = Utility.pieces[this.getColor()][Utility.R1].hasMoved();
            for (int i = 1; i < 4; i++) {
                if (board.getSquare(i) != null) {
                    emptySquares = false;
                }
            }
            for (Piece piece : Utility.pieces[1 - this.getColor()]) {
                for (Move move1 : piece.getMoves()) {
                    int loc = move1.getEnd();
                    if (loc == 1 || loc == 2 || loc == 3 || loc == 4) {
                        squaresAttacked = true;
                    }
                }
            }
        } else if (location == 6 && this.getColor() == Utility.BLACK) {
            hasRookMoved = Utility.pieces[this.getColor()][Utility.R2].hasMoved();
            for (int i = 5; i < 7; i++) {
                if (board.getSquare(i) != null) {
                    emptySquares = false;
                }
            }
            for (Piece piece : Utility.pieces[1 - this.getColor()]) {
                for (Move move1 : piece.getMoves()) {
                    int loc = move1.getEnd();
                    if (loc == 4 || loc == 5 || loc == 6) {
                        squaresAttacked = true;
                    }
                }
            }
        } else if (location == 58 && this.getColor() == Utility.WHITE) {
            hasRookMoved = Utility.pieces[this.getColor()][Utility.R1].hasMoved();
            for (int i = 57; i < 60; i++) {
                if (board.getSquare(i) != null) {
                    emptySquares = false;
                }
            }
            for (Piece piece : Utility.pieces[1 - this.getColor()]) {
                for (Move move1 : piece.getMoves()) {
                    int loc = move1.getEnd();
                    if (loc == 57 || loc == 56 || loc == 59 || loc == 60) {
                        squaresAttacked = true;
                    }
                }
            }
        } else if (location == 62 && this.getColor() == Utility.WHITE) {
            hasRookMoved = Utility.pieces[this.getColor()][Utility.R2].hasMoved();
            for (int i = 61; i < 63; i++) {
                if (board.getSquare(i) != null) {
                    emptySquares = false;
                }
            }
            for (Piece piece : Utility.pieces[1 - this.getColor()]) {
                for (Move move1 : piece.getMoves()) {
                    int loc = move1.getEnd();
                    if (loc == 60 || loc == 61 || loc == 62) {
                        squaresAttacked = true;
                    }
                }
            }
        } else {
            return false;
        }

        return (!hasKingMoved && !hasRookMoved && !isKingChecked && !squaresAttacked && emptySquares && startsMatch);
    }
    public boolean isLegalMove(Board board, Move move) {
        int location = move.getEnd();
        boolean obstructedEnd = (board.getSquare(location).getColor() == this.getColor());
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(location)[0];
        boolean oneSquare = Math.abs(dX) <= 1 && Math.abs(dY) <= 1;
        boolean isSafe = board.isSafe(location, this.getColor());
        boolean startsMatch = move.getStart() == this.getLocation();
        boolean capture = (board.getSquare(move.getEnd()).getColor()) == 1 - this.getColor();
        boolean correctCapture = capture == move.getCapture();

        return ((oneSquare && !obstructedEnd && isSafe && startsMatch && correctCapture) || canCastle(board, move));
    }

    @Override
    public String toString() {
        if (this.getColor() == Utility.WHITE) {
            return "K";
        } else if (this.getColor() == Utility.BLACK) {
            return "k";
        } else {
            return null;
        }
    }
}
