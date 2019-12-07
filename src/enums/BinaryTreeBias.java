package enums;

public enum BinaryTreeBias {
    TOP_RIGHT(true, true),
    TOP_LEFT(false, true),
    BOTTOM_RIGHT(true, false),
    BOTTOM_LEFT(false, false);

    public boolean rightBias;
    public boolean topBias;

    BinaryTreeBias(boolean rightBias, boolean topBias) {
        this.rightBias = rightBias;
        this.topBias = topBias;
    }

    public static BinaryTreeBias randomBias() {
        return BinaryTreeBias.values()[(int) (BinaryTreeBias.values().length * Math.random())];
    }
}
