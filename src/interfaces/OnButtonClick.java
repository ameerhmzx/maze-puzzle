package interfaces;

import layoutStrategies.LayoutStrategy;
import layoutStrategies.PostLayoutStrategy;
import solutionStrategies.SolutionStrategy;

public interface OnButtonClick {
    void solve(SolutionStrategy solutionStrategy);

    void generate(int width, int height, LayoutStrategy layoutStrategy, PostLayoutStrategy postLayoutStrategy);
}
