package chess;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChessTest {

    @Test
    public void testGameState() {
        // Test 1: in progress
        Board board = new Board();
        String gs1 = "in progress";
        board.setGameState(gs1);
        assertEquals("in progress", board.getGameState());
    }

    //TODO: Add a test to test for true
    @Test
    public void testInsufficientMaterial() {
        Board board = new Board();
        assertEquals(false, board.insufficientMaterial());
    }

    @Test
    public void testPawnIsLegalMove() {
        Board board = new Board();
        Pawn pawn = (Pawn) board.getSquare(48);
        assertEquals(true, pawn.isLegalMove(board, 40));
        assertEquals(true, pawn.isLegalMove(board, 32));
        assertEquals(false, pawn.isLegalMove(board, 24));
        assertEquals(false, pawn.isLegalMove(board, 41));
    }




}
