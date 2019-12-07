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
    WILSON_MAZE("Wilson's Maze") {
        public LayoutChanges layoutBoard(Board board) {
            return new WilsonLayoutStrategy().layoutBoard(board);
        }
    },
    ELLER_MAZE_ALGORITHM("Eller's Maze") {
        public LayoutChanges layoutBoard(Board board) {
            return new EllerLayoutStrategy().layoutBoard(board);
        }
    },
    ALDOUS_BRODER("Aldou'ss Border") {
        public LayoutChanges layoutBoard(Board board) {
            return new AldousBroderLayoutStrategy().layoutBoard(board);
        }
    },
    BINARY_TREE("Binary Tree") {
        public LayoutChanges layoutBoard(Board board) {
            return new BinaryTreeLayoutStrategy().layoutBoard(board);
        }
    },
    SIDEWINDER("Sidewinder's Maze") {
        public LayoutChanges layoutBoard(Board board) {
            return new SidewinderLayoutStrategy().layoutBoard(board);
        }
    },
    KRUSKAL_MAZE("Kruskal's Maze") {
        public LayoutChanges layoutBoard(Board board) {
            return new KruskalMazeLayoutStrategy().layoutBoard(board);
        }
    },
    HUNT_AND_KILL("Hunt and Kill") {
        public LayoutChanges layoutBoard(Board board) {
            return new HuntAndKillLayoutStrategy().layoutBoard(board);
        }
    };

    private String name;

    LayoutStrategy(String name) {
        this.name = name;
    }

    public static LayoutStrategy getFromName(String name) {
        for (LayoutStrategy strategy : LayoutStrategy.values()) {
            if (strategy.getName().equals(name))
                return strategy;
        }
        throw new IllegalArgumentException(name);
    }

    public String getName() {
        return name;
    }
}
