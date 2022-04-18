package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final char X = 'X';
        final char O = 'O';
        final String clearField = "_________";

        char[] fieldState = clearField.toCharArray();
        drawField(fieldState);

        char ch = X;
        while (true) {
            fieldState = changeField(fieldState, ch);
            drawField(fieldState);

            String gameState = gameState(fieldState);
            if (gameState.equals("X wins") || gameState.equals("O wins") || gameState.equals("Draw")) {
                System.out.println(gameState);
                break;
            }

            if (ch == X) {
                ch = O;
            } else {
                ch = X;
            }
        }
    }

    public static char[] changeField(char[] arr, char elem) {
        Scanner scanner = new Scanner(System.in);

        int index1 = 0;
        int index2 = 0;
        String[] line;


        while (true) {
            System.out.print("Enter the coordinates: ");
            line = scanner.nextLine().split(" ");
            try {
                index1 = Integer.parseInt(line[0]);
                index2 = Integer.parseInt(line[1]);
            } catch (NumberFormatException ex) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (index1 < 1 || index2 > 3 || index1 > 3 || index2 < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (isFreeIndex(arr, index1, index2) == -1) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                break;
            }
        }

        arr[isFreeIndex(arr, index1, index2)] = elem;
        return arr;
    }

    public static int isFreeIndex(char[] arr, int index1, int index2) {
        /*
        returns -1, if the space in the string represented by the array is occupied
        returns a number from 0 to the length of the string (represented by an array) -1,
        if that space is free in the string
         */
        final int size = 3;
        int k = 0;
        algo:
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size && k < size * size; j++, k++) {
                if (i == index1 - 1 && j == index2 - 1) {
                    if (arr[k] == '_') {
                        break algo;
                    } else {
                        k = -1;
                        break algo;
                    }
                }
            }
        }

        return k;
    }

    public static void drawField(char[] arr) {
        System.out.println("---------");
        System.out.printf("| %c %c %c |%n", arr[0], arr[1], arr[2]);
        System.out.printf("| %c %c %c |%n", arr[3], arr[4], arr[5]);
        System.out.printf("| %c %c %c |%n", arr[6], arr[7], arr[8]);
        System.out.println("---------");
    }

    public static String gameState(char[] fieldState) {
        final int size = 3;

        char[][] lines = new char[size][size];
        char[][] columns = new char[size][size];
        char[][] diagonals = new char[size][size];
        int k = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size && k < size * size; j++, k++) {
                lines[i][j] = fieldState[k];
                columns[j][i] = lines[i][j];

                if (i == j) {
                    diagonals[0][j] = fieldState[k];
                }
                if (i + j == size - 1) {
                    diagonals[1][i] = fieldState[k];
                    diagonals[2][i] = fieldState[k]; //supporting array
                }
            }
        }


        int countWhitespace = countOfOccurrences(fieldState, '_');
        boolean haveRowO = threeInRow(lines, columns, diagonals, 'O');
        boolean haveRowX = threeInRow(lines, columns, diagonals, 'X');


        if (haveRowX) {
            return "X wins";
        } else if (haveRowO) {
            return "O wins";
        } else if (countWhitespace == 0) {
            return "Draw";
        }
        return "Game not finished";
    }

    public static boolean threeInRow(char[][] lines, char[][] columns, char[][] diagonals, char elem) {
        for (int i = 0; i < lines.length; i++) {
            if (areSameAndEqual(lines[i], elem) ||
                    areSameAndEqual(columns[i], elem) ||
                    areSameAndEqual(diagonals[i], elem)) {
                return true;
            }
        }
        return false;
    }

    public static boolean areSameAndEqual(char[] elements, char elem) {
        for (char ch : elements) {
            if (ch != elem) {
                return false;
            }
        }
        return true;
    }

    public static int countOfOccurrences(char[] arr, char elem) {
        int count = 0;
        for (char ch : arr) {
            if (ch == elem) {
                count++;
            }
        }
        return count;
    }
}
