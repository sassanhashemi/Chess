package chess;

public class Queen extends Piece {

    Queen(int color, int location) {
        super(color, location);
    }
    public boolean isLegalMove(Board board, Move move) {
        int location = move.getEnd();
        boolean obstructedEnd = (board.getSquare(location).getColor() == this.getColor());
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(location)[0];
        boolean rightDirection = (Math.abs(dX - dY) == 0 || dY == 0 || dX == 0);
        boolean obstructedPath = false;
        boolean startsMatch = move.getStart() == this.getLocation();
        for (int square : Utility.squaresBetween(this.getLocation(), location)) {
            if (!(board.getSquare(square) instanceof NullPiece)) {
                System.out.println(board.getSquare(square).toString());
                obstructedPath = true;
            }
        }
        boolean capture = (board.getSquare(move.getEnd()).getColor()) == 1 - this.getColor();
        boolean correctCapture = capture == move.getCapture();
        System.out.println(rightDirection);
        System.out.println(obstructedEnd);
        System.out.println(obstructedPath);
        System.out.println(startsMatch);
        System.out.println(correctCapture);
        return (rightDirection && !obstructedEnd && !obstructedPath && startsMatch && correctCapture);
    }

    @Override
    public String toString() {
        if (this.getColor() == Utility.WHITE) {
            return "Q";
        } else if (this.getColor() == Utility.BLACK) {
            return "q";
        } else {
            return null;
        }
    }

}
