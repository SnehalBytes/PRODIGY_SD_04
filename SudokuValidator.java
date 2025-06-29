package task4;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class SudokuValidator extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] grid = new JTextField[SIZE][SIZE];
    private int[][] puzzle = new int[SIZE][SIZE];
    private int[][] solution = new int[SIZE][SIZE];
    private JLabel statusBar = new JLabel("🎉 Welcome to Sudoku!");

    enum Difficulty { EASY, MEDIUM, HARD, EXPERT }

    public SudokuValidator(Difficulty level) {
        setTitle("Sudoku Validator - Modern UI");
        setSize(600, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        generatePuzzle(level);

        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        boardPanel.setBackground(Color.DARK_GRAY);

        Font font = new Font("Consolas", Font.BOLD, 20);
        Color bg = new Color(30, 30, 30);
        Color fg = new Color(220, 220, 220);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(font);
                cell.setBackground(bg);
                cell.setForeground(fg);
                cell.setCaretColor(fg);

                int top = (row % 3 == 0) ? 3 : 1;
                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = (row == SIZE - 1) ? 3 : 1;
                int right = (col == SIZE - 1) ? 3 : 1;
                cell.setBorder(new MatteBorder(top, left, bottom, right, Color.LIGHT_GRAY));

                if (puzzle[row][col] != 0) {
                    cell.setText(String.valueOf(puzzle[row][col]));
                    cell.setEditable(false);
                    cell.setBackground(new Color(60, 60, 60));
                } else {
                    int r = row, c = col;
                    cell.addActionListener(e -> validateCell(r, c));
                }

                grid[row][col] = cell;
                boardPanel.add(cell);
            }
        }

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        controlPanel.setBackground(Color.DARK_GRAY);

        JButton reset = new JButton("🔄 Reset");
        JButton reveal = new JButton("🕵️ Show Solution");

        reset.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new SudokuValidator(level));
        });

        reveal.addActionListener(e -> showSolution());

        controlPanel.add(reset);
        controlPanel.add(reveal);

        statusBar.setOpaque(true);
        statusBar.setBackground(Color.BLACK);
        statusBar.setForeground(Color.CYAN);
        statusBar.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void validateCell(int row, int col) {
        try {
            String input = grid[row][col].getText().trim();
            if (input.isEmpty()) return;

            int value = Integer.parseInt(input);
            if (value == solution[row][col]) {
                grid[row][col].setBackground(new Color(34, 139, 34)); // Green
                statusBar.setText("✅ Correct!");
            } else {
                grid[row][col].setBackground(new Color(139, 0, 0)); // Red
                statusBar.setText("❌ Incorrect, try again.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter numbers 1–9 only.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showSolution() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                grid[i][j].setText(String.valueOf(solution[i][j]));
                grid[i][j].setBackground(new Color(70, 70, 70));
                grid[i][j].setEditable(false);
            }
        statusBar.setText("🎉 Full solution revealed!");
    }

    private void generatePuzzle(Difficulty level) {
        solveSudoku(solution, 0, 0);
        for (int i = 0; i < SIZE; i++)
            System.arraycopy(solution[i], 0, puzzle[i], 0, SIZE);

        int holes = switch (level) {
            case EASY -> 35;
            case MEDIUM -> 45;
            case HARD -> 55;
            case EXPERT -> 64;
        };

        while (holes > 0) {
            int r = (int)(Math.random() * SIZE);
            int c = (int)(Math.random() * SIZE);
            if (puzzle[r][c] != 0) {
                puzzle[r][c] = 0;
                holes--;
            }
        }
    }

    private boolean solveSudoku(int[][] board, int row, int col) {
        if (row == SIZE) return true;
        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col == SIZE - 1) ? 0 : col + 1;
        if (board[row][col] != 0) return solveSudoku(board, nextRow, nextCol);

        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board, nextRow, nextCol)) return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++)
            if (board[row][i] == num || board[i][col] == num) return false;

        int boxRow = (row / 3) * 3, boxCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[boxRow + i][boxCol + j] == num) return false;

        return true;
    }

    public static void main(String[] args) {
        String[] levels = { "Easy", "Medium", "Hard", "Expert" };
        int choice = JOptionPane.showOptionDialog(
                null, "Select Difficulty 🎯", "Sudoku Difficulty",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, levels, levels[1]);

        Difficulty level = switch (choice) {
            case 0 -> Difficulty.EASY;
            case 1 -> Difficulty.MEDIUM;
            case 2 -> Difficulty.HARD;
            case 3 -> Difficulty.EXPERT;
            default -> Difficulty.MEDIUM;
        };

        SwingUtilities.invokeLater(() -> new SudokuValidator(level));
    }
}