package layoutStrategies;

import objects.Board;

public enum LayoutStrategy implements ILayoutStrategy {
    RECURSIVE_BACK_TRACK("Back Track (Recursive)") {
        public void layoutBoard(Board board) {
            new RecursiveBackTrackLayoutStrategy().layoutBoard(board);
        }
    },
    SIMPLE_BACK_TRACK("Back Track (Stack)") {
        public void layoutBoard(Board board) {
            new SimpleBackTrackLayoutStrategy().layoutBoard(board);
        }
    },
    PRIM_RANDOMIZATION("Prim's Randomization") {
        public void layoutBoard(Board board) {
            new PrimRandomizationLayoutStrategy().layoutBoard(board);
        }
    },
    WILSON_MAZE("Wilson's Maze Algorithm") {
        public void layoutBoard(Board board) {
            new WilsonLayoutStrategy().layoutBoard(board);
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
