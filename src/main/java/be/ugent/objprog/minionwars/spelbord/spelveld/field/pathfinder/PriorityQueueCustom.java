package be.ugent.objprog.minionwars.spelbord.spelveld.field.pathfinder;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

//bron: https://www.redblobgames.com/pathfinding/a-star/implementation.html#python-dijkstra
public class PriorityQueueCustom<T> {
    private final PriorityQueue<Pair<Double, T>> elements = new PriorityQueue<>(Comparator.comparingDouble(a -> a.first));

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void put(T item, double priority) {
        elements.add(new Pair<>(priority, item));
    }

    public T get() {
        return Objects.requireNonNull(elements.poll()).second;
    }

    record Pair<F, S>(F first, S second) {}
}