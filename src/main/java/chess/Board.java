// Board.java
// Sassan Hashemi

package chess;

//import com.sun.istack.internal.Nullable;

//import javax.rmi.CORBA.Util;
//import java.lang.reflect.Array;
import java.util.ArrayList;


public class Board {

    private Piece[] board = new Piece[64];
    private ArrayList<String> previousBoards;
    private ArrayList<Move> previousMoves;
    private int numMoves;
    private int turn;
    private String gameState; // either "in progress", "black win", "white win", "draw"
    private int mslcopa; // moves since last capture or pawn advance
    private boolean check;

    Board() {
        this.reset();
        this.previousBoards = new ArrayList<String>();
        this.previousMoves = new ArrayList<Move>();
        this.numMoves = 0;
        this.turn = Utility.WHITE;
        this.gameState = "in progress";
        this.mslcopa = 0;
        this.check = false;
        //this.updateAllMoves();
    }

    /**
     * @return  an array of pieces representing the board
     */
    public Piece[] getBoard() {
        return this.board;
    }

    /**
     * @return  an ArrayList of Strings representing all previous board states
     */
    ArrayList<String> getPreviousBoards() {
        return this.previousBoards;
    }

    /**
     * @return  an ArrayList of Moves representing all previous moves
     */
    ArrayList<Move> getPreviousMoves() {
        return this.previousMoves;
    }

    /**
     * @return                  the number of moves played
     * @throws ChessException   if number of moves is less than 0 (corrupted private vars)
     */
    public int getNumMoves() throws ChessException {
        if (numMoves >= 0) {
            return this.numMoves;
        } else {
            throw new ChessException("Number of moves is < 0");
        }

    }

    /**
     * @return  an integer representing the player whose turn it is
     */
    int getTurn() {
        return this.turn;
    }

    /**
     * @return  the current game state (in progress, draw, white win, black win)
     */
    String getGameState() {
        return this.gameState;
    }

    /**
     * @return  number of moves since last capture or pawn advance
     */
    public int getMslcopa() {
        return this.mslcopa;
    }

    /**
     * @return  true if the king is checked
     */
    boolean getCheck() {
        return this.check;
    }


    /**
     * Appends the current board state to an ArrayList of previous board states
     */
    void appendPreviousBoards() {
        this.previousBoards.add(this.toString());
    }

    /**
     * Appends move to an ArrayList of previous moves
     * @param move  Move to be appended
     */
    void appendPreviousMoves(Move move) {
        this.previousMoves.add(move);
    }

    /**
     * Increments the number of moves by one
     */
    void incrementNumMoves() {
        this.numMoves++;
    }

    /**
     * Changes the turn to the opposite player
     */
    void incrementTurn() {
        this.turn = 1 - this.turn;
    }

    /**
     * Sets the game state to the specified string
     * @param gameState         a string representing the new game state
     * @throws ChessException   throws exception if an invalid game state is input
     */
    void setGameState(String gameState) throws ChessException {
        if (gameState.equals("in progress") || gameState.equals("white win") || gameState.equals("black win") || gameState.equals("draw")) {
            this.gameState = gameState;
        } else {
            throw new ChessException("Invalid gameState");
        }
    }

    /**
     * Increments mslcopa by one if increment is true, and resets it if false
     * @param increment     true if no pawn advance or capture, false otherwise
     */
    void incrementMslcopa(boolean increment) {
        if (increment) {
            this.mslcopa++;
        } else {
            this.mslcopa = 0;
        }
    }

    /**
     * Sets check to the specified value
     * @param check     a boolean representing the desired value of check
     */
    void setCheck(boolean check) {
        this.check = check;
    }

    /**
     * @return a string representing the board
     */
    String printBoard() {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < 64; i++) {
            result.append(this.getSquare(i).toString());
            result.append(" ");
            if (i % 8 == 7) {
                result.append("\n");
            }
        }
        return new String(result);
    }


    /**
     * Clears the board and sets every square to nullPiece
     */
    void clear() {
        for (int i = 0; i < 64; i++) {
            this.setSquare(i, Utility.nullPiece);
        }
    }

    /**
     * Resets the board to the state at the beginning of the game
     */
    void reset() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 16; j++) {
                this.setSquare(Utility.pieces[i][j].getLocation(), Utility.pieces[i][j]);
            }
        }
        for (int i = 16; i < 48; i++) {
            this.setSquare(i, Utility.nullPiece);
        }
    }

    /**
     * @return  true if there is insufficient material to win the game
     */
    boolean insufficientMaterial() {
        int whiteMaterial = 0, blackMaterial = 0;
        for (Piece piece : this.board) {
            if (piece != null) {
                if (piece.toString().equals("R") || piece.toString().equals("Q") || piece.toString().equals("P")) {
                    return false;
                } else if (piece.toString().equals("r") || piece.toString().equals("q") || piece.toString().equals("p")) {
                    return false;
                } else if (piece.toString().equals("B") || piece.toString().equals("N")) {
                    whiteMaterial++;
                } else if (piece.toString().equals("b") || piece.toString().equals("n")) {
                    blackMaterial++;
                }
            }
        }
        return (whiteMaterial <= 1 || blackMaterial <= 1);
    }

    /**
     * @return  true if the game is a draw due to 3 identical board states
     */
    boolean boardRepetitionDraw() {
        for (int i = 0; i < this.getPreviousBoards().size(); i++) {
            int numRepeatedStates = 0;
            for (int j = 0; j < this.getPreviousBoards().size(); j++) {
                if (this.getPreviousBoards().get(i).equals(this.getPreviousBoards().get(j))) {
                    numRepeatedStates++;
                }
            }
            if (numRepeatedStates >= 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param color     the color representing the player
     * @return          true if there are no legal moves for the specified player
     */
    boolean noLegalMoves(int color) {
        boolean availableMoves = false;
        int kingLocation = Utility.pieces[color][Utility.K].getLocation();
        for (Piece piece : this.getBoard()) {
            if (piece.getColor() == color) {
                if (piece.getMoves().size() > 0) {
                    availableMoves = true;
                }
            }
        }
        return (!availableMoves && this.isSafe(kingLocation, color));
    }

    /**
     *
     * @param location  the location to check for safety
     * @param color     the player to check for safety
     * @return          true if the specified location is safe for the specified player
     */
    boolean isSafe(int location, int color) {
        for (Piece piece : Utility.pieces[1 - color]) {
            if (!piece.isCaptured()) {
                for (Move move : piece.getMoves()) {
                    if (move.getEnd() == location) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    /**
     * @param location          the square to find the desired piece
     * @return                  the piece in the specified square
     * @throws ChessException   if the square is not on the board
     */
    Piece getSquare(int location) throws ChessException {
        if (location >= 0 && location < 64) {
            return this.board[location];
        } else {
            throw new ChessException("Invalid square number");
        }

    }

    /**
     * Sets the specified square to the specified piece
     * @param location          the square to be set
     * @param piece             the piece to place on the square
     * @throws ChessException   if the square is not on the board
     */
    void setSquare(int location, Piece piece) throws ChessException {
        if (location < 64 && location >= 0) {
            this.board[location] = piece;
        } else {
            throw new ChessException("Invalid location number");
        }

    }

    /**
     * Updates the current game state
     */
    void updateGameState() {
        if (this.isDraw()) {
            this.gameState = "draw";
        } else if (isCheckMated(Utility.BLACK)) {
            this.gameState = "white win";
        } else if (isCheckMated(Utility.WHITE)) {
            this.gameState = "black win";
        } else {
            this.gameState = "in progress";
        }
    }

    /**
     * @param color     the color of the player
     * @return          true if the specified player is checkmated
     */
    boolean isCheckMated(int color) {
        /* 1. King is in check
     * 2. King cannot move
     * 3. If there is only one checking piece:
              if possible block or capture:
                  no check mate
              else:
                  check mate

          Else:
              check mate
     */
        boolean isChecked, kingCanMove, canBlock = false, canCapture = false;
        ArrayList<Piece> checkingPieces = new ArrayList<>();

        int kingLocation = Utility.pieces[color][Utility.K].getLocation();
        isChecked = !this.isSafe(kingLocation, color);

        kingCanMove = !(this.getSquare(kingLocation).getMoves().size() == 0);

        for (Piece piece : Utility.pieces[1 - color]) {
            for (Move move : piece.getMoves()) {
                if (move.getEnd() == kingLocation) {
                    checkingPieces.add(piece);
                }
            }
        }

        if (checkingPieces.size() > 1) {
            return (isChecked && !kingCanMove);
        } else if (checkingPieces.size() == 1) {
            // Find possible captures or blocks
            for (Piece piece : Utility.pieces[color]) {
                for (Move move : piece.getMoves()) {
                    if (move.getEnd() == checkingPieces.get(0).getLocation()) {
                        canCapture = true;
                    }
                }
            }

            for (Piece piece : Utility.pieces[color]) {
                for (Move move : piece.getMoves()) {
                    for (int square : Utility.squaresBetween(checkingPieces.get(0).getLocation(), kingLocation)) {
                        if (move.getEnd() == square) {
                            canBlock = true;
                        }
                    }
                }
            }
        }
        return (isChecked && !kingCanMove && !canBlock && !canCapture);
    }

    /**
     * @return  true if there is a draw on the board
     */
    boolean isDraw() {
        return (this.insufficientMaterial() || this.boardRepetitionDraw() || this.noLegalMoves(this.turn) || this.mslcopa >= 50);
    }

    /**
     * Updates all possible moves for all pieces on the board
     */
    void updateAllMoves() {
        for (int i = 0; i < 2; i ++) {
            for (Piece piece : Utility.pieces[i]) {
                if (!piece.isCaptured()) {
                    piece.updateMoves(this);
                }
            }
        }
    }

    /**
     * Executes the move of castling
     * @param move  the move
     */
    void castle(Move move) {
        assert(move.isCastling());
        if (move.getEnd() == 2) {
            Rook rook = (Rook) Utility.pieces[move.getPiece().getColor()][Utility.R1];
            this.setSquare(3, rook);
            this.setSquare(rook.getLocation(), Utility.nullPiece);
            rook.setLocation(3);
        } else if (move.getEnd() == 6) {
            Rook rook = (Rook) Utility.pieces[move.getPiece().getColor()][Utility.R2];
            this.setSquare(5, rook);
            this.setSquare(rook.getLocation(), Utility.nullPiece);
            rook.setLocation(5);
        } else if (move.getEnd() == 58) {
            Rook rook = (Rook) Utility.pieces[move.getPiece().getColor()][Utility.R1];
            this.setSquare(59, rook);
            this.setSquare(rook.getLocation(), Utility.nullPiece);
            rook.setLocation(59);
        } else if (move.getEnd() == 62) {
            Rook rook = (Rook) Utility.pieces[move.getPiece().getColor()][Utility.R2];
            this.setSquare(61, rook);
            this.setSquare(rook.getLocation(), Utility.nullPiece);
            rook.setLocation(61);
        }
        this.setSquare(move.getPiece().getLocation(), Utility.nullPiece);
        this.setSquare(move.getEnd(), move.getPiece());
        move.getPiece().setLocation(move.getEnd());
    }

    /**
     * Executes the move of capturing en passant
     * @param move  the move
     */
    void captureEnPassant(Move move) {
        int direction = -2 * (move.getPiece().getColor()) + 1; // if +1, moves up, if -1, moves down
        this.setSquare(move.getPiece().getLocation(), Utility.nullPiece);           // Set capturing pawn's square to null
        this.setSquare(move.getEnd(), move.getPiece());                             // Set end square to pawn
        this.getSquare(move.getEnd() + 8 * direction).getCaptured();        // Update captured pawns' attributes
        this.setSquare(move.getEnd() + 8 * direction, Utility.nullPiece);   // Set captured pawn's square to null
        move.getPiece().setLocation(move.getEnd());
    }

    /**
     * Executes any regular move
     * @param move  the move
     */
    void move(Move move) {
        boolean capture =  move.getCapture();
        Piece piece = move.getPiece();
        int location = move.getEnd();

        if (piece.isLegalMove(this, move)) {
            this.appendPreviousBoards();                    // Save to prev boards
            this.appendPreviousMoves(move);
            if (capture) {                                  // Update capture info
                this.getSquare(location).getCaptured();
            }

            //Special moves
            if (move.isCastling()) {
                this.castle(move);
            } else if (move.isEnPassant(this)) {
                this.captureEnPassant(move);
            } else {
                this.setSquare(piece.getLocation(), Utility.nullPiece);
                this.setSquare(location, piece);
                piece.setLocation(location);
            }

            if (move.isPromotion()){
                Utility.promote(this, (Pawn) piece, "Queen");
                //TODO: change pieceType to move.getPromotion() after stringToMove func is done
            }

            //TODO: add promotion above


            this.incrementMslcopa(!piece.toString().equals("P") && !piece.toString().equals("p") && !capture);
            piece.setMoved();
            this.incrementTurn();       // Increment turn
            this.incrementNumMoves();   // Increment numMoves
            this.updateAllMoves();      // Update possible moves
            this.updateGameState();     // Update game state
        } else {
            throw new ChessException("Invalid move");
        }
    }



    //TODO: CREATE METHOD
    /*
    boolean undoMove() {
        if (this.getNumMoves() == 0) {
            return false;
        }
        Move move = this.previousMoves.get(this.previousMoves.size() - 1);
        this.previousMoves.remove(this.previousMoves.size() - 1);       // Remove move from previousMoves
        this.previousBoards.remove(this.previousMoves.size() - 1);      // Remove board from previousBoards

        this.setSquare(move.getStart(), move.getPiece());                   // Move piece back to original square
        if (move.getCapture()) {
            String pieceType  =
            this.setSquare(move.getEnd(), )
        } else {
            this.setSquare(move.getEnd(), Utility.nullPiece);
        }


        this.numMoves--;

        this.updateAllMoves();



        return true;
    }
    */

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < 64; i++) {
            result.append(this.getSquare(i).toString());
            if (i % 8 == 7) {
                result.append("\n");
            }
        }
        return new String(result);
    }

}
