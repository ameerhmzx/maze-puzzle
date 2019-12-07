package interfaces;

import layoutStrategies.LayoutStrategy;
import layoutStrategies.PostLayoutStrategy;

public interface OnButtonClick {
    void solve();

    void generate(int width, int height, LayoutStrategy layoutStrategy, PostLayoutStrategy postLayoutStrategy);
}
