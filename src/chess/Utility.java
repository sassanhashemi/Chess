// Utility.java
// Sassan Hashemi

package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//TODO: moves to location and moves to piece
//TODO: Get input in getMove
//TODO: When putting the king in check, don't check pawn's moves



class Utility {

    final static int EMPTY = -1;
    final static int WHITE = 0;
    final static int BLACK = 1;

    final static int K = 0;
    final static int R1 = 2;
    final static int R2 = 3;
    final static int B1 = 4;
    /*
    public final static int Q = 1;
    public final static int B1 = 4;
    public final static int B2 = 5;
    public final static int N1 = 6;
    public final static int N2 = 7;
    public final static int P1 = 8;
    public final static int P2 = 9;
    public final static int P3 = 10;
    public final static int P4 = 11;
    public final static int P5 = 12;
    public final static int P6 = 13;
    public final static int P7 = 14;
    public final static int P8 = 15;
    */

    // Piece Definitions
    public static Piece nullPiece = new NullPiece();

    private static Rook br1 = new Rook(BLACK, 0);
    private static Knight bn1 = new Knight(BLACK, 1);
    private static Bishop bb1 = new Bishop(BLACK, 2);
    private static Queen bq = new Queen(BLACK, 3);
    private static King bk = new King(BLACK, 4);
    private static Bishop bb2 = new Bishop(BLACK, 5);
    private static Knight bn2 = new Knight(BLACK, 6);
    private static Rook br2 = new Rook(BLACK, 7);

    private static Pawn bp1 = new Pawn(BLACK, 8);
    private static Pawn bp2 = new Pawn(BLACK, 9);
    private static Pawn bp3 = new Pawn(BLACK, 10);
    private static Pawn bp4 = new Pawn(BLACK, 11);
    private static Pawn bp5 = new Pawn(BLACK, 12);
    private static Pawn bp6 = new Pawn(BLACK, 13);
    private static Pawn bp7 = new Pawn(BLACK, 14);
    private static Pawn bp8 = new Pawn(BLACK, 15);


    private static Pawn wp1 = new Pawn(WHITE, 48);
    private static Pawn wp2 = new Pawn(WHITE, 49);
    private static Pawn wp3 = new Pawn(WHITE, 50);
    private static Pawn wp4 = new Pawn(WHITE, 51);
    private static Pawn wp5 = new Pawn(WHITE, 52);
    private static Pawn wp6 = new Pawn(WHITE, 53);
    private static Pawn wp7 = new Pawn(WHITE, 54);
    private static Pawn wp8 = new Pawn(WHITE, 55);

    private static Rook wr1 = new Rook(WHITE, 56);
    private static Knight wn1 = new Knight(WHITE, 57);
    private static Bishop wb1 = new Bishop(WHITE, 58);
    private static Queen wq = new Queen(WHITE, 59);
    private static King wk = new King(WHITE, 60);
    private static Bishop wb2 = new Bishop(WHITE, 61);
    private static Knight wn2 = new Knight(WHITE, 62);
    private static Rook wr2 = new Rook(WHITE, 63);


    static Piece[][] pieces = {{wk, wq, wr1, wr2, wb1, wb2, wn1, wn2, wp1, wp2, wp3, wp4, wp5, wp6, wp7, wp8},
                               {bk, bq, br1, br2, bb1, bb2, bn1, bn2, bp1, bp2, bp3, bp4, bp5, bp6, bp7, bp8}};



    static int[] getRC(int location) {
        int result[] = new int[2];
        result[0] = (location / 8);
        result[1] = (location % 8);
        return result;
    }
    static ArrayList<Integer> squaresBetween(int start, int end) {
        ArrayList<Integer> squares = new ArrayList<>();
        int startRow = getRC(start)[0];
        int startCol = getRC(start)[1];
        int endRow = getRC(end)[0];
        int endCol = getRC(end)[1];
        boolean vertical = (startCol == endCol);
        boolean horizontal = (startRow == endRow);
        boolean diagonal = (java.lang.Math.abs(endRow - startRow) == Math.abs(endCol - startCol));
        int dY = startRow - endRow;
        int dX = endCol - startCol;

        if (vertical && dY > 1) {                   // Up
            for (int i = 1; i < dY; i++) {
                squares.add(start - 8 * i);
            }
        } else if (vertical && dY < 1) {            // Down
            for (int i = -1; i > dY; i--) {
                squares.add(start - 8 * i);
            }
        } else if (horizontal && dX > 1) {          // Right
            for (int i = 1; i < dX; i++) {
                squares.add(start + i);
            }
        } else if (horizontal && dX < 1) {          // Left
            for (int i = -1; i > dX; i--) {
                squares.add(start + i);
            }
        } else if (diagonal && dY > 1 && dX > 1) {  // Up Right
            for (int i = 1; i < Math.abs(dY); i++) {
                squares.add(start - 7 * i);
            }
        } else if (diagonal && dY > 1 && dX < 1) {  // Up Left
            for (int i = 1; i < Math.abs(dY); i++) {
                squares.add(start - 9 * i);
            }
        } else if (diagonal && dY < 1 && dX > 1) {  // Down Right
            for (int i = 1; i < Math.abs(dY); i++) {
                squares.add(start + 9 * i);
            }
        } else if (diagonal && dY < 1 && dX < 1) {  // Down Left
            for (int i = 1; i < Math.abs(dY); i++) {
                squares.add(start + 7 * i);
            }
        }
        return squares;
    }

    //TODO: Keep asking until a valid move is given
    static String getMove(int turn) {
        /*
         * Nf3
         * Nxf3
         * Rae3
         * Raxe2
         * exd6e.p en passant
         * e8=Q
         * exd8=N
         * 0-0
         * 0-0-0
         */
        String move = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (turn == Utility.WHITE) {
            System.out.println("Enter a move white:");

        } else if (turn == Utility.BLACK){
            System.out.println("Enter a move black:");
        }
        try {
            move = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        //TODO: convert string to move
        return move;
    }

    //TODO: Add promotion
    //TODO: Fix Rae1
    static Move stringToMove(String moveStr, int turn) throws ChessException {
        // Castling
        if (moveStr.equals("0-0")) {
            return new Move(Utility.pieces[turn][Utility.K], Utility.pieces[turn][Utility.K].getLocation(), 62 - 56 * turn);
        } else if (moveStr.equals("0-0-0")) {
            return new Move(Utility.pieces[turn][Utility.K], Utility.pieces[turn][Utility.K].getLocation(), 58 - 56 * turn);
        }

        //Piece type
        ArrayList<Piece> possiblePieces = new ArrayList<Piece>();
        Piece piece = nullPiece;
        String pieceType = "";
        int pawnFile = -1;
        char pieceChar = moveStr.charAt(0);
        switch (pieceChar) {
            case 'N':
                pieceType = "Knight";
                break;
            case 'K':
                pieceType = "King";
                break;
            case 'R':
                pieceType = "Rook";
                break;
            case 'B':
                pieceType = "Bishop";
                break;
            case 'Q':
                pieceType = "Queen";
                break;
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
                pieceType = "Pawn";
                pawnFile = pieceChar - 97;
                break;
        }

        //Capturing
        boolean capture = moveStr.charAt(1) == 'x' || moveStr.charAt(2) == 'x';
        int capture1 = (moveStr.charAt(1) == 'x') ? 1 : 0;
        int capture2 = (moveStr.charAt(2) == 'x') ? 1 : 0;
        int captureInt = capture1 + capture2;

        //Location
        int letter = moveStr.charAt(1 + captureInt) - 97;
        int number = 8 - moveStr.charAt(2 + captureInt);
        int location = 8 * number + letter;

        //Find specific piece
        for (Piece currPiece : pieces[turn]) {
            if (currPiece.getType().equals(pieceType) && !currPiece.isCaptured()) {
                for (Move move : currPiece.getMoves()) {
                    if (move.getEnd() == location) {
                        possiblePieces.add(currPiece);
                    }
                }
            }
        }

        //Finding piece
        //TODO: Take into account multiple pawns that can capture a piece
        if (possiblePieces.size() > 1 && capture2 == 1) {                   // If there are more than 1 possible pieces and it is specified in the move
            Piece piece1 = possiblePieces.get(0);
            Piece piece2 = possiblePieces.get(1);
            if (piece1.getLocation() / 8 == piece2.getLocation() / 8) {         // Same Row
                int let = moveStr.charAt(1) - 97;
                if (let < 0 || let >= 8) {
                    throw new ChessException("Invalid row number");
                }
                if (piece1.getLocation() % 8 == let) {
                    piece = piece1;
                } else if (piece2.getLocation() % 8 == let) {
                    piece = piece2;
                } else {
                    throw new ChessException("Logic error: no piece is currently on the specified square");
                }
            } else if (piece1.getLocation() % 8 == piece2.getLocation() % 8) {  // Same column
                int num = moveStr.charAt(1) - 49;
                if ((num < 0 || num >= 8)) {
                    throw new ChessException("Invalid column number");
                }
                if (piece1.getLocation() / 8 == num) {
                    piece = piece1;
                } else if (piece2.getLocation() / 8 == num) {
                    piece = piece2;
                } else {
                    throw new ChessException("Logic error: no piece is currently on the specified square");
                }
            }
        } else if (possiblePieces.size() == 1 && capture1 == 1) {
            piece = possiblePieces.get(0);
        } else {
            throw new ChessException("Invalid move format");
        }

        if (piece == nullPiece) {
            throw new ChessException("Invalid move");
        }

        return new Move(piece, piece.getLocation(), location, capture);
    }

    //TODO: FIX EN PASSANT
    static Move tempStringToMove(Board board) throws ChessException {
        // Move in format e2-e4
        String moveStr = Utility.getMove(board.getTurn());
        int start = (moveStr.charAt(0) - 97) + 8 * (8 - (moveStr.charAt(1) - 48));
        int end = (moveStr.charAt(3) - 97) + 8 * (8 - (moveStr.charAt(4) - 48));
        Piece piece = board.getSquare(start);
        boolean capture = board.getSquare(end).getColor() == 1 - piece.getColor();
        Move move = new Move(piece, start, end, capture);
        boolean enPassant = move.isEnPassant(board);
        while (!piece.isLegalMove(board, move)) {
            moveStr = Utility.getMove(board.getTurn());
            start = (moveStr.charAt(0) - 97) + 8 * (8 - (moveStr.charAt(1) - 48));
            end = (moveStr.charAt(3) - 97) + 8 * (8 - (moveStr.charAt(4) - 48));
            piece = board.getSquare(start);
            capture = board.getSquare(end).getColor() == 1 - piece.getColor();
            move = new Move(piece, start, end, capture, "", enPassant);
        }
        //TODO: Check to see if i should just return move
        return new Move(piece, start, end, capture, "", enPassant);

    }

    /*
    static Move tempStringToMove(Board board, String moveStr) {
        // Move in format e2-e4
        int start = (moveStr.charAt(0) - 97) + 8 * (8 - (moveStr.charAt(1) - 48));
        int end = (moveStr.charAt(3) - 97) + 8 * (8 - (moveStr.charAt(4) - 48));
        Piece piece = board.getSquare(start);
        boolean capture = board.getSquare(end).getColor() == 1 - piece.getColor();
        Move move = new Move(piece, start, end, capture);

        return move;

    }
    */

    public static void promote(Board board, Pawn pawn, String pieceType) throws ChessException {
        int index = -1;
        for (int i = 8; i < 16; i++) {
            if (Utility.pieces[pawn.getColor()][i] == pawn) {
                index = i;
            }
        }
        if (pieceType.equals("Queen")) {
            Queen queen = new Queen(pawn.getColor(), pawn.getLocation());
            board.setSquare(queen.getLocation(), queen);
            Utility.pieces[pawn.getColor()][index] = queen;
        } else if (pieceType.equals("Rook")) {
            Rook rook = new Rook(pawn.getColor(), pawn.getLocation());
            board.setSquare(rook.getLocation(), rook);
            Utility.pieces[pawn.getColor()][index] = rook;
        } else if (pieceType.equals("Bishop")) {
            Bishop bishop = new Bishop(pawn.getColor(), pawn.getLocation());
            board.setSquare(bishop.getLocation(), bishop);
            Utility.pieces[pawn.getColor()][index] = bishop;
        } else if (pieceType.equals("Knight")) {
            Knight knight = new Knight(pawn.getColor(), pawn.getLocation());
            board.setSquare(knight.getLocation(), knight);
            Utility.pieces[pawn.getColor()][index] = knight;
        } else {
            throw new ChessException("Invalid piece type parameter");
        }

    }

}
