# Heuristic MiniMax Game Solver

This project implements a two-player, turn-based game on a 3x3 grid using a **heuristic MiniMax algorithm** with **alpha-beta pruning** for optimized decision-making.

## 🧩 Game Overview

- The board is a **3x3 grid**, where each cell can be:
  - `null` — empty
  - `Player.MAX` — a piece for Max
  - `Player.MIN` — a piece for Min
- The game progresses through **placement** and **movement** phases.
- Players take turns placing or moving their pieces according to the game rules.

## 🔍 Project Structure

- **`Driver.java`**  
  Entry point for running the program. Executes the MiniMax algorithm and prints the board states and results.

- **`MiniMaxAlgorithm.java`**  
  Contains the `heuristicMiniMax` function with **alpha-beta pruning**. Also includes a win-checking method to evaluate game outcomes.

- **`Player.java`**  
  Enum to represent players (`MAX`, `MIN`) or empty (`null`) for better readability and board representation.

- **`Node.java`**  
  Represents a game state and includes:
  - 2D array board of `Player` objects
  - Functions for:
    - Printing the board
    - Generating valid children (next possible moves)
    - Checking terminal/leaf status
    - Executing moves

## ⚙️ Features

- Efficient game tree traversal using **alpha-beta pruning**
- Clear board display for debugging and testing
- Dynamic child generation based on current player and game phase

## ▶️ Running the Project

To run the project:
1. Open in any Java IDE.
2. Run `Driver.java`.
3. View printed output of the board states and decision steps.
