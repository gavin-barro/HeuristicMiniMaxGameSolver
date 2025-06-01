public enum Player {

    // MAX: represents the maximizing player
    // MIN: represents the minimizing player
    MAX, MIN;

    // Method to get the opponent of a player
    public static Player opponent(Player player) {
        return player == MAX ? MIN : MAX;
    }
}