import java.util.concurrent.TimeUnit;

public class Driver {
    public static void main(String[] args) {
        // Create an initial game state
        Node maxStart = new Node("Max");
        Node minStart = new Node("Min");

        // Run the algorithm twice: once with Max starting and once with Min starting
        long maxStartingTime = runAlgorithm(maxStart, "Max");
        long minStartingTime = runAlgorithm(minStart, "Min");

        // Compute the average time of the two runs
        long averageTime = (maxStartingTime + minStartingTime) / 2;

        System.out.println("Average execution time: " + averageTime + " milliseconds");
    }

    public static long runAlgorithm(Node initialNode, String maxOrMin) {
        // Create an instance of the MiniMaxAlgorithm
        MiniMaxAlgorithm algorithm = new MiniMaxAlgorithm();

        // Start measuring time
        long startTime = System.nanoTime();

        // Run the algorithm
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        boolean max = maxOrMin.equalsIgnoreCase("max");
        algorithm.heuristicAlphaBeta(initialNode, Node.MAX_DEPTH, max, alpha, beta);

        // End measuring time
        long endTime = System.nanoTime();

        // Calculate execution time
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

        System.out.println("Execution time for " + maxOrMin + ": " + elapsedTimeInMillis + " milliseconds");

        return elapsedTimeInMillis;
    }
}