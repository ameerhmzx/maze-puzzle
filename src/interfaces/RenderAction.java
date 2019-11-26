package interfaces;

@FunctionalInterface
public interface RenderAction {
    void updated(Runnable runnable);
}
