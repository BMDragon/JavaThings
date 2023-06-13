import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.AbstractMap.SimpleEntry;

public class StonePuzzle {
    private static final byte[] START_BOARD = { 2, 2, 2, 1, 1, 1, 2, 2, 2,
            2, 2, 2, 1, 0, 1, 2, 2, 2,
            2, 1, 1, 1, 0, 1, 1, 1, 2,
            2, 1, 1, 1, 1, 1, 1, 1, 2,
            2, 1, 1, 1, 1, 1, 1, 1, 2,
            2, 2, 2, 1, 1, 1, 2, 2, 2,
            2, 2, 2, 1, 1, 1, 2, 2, 2 };
    private static final byte[] SOLVED_BOARD = { 2, 2, 2, 0, 0, 0, 2, 2, 2,
            2, 2, 2, 0, 0, 0, 2, 2, 2,
            2, 0, 0, 0, 0, 0, 0, 0, 2,
            2, 0, 0, 0, 1, 0, 0, 0, 2,
            2, 0, 0, 0, 0, 0, 0, 0, 2,
            2, 2, 2, 0, 0, 0, 2, 2, 2,
            2, 2, 2, 0, 0, 0, 2, 2, 2 };
    private static final int GRID_SIZE = 7;
    private static final int NUM_COLUMNS = START_BOARD.length / GRID_SIZE; // 9
    private static Queue<SimpleEntry<byte[], ArrayList<String>>> queue;
    private static HashSet<byte[]> exploredStates;
    private static ArrayList<ArrayList<String>> paths;

    public static void main(String[] args) {
        ArrayList<String> startingMove = new ArrayList<>();
        startingMove.add("13D");
        SimpleEntry<byte[], ArrayList<String>> start = new SimpleEntry<>(START_BOARD, startingMove);
        queue = new LinkedList<>();
        queue.add(start);
        exploredStates = new HashSet<>();

        ArrayList<String> currentPath, nextPath, listPossibleMoves;
        byte[] currentState, nextState;
        int depth = 1, setSize;
        while (!queue.isEmpty()) {
            setSize = exploredStates.size();
            if (setSize % 100000 < 70) {
                System.out.print(" " + setSize);
            }
            SimpleEntry<byte[], ArrayList<String>> SimpleEntry = queue.poll();
            currentPath = SimpleEntry.getValue();
            currentState = SimpleEntry.getKey();
            if (currentPath.size() != depth) {
                depth = currentPath.size();
                System.out.print("\033[H\033[2J Depth: " + depth + "/31 ");
                exploredStates.removeAll(exploredStates);
            }
            for (int i = 3; i < currentState.length - 3; i++) {
                if (currentState[i] != 1) {
                    continue;
                }
                listPossibleMoves = possibleMoves(currentState, i);
                for (String s : listPossibleMoves) {
                    nextState = getNextState(currentState, s);
                    if (isSameState(nextState, SOLVED_BOARD)) {
                        currentPath.add(s);
                        paths.add(currentPath);
                    } else {
                        if (!exploredStates.contains(nextState)) {
                            nextPath = new ArrayList<>(currentPath);
                            nextPath.add(s);
                            queue.add(new SimpleEntry<byte[], ArrayList<String>>(nextState, nextPath));
                            addIsomorphisms(nextState);
                        }
                    }
                }
            }
        }
        displayPaths(paths);
    }

    private static ArrayList<String> possibleMoves(byte[] state, int index) {
        assert state[index] == 1 : " No piece at index " + index;
        ArrayList<String> ret = new ArrayList<>();
        if (index > 20 && state[index - NUM_COLUMNS] == 1 && state[index - 2 * NUM_COLUMNS] == 0) {
            ret.add("" + index + "U");
        } else if (index < 42 && state[index + NUM_COLUMNS] == 1 && state[index + 2 * NUM_COLUMNS] == 0) {
            ret.add("" + index + "D");
        } else if (state[index + 1] == 1 && state[index + 2] == 0) {
            ret.add("" + index + "R");
        } else if (state[index - 1] == 1 && state[index - 2] == 0) {
            ret.add("" + index + "L");
        }
        return ret;
    }

    private static byte[] getNextState(byte[] state, String move) {
        byte[] next = Arrays.copyOf(state, state.length);
        int index = Integer.parseInt(move.substring(0, move.length() - 1));
        char direction = move.charAt(move.length() - 1);
        if (direction == 'U') {
            next[index - NUM_COLUMNS] = 0;
            next[index - 2 * NUM_COLUMNS] = 1;
        } else if (direction == 'D') {
            next[index + NUM_COLUMNS] = 0;
            next[index + 2 * NUM_COLUMNS] = 1;
        } else if (direction == 'R') {
            next[index + 1] = 0;
            next[index + -2] = 1;
        } else if (direction == 'L') {
            next[index - 1] = 0;
            next[index - 2] = 1;
        }
        next[index] = 0;
        return next;
    }

    // Has reflection and rotational symmetry, so is the D_4 dihedral group.
    // Therefore must check state against 4 rotations and 4 reflected rotations.
    private static void addIsomorphisms(byte[] a) {
        byte[] copyOfA = Arrays.copyOf(a, a.length);
        for (int i = 0; i < 4; i++) {
            byte[] reflected = reflectState(copyOfA);
            exploredStates.add(reflected);
            exploredStates.add(copyOfA);
            copyOfA = rotateState(copyOfA);
        }
    }

    // Rotates middle 7 columns 90 degrees counter-clockwise
    private static byte[] rotateState(byte[] state) {
        byte[] ret = new byte[GRID_SIZE * NUM_COLUMNS];
        for (int i = 0; i < GRID_SIZE; i++) {
            ret[i * NUM_COLUMNS] = state[i * NUM_COLUMNS];
            for (int j = 0; j < GRID_SIZE; j++) {
                ret[i * NUM_COLUMNS + j + 1] = state[(j + 1) * NUM_COLUMNS - i - 2];
            }
            ret[(i + 1) * NUM_COLUMNS - 1] = state[(i + 1) * NUM_COLUMNS - 1];
        }
        return ret;
    }

    // Reflect across the vertical axis
    private static byte[] reflectState(byte[] state) {
        byte[] ret = new byte[GRID_SIZE * NUM_COLUMNS];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = state[((i / NUM_COLUMNS) + 1) * NUM_COLUMNS - (i % NUM_COLUMNS + 1)];
        }
        return ret;
    }

    private static boolean isSameState(byte[] a, byte[] b) {
        boolean same = true;
        for (int i = 3; i < a.length && same; i++) {
            same &= a[i] == b[i];
        }
        return same;
    }

    private static void displayPaths(ArrayList<ArrayList<String>> paths) {
        ArrayList<String> innerPath;
        for (int i = 0; i < paths.size(); i++) {
            System.out.println("");
            innerPath = paths.get(i);
            for (int j = 0; j < innerPath.size(); j++) {
                System.out.print(innerPath.get(j) + ", ");
            }
            System.out.print("\b\b");
        }
    }
}
