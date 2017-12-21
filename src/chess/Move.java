package chess;

//TODO: Add check to constructors
import java.lang.StringBuilder;

class Move {

    private Piece piece;
    private int start;
    private int end;
    private boolean capture;
    private boolean promotion;
    private boolean check;

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
    boolean getCheck() {
        return this.check;
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
    void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }
    void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        //result.append("Start: ", (String) this.getStart());
        result.append("Piece: ");
        result.append(this.getPiece().toString());
        result.append("\n");
        result.append("Start: ");
        result.append(this.getStart());
        result.append("\n");
        result.append("End: ");
        result.append(this.getEnd());
        result.append("\n");
        result.append("Capture: ");
        result.append(this.getCapture());
        result.append("\n");
        result.append("Promotion: ");
        result.append(this.getPromotion());
        return result.toString();
    }
}
