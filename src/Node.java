import java.util.ArrayList;
import java.util.List;

public class Node {

    // REPRESENTS THE STATE THAT WE ARE IN

    private boolean isLeaf;
    private int value;
    private Player player;
    private Player[][] board;

    public static final int MAX_DEPTH = 16;

    public Node(int value, Player player, Player[][] board) {
        this.isLeaf = false;
        this.value = value;
        this.player = player;
        this.board = board;
    }

    public Node(String maxOrMin) {
        if (maxOrMin.equalsIgnoreCase("Max")) {
            this.player = Player.MAX;
        } else {
            this.player = Player.MIN;
        }

        this.isLeaf = false;
        this.value = 0;
        this.board = new Player[3][3]; // Empty board
    }

    public Player getBoard(int row, int col) {
        return board[row][col];
    }


    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean b) {
        this.isLeaf = b;
    }

    public int getValue() {
        return this.value;
    }

    // TODO: return an arraylist of children
    public static List<Node> generateChildren(Node node) {
        List<Node> children = new ArrayList<>();
        Player currentPlayer = node.player;

        // Placeholder for the actual logic to check if we're still placing or moving pieces
        boolean placementPhase = checkPlacementPhase(node);

        if (placementPhase) {
            for (int row = 0; row < node.board.length; row++) {
                for (int col = 0; col < node.board[row].length; col++) {
                    if (node.board[row][col] == null) {
                        // If the position is empty, place the current player's piece
                        Node child = copyNodeWithPlacement(node, row, col);
                        children.add(child);
                        // child.printBoard();
                    }
                }
            }
        } else {
            // Handle piece movement logic
            for (int row = 0; row < node.board.length; row++) {
                for (int col = 0; col < node.board[row].length; col++) {
                    if (node.board[row][col] == currentPlayer) {
                        // Generate moves for this piece
                        List<int[]> moves = getValidMoves(node, row, col);
                        for (int[] move : moves) {
                            Node child = copyNodeWithMove(node, row, col, move[0], move[1]);
                            children.add(child);
                        }
                    }
                }
            }
        }

        return children;
    }

    // Helper method to print the board of a node
    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print((board[i][j] == null ? "-" : board[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    private static boolean checkPlacementPhase(Node node) {
        int piecesCount = 0;
        for (int i = 0; i < node.board.length; i++) {
            for (int j = 0; j < node.board[i].length; j++) {
                if (node.board[i][j] != null) {
                    piecesCount++;
                }
            }
        }
        // Assuming each player has 3 pieces and there are 2 players
        return piecesCount < 6; // 6 pieces in total mean all pieces have been placed
    }


    private static Node copyNodeWithPlacement(Node originalNode, int row, int col) {
        // Deep copy the board from the original node.
        Player[][] copiedBoard = new Player[3][3];
        for (int i = 0; i < originalNode.board.length; i++) {
            System.arraycopy(originalNode.board[i], 0, copiedBoard[i], 0, originalNode.board[i].length);
        }

        // Place the current player's piece at the specified position.
        copiedBoard[row][col] = originalNode.player;

        // Create a new Node with the updated board and switch the player.
        Node newNode = new Node(originalNode.getValue(), Player.opponent(originalNode.player), copiedBoard);

        return newNode;
    }

    private static List<int[]> getValidMoves(Node node, int row, int col) {
        List<int[]> moves = new ArrayList<>();

        // Assuming a simple grid where each piece can move to an adjacent spot
        // in any of the 4 directions (up, down, left, right) if it's empty
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        for (int[] d : directions) {
            int newRow = row + d[0];
            int newCol = col + d[1];

            // Check boundaries and if the new position is empty
            if (newRow >= 0 && newRow < node.board.length && newCol >= 0 && newCol < node.board[newRow].length &&
                    node.board[newRow][newCol] == null) {

                moves.add(new int[]{newRow, newCol});
            }
        }

        return moves;
    }

    private static Node copyNodeWithMove(Node originalNode, int fromRow, int fromCol, int toRow, int toCol) {
        // First, copy the board from the original node.
        Player[][] copiedBoard = new Player[3][3];
        for (int i = 0; i < originalNode.board.length; i++) {
            for (int j = 0; j < originalNode.board[i].length; j++) {
                copiedBoard[i][j] = originalNode.board[i][j];
            }
        }

        // Apply the move on the copied board.
        copiedBoard[toRow][toCol] = copiedBoard[fromRow][fromCol]; // Move the piece.
        copiedBoard[fromRow][fromCol] = null; // Clear the original position.

        // Create a new Node with the updated board.es.
        Node newNode = new Node(originalNode.getValue(), originalNode.player, copiedBoard);

        // Depending on the game rules, you may also need to update the player turn, check if the move resulted in a win, etc.
        // newNode.setPlayer(switchPlayer(originalNode.getPlayer()));
        return newNode;
    }



    public static boolean isLeafNode(Node node, int depth) {
        // Check if the depth has reached the lookahead depth
        // Check if the game has reached a terminal state
        return depth == 0 || isTerminalState(node);
    }

    private static boolean isTerminalState(Node node) {
        Player[][] board = node.board;

        // Check if Max (Player.MAX) has three in a row
        if ((board[0][0] == Player.MAX && board[0][1] == Player.MAX && board[0][2] == Player.MAX) ||
                (board[1][0] == Player.MAX && board[1][1] == Player.MAX && board[1][2] == Player.MAX) ||
                (board[2][0] == Player.MAX && board[2][1] == Player.MAX && board[2][2] == Player.MAX) ||
                (board[0][0] == Player.MAX && board[1][0] == Player.MAX && board[2][0] == Player.MAX) ||
                (board[0][1] == Player.MAX && board[1][1] == Player.MAX && board[2][1] == Player.MAX) ||
                (board[0][2] == Player.MAX && board[1][2] == Player.MAX && board[2][2] == Player.MAX) ||
                (board[0][0] == Player.MAX && board[1][1] == Player.MAX && board[2][2] == Player.MAX) ||
                (board[0][2] == Player.MAX && board[1][1] == Player.MAX && board[2][0] == Player.MAX)) {
            return true;
        }

        // Check if Min (Player.MIN) has three in a row
        if ((board[0][0] == Player.MIN && board[0][1] == Player.MIN && board[0][2] == Player.MIN) ||
                (board[1][0] == Player.MIN && board[1][1] == Player.MIN && board[1][2] == Player.MIN) ||
                (board[2][0] == Player.MIN && board[2][1] == Player.MIN && board[2][2] == Player.MIN) ||
                (board[0][0] == Player.MIN && board[1][0] == Player.MIN && board[2][0] == Player.MIN) ||
                (board[0][1] == Player.MIN && board[1][1] == Player.MIN && board[2][1] == Player.MIN) ||
                (board[0][2] == Player.MIN && board[1][2] == Player.MIN && board[2][2] == Player.MIN) ||
                (board[0][0] == Player.MIN && board[1][1] == Player.MIN && board[2][2] == Player.MIN) ||
                (board[0][2] == Player.MIN && board[1][1] == Player.MIN && board[2][0] == Player.MIN)) {
            return true;
        }

        // Check if there are available moves left
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    return false; // There are still available moves left
                }
            }
        }

        return true; // No available moves left, game is over
    }

    public void applyMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Check if the move is valid (e.g., within the board bounds and empty destination)
        if (isValidMove(fromRow, fromCol, toRow, toCol)) {
            // Move the piece
            this.board[toRow][toCol] = this.board[fromRow][fromCol];
            this.board[fromRow][fromCol] = null;
        } else {
            // Handle invalid move (e.g., throw an exception or print an error message)
            System.err.println("Invalid move!");
        }
    }

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Check if the move is within the board bounds and the destination is empty
        return fromRow >= 0 && fromRow < 3 && fromCol >= 0 && fromCol < 3 &&
                toRow >= 0 && toRow < 3 && toCol >= 0 && toCol < 3 &&
                this.board[fromRow][fromCol] != null && this.board[toRow][toCol] == null;
    }
}
