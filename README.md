# Sudoku Validator - Modern UI

🎯 A sleek Java Swing-based Sudoku game with real-time validation, multiple difficulty levels, and a clean, modern interface.

---

## Project Overview

Sudoku Validator is a desktop Sudoku puzzle game implemented in Java using Swing.  
It dynamically generates Sudoku puzzles of varying difficulty levels (Easy, Medium, Hard, Expert), lets users input their answers, and validates each entry instantly by comparing it with the solution.  
Features include puzzle reset, solution reveal, and a stylish dark-themed UI for an enjoyable user experience.

---

## Features

- Generates Sudoku puzzles programmatically with guaranteed solutions.
- Four difficulty levels: Easy, Medium, Hard, Expert.
- Real-time cell validation with color feedback (green for correct, red for incorrect).
- Reset puzzle button to start fresh.
- Reveal full solution option.
- Modern dark theme with clear grid borders and responsive UI.

---

## Project Structure

- **SudokuValidator.java**  
  Contains the entire game logic: puzzle generation, Sudoku solver, UI setup with Swing components, and event handling.

---

## Prerequisites

- Java Development Kit (JDK) 8 or higher installed.
- An IDE like IntelliJ IDEA, Eclipse, or simple command line to compile and run.

---

## How to Run

### Using Command Line

1. Clone or download the repository.

2. Navigate to the project directory containing `SudokuValidator.java`.

3. Compile the Java source file:

   ```bash
   javac task4/SudokuValidator.java

