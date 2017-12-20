package chess;

class Move {

    private Piece piece;
    private int start;
    private int end;
    private boolean capture;
    private boolean promotion;

    Move(Piece piece, int start, int end, boolean capture, boolean promotion) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.capture = capture;
        this.promotion = promotion;
    }

    Move(Piece piece, int start, int end) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.capture = false;
        this.promotion = false;
    }

    Move(Piece piece, int start, int end, boolean capture) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.capture = capture;
        promotion = false;
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
    boolean getCapture() {
        return this.capture;
    }
    boolean getPromotion() {
        return this.promotion;
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
    void setCapture(boolean capture) {
        this.capture = capture;
    }
    void setPromotion() {
        this.promotion = promotion;
    }

}
