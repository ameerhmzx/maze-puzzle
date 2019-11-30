package layoutStrategies;

import layoutChanges.LayoutChanges;
import objects.Board;

public enum LayoutStrategy implements ILayoutStrategy {
    RECURSIVE_BACK_TRACK("Back Track (Recursive)") {
        public LayoutChanges layoutBoard(Board board) {
            return new RecursiveBackTrackLayoutStrategy().layoutBoard(board);
        }
    },
    SIMPLE_BACK_TRACK("Back Track (Stack)") {
        public LayoutChanges layoutBoard(Board board) {
            return new SimpleBackTrackLayoutStrategy().layoutBoard(board);
        }
    },
    PRIM_RANDOMIZATION("Prim's Randomization") {
        public LayoutChanges layoutBoard(Board board) {
            return new PrimRandomizationLayoutStrategy().layoutBoard(board);
        }
    },
    WILSON_MAZE("Wilson's Maze Algorithm") {
        public LayoutChanges layoutBoard(Board board) {
            return new WilsonLayoutStrategy().layoutBoard(board);
        }
    },
    ELLER_MAZE_ALGORITHM("Eller's Maze Algorithm") {
        public LayoutChanges layoutBoard(Board board) {
            return new EllerLayoutStrategy().layoutBoard(board);
        }
    };

    private String name;

    LayoutStrategy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
