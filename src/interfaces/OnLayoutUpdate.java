package interfaces;

@FunctionalInterface
public interface OnLayoutUpdate {
    void updated(Runnable runnable);
}
