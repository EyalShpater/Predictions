package rule.api;

public interface Activation {
    boolean isActive(int tickNumber, double probability);
}
