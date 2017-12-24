package chess;

//TODO: Add check to constructors
import java.lang.StringBuilder;

class Move {

    private Piece piece;
    private int start;
    private int end;
    private boolean capture;
    private String promotion;
    private boolean check;
    private boolean enPassant;


    Move(Piece piece, int start, int end) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.capture = false;
        this.promotion = "";
        this.enPassant = false;
    }
    Move(Piece piece, int start, int end, boolean capture) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.capture = capture;
        this.promotion = "";
        this.enPassant = false;
    }
    Move(Piece piece, int start, int end, boolean capture, String promotion) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.capture = capture;
        this.promotion = promotion;
        this.enPassant = false;
    }
    Move(Piece piece, int start, int end, boolean capture, String promotion, boolean enPassant) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.capture = capture;
        this.promotion = promotion;
        this.enPassant = enPassant;
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
    String getPromotion() {
        return this.promotion;
    }
    boolean getCheck() {
        return this.check;
    }
    boolean getEnPassant() {
        return this.enPassant;
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
    void setPromotion(String promotion) {
        this.promotion = promotion;
    }
    void setCheck(boolean check) {
        this.check = check;
    }
    void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    //boolean putsKingInCheck(Board board) { }
    boolean isCastling() {
        boolean king = this.getPiece().toString().equals("K") || this.getPiece().toString().equals("k");
        boolean castling = Math.abs(this.getStart() - this.getEnd()) > 1;
        boolean rightSquare = this.getEnd() == 2 || this.getEnd() == 6 || this.getEnd() == 56 || this.getEnd() == 62;
        return king && castling && rightSquare;
    }
    boolean isEnPassant(Board board) {
        if (this.getPiece().toString().equals("p") || this.getPiece().toString().equals("P")) {
            Pawn pawn = (Pawn) this.getPiece();
            if (pawn.canCaptureEnPassant(board, this)) {
                return true;
            }
        }
        return false;
    }
    boolean isPromotion() {
        boolean pawn = this.getPiece().toString().equals("P") || this.getPiece().toString().equals("p");
        boolean eighthRank = this.getEnd() ==8 - 8 * this.getPiece().getColor();
        return pawn && eighthRank;
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
