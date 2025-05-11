package be.ugent.objprog.minionwars.spelbord.spelveld.field.pathfinder;

import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

import java.util.*;

//bron: https://www.redblobgames.com/pathfinding/a-star/implementation.html#python-astar
public class PathCost {

    private final SpelveldModel model;
    public PathCost(SpelveldModel model) {
        this.model = model;
    }

    public Double heuristic(Field a, Field b) {
        return Math.sqrt(
                Math.pow(a.getCoords().x() - b.getCoords().x(), 2) + Math.pow(a.getCoords().y() - b.getCoords().y(), 2)
        );
    }

    public Integer search(Field start, Field goal) {
        PriorityQueueCustom<Field> frontier = new PriorityQueueCustom<>();
        HashMap<Field, Integer> costSoFar = new HashMap<>();
        boolean pathFound = false;
        frontier.put(start, 0);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            Field current = frontier.get();

            if (current.equals(goal)) {
                pathFound = true;
                break;
            }

            List<Field> neighbors = model.getFieldNeighbors(current);
            for (Field next : neighbors) {
                int newCost = costSoFar.get(current) + cost(current, next);
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.put(next, newCost + heuristic(next, goal));
                }
            }
        }

        if (pathFound) {
            return costSoFar.get(goal);
        }
        return null;
    }

    private int cost(Field current, Field next) {
        return Math.max(current.getMovementCost(), next.getMovementCost());
    }
}