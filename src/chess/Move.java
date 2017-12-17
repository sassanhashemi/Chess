package chess;

class Move {

    private Piece piece;
    private int start;
    private int end;

    Move(Piece piece, int start, int end) {
        this.piece = piece;
        this.start = start;
        this.end = end;
    }

    int getStart() {
        return this.start;
    }
    int getEnd() {
        return this.end;
    }
    Piece getPiece() {
        return this.piece;
    }
    void setStart(int start) {
        this.start = start;
    }
    void setEnd(int end) {
        this.end = end;
    }
    void setPiece(Piece piece) {
        this.piece = piece;
    }


}
