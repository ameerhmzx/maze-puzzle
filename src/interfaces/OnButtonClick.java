package interfaces;

import layoutStrategies.LayoutStrategy;

public interface OnButtonClick {
    void solve();

    void generate(int width, int height, LayoutStrategy layoutStrategy);
}
