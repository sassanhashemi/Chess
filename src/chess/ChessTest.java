package chess;


import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ChessTest {

    @Test   // COMPLETE
    public void testGameState() {
        // Test 1: in progress
        Board board = new Board();
        String gs1 = "in progress";
        board.setGameState(gs1);
        assertEquals("in progress", board.getGameState());
    }

    @Test   // COMPLETE
    public void testClearBoard() {
        Board board = new Board();
        board.clear();
        for (int i = 0; i < 64; i++) {
            assertEquals(true, board.getSquare(i) instanceof NullPiece);
        }

    }

    @Test   // COMPLETE
    public void testInsufficientMaterial() {
        Board board = new Board();
        assertEquals(false, board.insufficientMaterial());
        board.clear();
        assertEquals(true, board.insufficientMaterial());
        board.setSquare(0, Utility.pieces[Utility.WHITE][Utility.K]);
        board.setSquare(0, Utility.pieces[Utility.BLACK][Utility.K]);
        board.setSquare(0, Utility.pieces[Utility.WHITE][Utility.B1]);
        assertEquals(true, board.insufficientMaterial());
    }

    @Test   // COMPLETE
    public void testPawnIsLegalMove() {
        Board board = new Board();
        Pawn pawn = (Pawn) board.getSquare(48);
        Pawn pawn2 = (Pawn) board.getSquare(12);
        Pawn whiteE = (Pawn) board.getSquare(52);

        Move move1 = new Move(pawn, pawn.getLocation(), 40); // Advance 1
        Move move2 = new Move(pawn, pawn.getLocation(), 32); // Advance 2
        Move move3 = new Move(pawn, pawn.getLocation(), 24); // Advance 3
        Move move4 = new Move(pawn, pawn.getLocation(), 41); // Move diagonally without capture
        Move move5 = new Move(pawn, pawn.getLocation(), 41, true); // Capture a piece
        Move move6 = new Move(pawn, pawn.getLocation(), 40); // Advance 1 with blocked square
        Move move7 = new Move(pawn, pawn.getLocation(), 32); // Advance 2 with blocked square
        Move move8 = new Move(pawn2, pawn2.getLocation(), 28);
        Move move9 = new Move(whiteE, whiteE.getLocation(), 36);

        assertEquals(true, pawn.isLegalMove(board, move1));
        assertEquals(true, pawn.isLegalMove(board, move2));
        assertEquals(false, pawn.isLegalMove(board, move3));
        assertEquals(false, pawn.isLegalMove(board, move4));
        board.setSquare(41, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, pawn.isLegalMove(board, move5));
        board.setSquare(40, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, pawn.isLegalMove(board, move6));
        board.setSquare(32, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, pawn.isLegalMove(board, move7));
        assertEquals(true, whiteE.isLegalMove(board, move9));
        board.move(move9);
        assertEquals(true, pawn2.isLegalMove(board, move8));
        //TODO: test capturing en passant
        //TODO: test promotion
    }

    @Test   // COMPLETE
    public void testKnightIsLegalMove() {
        Board board = new Board();
        Knight knight = (Knight) board.getSquare(57);

        Move move1 = new Move(knight, knight.getLocation(), 42); // Nc3 first move
        Move move2 = new Move(knight, knight.getLocation(), 51); // Nd2 first move
        Move move3 = new Move(knight, knight.getLocation(), 42, true); // Capture a piece Nc3

        assertEquals(true, knight.isLegalMove(board, move1)); // Nc3 first move
        assertEquals(false, knight.isLegalMove(board, move2)); // Nd2 first move
        board.setSquare(42, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, knight.isLegalMove(board, move3)); // Capture a piece Nc3

        Knight knight2 = (Knight) board.getSquare(62);
        Move move4 = new Move(knight2, knight2.getLocation(), 47);
        Move move5 = new Move(knight2, knight2.getLocation(), 45);
        assertEquals(true, knight2.isLegalMove(board, move4));
        assertEquals(true, knight2.isLegalMove(board, move5));

        //TODO: test move outside of board range
    }

    @Test   // COMPLETE
    public void testBishopIsLegalMove() {
        Board board = new Board();
        Bishop bishop = (Bishop) board.getSquare(58);
        board.clear();
        board.setSquare(58, bishop);

        Move move1 = new Move(bishop, bishop.getLocation(), 51); // Bc1-d2: true
        Move move2 = new Move(bishop, bishop.getLocation(), 44); // Bc1-e3: true
        Move move3 = new Move(bishop, bishop.getLocation(), 36); // Bc1-e4: false
        Move move4 = new Move(bishop, bishop.getLocation(), 28); // Bc1-e5: false
        Move move5 = new Move(bishop, bishop.getLocation(), 51, true); // Bc1-d2 captures: true
        Move move6 = new Move(bishop, bishop.getLocation(), 51); // Bc1-e3 with piece between on d2: false

        assertEquals(true, bishop.isLegalMove(board, move1));
        assertEquals(true, bishop.isLegalMove(board, move2));
        assertEquals(false, bishop.isLegalMove(board, move3));
        assertEquals(false, bishop.isLegalMove(board, move4));
        board.setSquare(51, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, bishop.isLegalMove(board, move5));
        assertEquals(false, bishop.isLegalMove(board, move6));

    }

    @Test   // COMPLETE
    public void testQueenIsLegalMove() {
        Board board = new Board();
        Queen queen = (Queen) board.getSquare(59);
        queen.setLocation(60);
        board.clear();
        board.setSquare(60, queen);

        Move move1 = new Move(queen, queen.getLocation(), 28); // Up 4: true
        Move move2 = new Move(queen, queen.getLocation(), 57); // Left 3: true
        Move move3 = new Move(queen, queen.getLocation(), 37); // Diagonal up right 2: false
        Move move4 = new Move(queen, queen.getLocation(), 27); // e1 - d5
        Move move5 = new Move(queen, queen.getLocation(), 51, true); // diagonal up left 1 captures: true
        Move move6 = new Move(queen, queen.getLocation(), 39); // diagonal up right 3 with piece between: false

        assertEquals(true, queen.isLegalMove(board, move1));
        assertEquals(true, queen.isLegalMove(board, move2));
        assertEquals(false, queen.isLegalMove(board, move3));
        assertEquals(false, queen.isLegalMove(board, move4));
        board.setSquare(51, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, queen.isLegalMove(board, move5));
        board.setSquare(46, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, queen.isLegalMove(board, move6));

        Board board2 = new Board();
        board2.setSquare(45, queen);
        queen.setLocation(45);
        Move move7 = new Move(queen, 45, 13, true);
        assertEquals(true, queen.isLegalMove(board2, move7));
    }

    @Test   // COMPLETE
    public void testSquaresBetween() {
        Board board = new Board();
        int size1 = Utility.squaresBetween(0, 32).size(); // Down: 3
        int size2 = Utility.squaresBetween(0, 7).size(); // Right: 6
        int size3 = Utility.squaresBetween(63, 58).size(); // Left: 4
        int size4 = Utility.squaresBetween(32, 0).size(); // Up: 3
        int size5 = Utility.squaresBetween(0, 27).size(); // Right Down: 2
        int size6 = Utility.squaresBetween(56, 28).size(); // Right Up: 3
        int size7 = Utility.squaresBetween(28, 56).size(); // Left Down: 3
        int size8 = Utility.squaresBetween(27, 0).size(); // Left Up: 2
        int size9 = Utility.squaresBetween(0, 1).size(); // Nowhere: 0

        assertEquals(3, size1);
        assertEquals(6, size2);
        assertEquals(4, size3);
        assertEquals(3, size4);
        assertEquals(2, size5);
        assertEquals(3, size6);
        assertEquals(3, size7);
        assertEquals(2, size8);
        assertEquals(0, size9);
    }

    @Test   // COMPLETE
    public void testRookIsLegalMove() {
        Board board = new Board();
        Rook rook = (Rook) board.getSquare(56);
        board.clear();
        board.setSquare(56, rook);

        Move move1 = new Move(rook, rook.getLocation(), 24); // Up 4: true
        Move move2 = new Move(rook, rook.getLocation(), 59); // Right 3: true
        Move move3 = new Move(rook, rook.getLocation(), 42); // Diagonal up right 2: false
        Move move4 = new Move(rook, rook.getLocation(), 49, true); // diagonal up right 1 captures: false
        Move move5 = new Move(rook, rook.getLocation(), 59, true); // Right 3 capture: true

        assertEquals(true, rook.isLegalMove(board, move1));
        assertEquals(true, rook.isLegalMove(board, move2));
        assertEquals(false, rook.isLegalMove(board, move3));
        board.setSquare(49, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, rook.isLegalMove(board, move4));
        board.setSquare(59, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, rook.isLegalMove(board, move5));
    }

    @Test   // TODO: Test castling queenside
    public void testKingIsLegalMove() {
        Board board = new Board();
        King king = (King) board.getSquare(60);
        board.clear();
        board.setSquare(60, king);

        //Normal moves
        Move move1 = new Move(king, king.getLocation(), 52); // Up 1: true
        Move move2 = new Move(king, king.getLocation(), 61); // Right 1: true
        Move move3 = new Move(king, king.getLocation(), 59); // Left 1: true
        Move move4 = new Move(king, king.getLocation(), 53); // Up Right 1: true
        Move move5 = new Move(king, king.getLocation(), 44); // Up 2: false
        //Move move6 = new Move(king, king.getLocation(), 62); // Right 2: false
        Move move7 = new Move(king, king.getLocation(), 52, true); // Up 1 capture: true
        Move move8 = new Move(king, king.getLocation(), 59, true); // Up Left 1 capture

        assertEquals(true, king.isLegalMove(board, move1));
        assertEquals(true, king.isLegalMove(board, move2));
        assertEquals(true, king.isLegalMove(board, move3));
        assertEquals(true, king.isLegalMove(board, move4));
        assertEquals(false, king.isLegalMove(board, move5));
        //assertEquals(false, king.isLegalMove(board, move6));
        board.setSquare(52, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, king.isLegalMove(board, move7));
        board.setSquare(59, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, king.isLegalMove(board, move8));

        //Castling kingside
        board.reset();
        Knight WN2 = (Knight) board.getSquare(62);
        Pawn WE = (Pawn) board.getSquare(52);
        Bishop WB2 = (Bishop) board.getSquare(61);
        Pawn BA = (Pawn) board.getSquare(8);
        board.move(new Move(WN2, WN2.getLocation(), 45));   //Nf3
        board.move(new Move(BA, BA.getLocation(), 16));     //a6
        board.move(new Move(WE, WE.getLocation(), 36));     //e4
        board.move(new Move(BA, BA.getLocation(), 24));     //a5
        board.move(new Move(WB2, WB2.getLocation(), 34));   //Bc4
        board.move(new Move(BA, BA.getLocation(), 32));     //a4
        Move castling = new Move(king, king.getLocation(), 62);
        System.out.println(king.canCastle(board, castling));
        //board.move(new Move(king, king.getLocation(), 62));
    }

    @Test   // TODO: UNFINISHED
    public void testStringToMove() {
        Board board = new Board();
        Move move1 = Utility.stringToMove("Nf3", Utility.WHITE);
        Move move2 = Utility.stringToMove("Nxf3", Utility.WHITE);
        Move move3 = Utility.stringToMove("Rae1", Utility.WHITE);
        Move move4 = Utility.stringToMove("Raxe1", Utility.WHITE);
        Move move5 = Utility.stringToMove("0-0", Utility.WHITE);
        Move move6 = Utility.stringToMove("0-0-0", Utility.WHITE);
    }

    @Test   // COMPLETE
    public void testGetRC() {
        assertEquals(0, Utility.getRC(0)[0]);
        assertEquals(0, Utility.getRC(0)[1]);
        assertEquals(7, Utility.getRC(63)[0]);
        assertEquals(7, Utility.getRC(63)[1]);
        assertEquals(3, Utility.getRC(28)[0]);
        assertEquals(4, Utility.getRC(28)[1]);
    }

    @Test
    public void testUpdateAllMoves() {
        Board board = new Board();
        board.updateAllMoves();
    }

    @Test   // COMPLETE
    public void testUpdateMoves() {
        Board board = new Board();
        Knight knight = (Knight) board.getSquare(57);
        knight.updateMoves(board);
        for (Move move : knight.getMoves()) {
            System.out.println(move.getEnd());
        }
    }

    @Test   // COMPLETE
    public void testBoardToString() {
        Board board = new Board();
        System.out.println(board.toString());
        board.clear();
        System.out.println(board.toString());
    }

    @Test   // COMPLETE
    public void testBoardRepetition() {
        Board board = new Board();
        assertEquals(false, board.boardRepetitionDraw());
        board.appendPreviousBoards();
        board.appendPreviousBoards();
        board.appendPreviousBoards();
        assertEquals(true, board.boardRepetitionDraw());
    }

    //TODO: test true case
    @Test
    public void testNoLegalMoves() {
        Board board = new Board();
        board.updateAllMoves();
        Move move1 = new Move(board.getSquare(52), 52, 36);
        Move move2 = new Move(board.getSquare(12), 12, 28);
        Move move3 = new Move(board.getSquare(62), 62, 45);
        Move move4 = new Move(board.getSquare(1), 1, 18);

        board.move(move1);
        board.move(move2);
        board.move(move3);
        board.move(move4);
        assertEquals(false, board.noLegalMoves(board.getTurn()));
    }

    @Test   //TODO: Test update moves, mslcopa...
    public void testMove() {
        Board board = new Board();
        Move move1 = new Move(board.getSquare(52), 52, 36);
        Move move2 = new Move(board.getSquare(12), 12, 28);
        Move move3 = new Move(board.getSquare(62), 62, 45);
        Move move4 = new Move(board.getSquare(1), 1, 18);
        board.move(move1);
        board.move(move2);
        board.move(move3);
        board.move(move4);
    }

    @Test   //TODO: test other checkmates
    public void testIsCheckMated() {
        Board board = new Board();
        assertEquals(false, board.isCheckMated(board.getTurn()));

        Move e4 = new Move(board.getSquare(52), 52, 36);
        board.move(e4);
        Move e5 = new Move(board.getSquare(12), 12, 28);
        board.move(e5);
        Move Bc4 = new Move(board.getSquare(61), 61, 34);
        board.move(Bc4);
        Move a4 = new Move(board.getSquare(8), 8, 24);
        board.move(a4);
        Move Qf3 = new Move(board.getSquare(59), 59, 45);
        board.move(Qf3);
        Move Nc6 = new Move(board.getSquare(1), 1, 18);
        board.move(Nc6);
        Move Qf7 = new Move(board.getSquare(45), 45, 13, true);
        board.move(Qf7);

        assertEquals(true, board.isCheckMated(board.getTurn()));
    }

    @Test   // COMPLETE
    public void testCanCaptureEnPassant() {
        Board board = new Board();
        Pawn whiteE = (Pawn) board.getSquare(52);
        Pawn blackA = (Pawn) board.getSquare(8);
        Pawn blackD = (Pawn) board.getSquare(11);


        board.move(new Move(whiteE, whiteE.getLocation(), 36));
        board.move(new Move(blackA, blackA.getLocation(), 16));

        Move ep1 = new Move(whiteE, whiteE.getLocation(), 21, true, "", true);
        Move ep2 = new Move(whiteE, whiteE.getLocation(), 29, true, "", true);
        assertEquals(false, whiteE.canCaptureEnPassant(board, ep1));
        assertEquals(false, whiteE.canCaptureEnPassant(board, ep2));

        board.move(new Move(whiteE, whiteE.getLocation(), 28));
        board.move(new Move(blackD, blackD.getLocation(), 27));

        Move ep3 = new Move(whiteE, whiteE.getLocation(), 19, true, "", true);
        assertEquals(true, whiteE.canCaptureEnPassant(board, ep3));
        assertEquals(true, whiteE.isLegalMove(board, ep3));

    }
    //TO TEST
    /*
     * public void testUndoMove
     * promotion
     */

}
