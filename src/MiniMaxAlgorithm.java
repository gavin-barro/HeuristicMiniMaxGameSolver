public class MiniMaxAlgorithm {

    private static final int MAX_WIN_VALUE = 8;
    private static final int MIN_WIN_VALUE = -8;
    private static final int DRAW = 0;

    // Three pieces in a row, it becomes a leaf node and 16 ply look ahead, becomes a leaf node if we are at depth 16

    public int generateHeuristicMiniMax(Node node, int depth, boolean isMaxNode) {
        if (Node.isLeafNode(node, depth)) {
            return heuristic(node);
        }

        int value;
        if (isMaxNode) {
            value = Integer.MIN_VALUE;
            for (Node child : Node.generateChildren(node)) {
                value = Math.max(value, generateHeuristicMiniMax(child, depth - 1, false));
            }
        } else {
            value = Integer.MAX_VALUE;
            for (Node child : Node.generateChildren(node)) {
                value = Math.min(value, generateHeuristicMiniMax(child, depth - 1, true));
            }
        }
        return value;
    }

    private int heuristic(Node node) {
        int maxPossibleWins = countPossibleWins(node, Player.MAX);
        int minPossibleWins = countPossibleWins(node, Player.MIN);
        int difference = maxPossibleWins - minPossibleWins;

        if (difference > 0) {
            return MAX_WIN_VALUE;
        } else if (difference < 0) {
            return MIN_WIN_VALUE;
        } else {
            return DRAW;
        }
    }

    private static int countPossibleWins(Node node, Player player) {
        int maxPossibleWins = 0;
        int minPossibleWins = 0;

        // Count possible wins for rows
        for (int row = 0; row < 3; row++) {
            int maxCount = 0;
            int minCount = 0;
            for (int column = 0; column < 3; column++) {
                if (node.getBoard(row, column) == player) {
                    maxCount++;
                } else if (node.getBoard(row, column) == Player.opponent(player)) {
                    minCount++;
                }
            }
            if (minCount == 0 && maxCount > 0) {
                maxPossibleWins++;
            } else if (maxCount == 0 && minCount > 0) {
                minPossibleWins++;
            }
        }

        // Count possible wins for columns
        for (int column = 0; column < 3; column++) {
            int maxCount = 0;
            int minCount = 0;
            for (int row = 0; row < 3; row++) {
                if (node.getBoard(row, column) == player) {
                    maxCount++;
                } else if (node.getBoard(row, column) == Player.opponent(player)) {
                    minCount++;
                }
            }
            if (minCount == 0 && maxCount > 0) {
                maxPossibleWins++;
            } else if (maxCount == 0 && minCount > 0) {
                minPossibleWins++;
            }
        }

        // Count possible wins for diagonals
        int maxDiagonal1 = 0;
        int minDiagonal1 = 0;
        int maxDiagonal2 = 0;
        int minDiagonal2 = 0;
        for (int diag = 0; diag < 3; diag++) {
            if (node.getBoard(diag, diag) == player) {
                maxDiagonal1++;
            } else if (node.getBoard(diag, diag) == Player.opponent(player)) {
                minDiagonal1++;
            }
            if (node.getBoard(diag, 2 - diag) == player) {
                maxDiagonal2++;
            } else if (node.getBoard(diag, 2 - diag) == Player.opponent(player)) {
                minDiagonal2++;
            }
        }
        if (minDiagonal1 == 0 && maxDiagonal1 > 0) {
            maxPossibleWins++;
        } else if (maxDiagonal1 == 0 && minDiagonal1 > 0) {
            minPossibleWins++;
        }
        if (minDiagonal2 == 0 && maxDiagonal2 > 0) {
            maxPossibleWins++;
        } else if (maxDiagonal2 == 0 && minDiagonal2 > 0) {
            minPossibleWins++;
        }

        return player == Player.MAX ? maxPossibleWins : minPossibleWins;
    }


    public int heuristicAlphaBeta(Node node, int depth, boolean isMaxNode, int alpha, int beta) {
        if (Node.isLeafNode(node, depth)) {
            return heuristic(node);
        }

        int value;
        if (isMaxNode) {
            value = Integer.MIN_VALUE;
            for (Node child : Node.generateChildren(node)) {
                value = Math.max(value, heuristicAlphaBeta(child, depth - 1, false, alpha, beta));
                alpha = Math.max(alpha, value);
                if (alpha >= beta) {
                    break; // Beta cut-off
                }
            }
        } else {
            value = Integer.MAX_VALUE;
            for (Node child : Node.generateChildren(node)) {
                value = Math.min(value, heuristicAlphaBeta(child, depth - 1, true, alpha, beta));
                beta = Math.min(beta, value);
                if (beta <= alpha) {
                    break; // Alpha cut-off
                }
            }
        }
        return value;
    }
}
