package grid;

import definition.entity.impl.EntityDefinitionImpl;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SphereSpaceImpl implements SphereSpace {
    private static final int MOVE_UP = 1;
    private static final int MOVE_DOWN = -1;
    private static final int MOVE_LEFT = -1;
    private static final int MOVE_RIGHT = 1;
    private static final int NO_MOVE = 0;
    private static final int ARRAY_FIRST_INDEX = 0;
    private static final int NUM_OF_MOVES = 4;

    EntityInstance[][] grid;
    int rows;
    int cols;

    public SphereSpaceImpl(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new EntityInstance[rows][cols];
    }

    @Override
    public boolean makeRandomMove(EntityInstance entity) {
        List<Function<EntityInstance, Boolean>> moveMaker = createMoveList();
        List<Integer> indexes = createRandomListOfNumbers(ARRAY_FIRST_INDEX, NUM_OF_MOVES - 1);
        boolean isSucceedToMove = false;

        for (int i = ARRAY_FIRST_INDEX; i < NUM_OF_MOVES && !isSucceedToMove; i++) {
            isSucceedToMove = moveMaker.get(indexes.get(i)).apply(entity);
        }

        return isSucceedToMove;
    }

    @Override
    public boolean setRandomLocation(EntityInstance entity) {
        return false;
    }

    public boolean moveUp(EntityInstance entity) {
        return move(entity, NO_MOVE, MOVE_UP);
    }

    public boolean moveDown(EntityInstance entity) {
        return move(entity, NO_MOVE, MOVE_DOWN);
    }

    public boolean moveLeft(EntityInstance entity) {
        return move(entity, MOVE_LEFT, NO_MOVE);
    }

    public boolean moveRight(EntityInstance entity) {
        return move(entity, MOVE_RIGHT, NO_MOVE);
    }

    private boolean move(EntityInstance entity, int dx, int dy) {
        Point position = entity.getLocationInSpace();
        int newXPosition = (position.x + dx + cols) % cols;
        int newYPosition = (position.y + dy + rows) % rows;

        if (grid[newYPosition][newXPosition] == null) {
            grid[position.y][position.x] = null;
            grid[newYPosition][newXPosition] = entity;
            return true;
        }

        return false;
    }

    private List<Function<EntityInstance, Boolean>> createMoveList() {
        List<Function<EntityInstance, Boolean>> moveMaker = new ArrayList<>();

        moveMaker.add(this::moveUp);
        moveMaker.add(this::moveDown);
        moveMaker.add(this::moveLeft);
        moveMaker.add(this::moveRight);

        return moveMaker;
    }

    private List<Integer> createRandomListOfNumbers(int from, int to) {
        List<Integer> numbers = new ArrayList<>();

        for (int i = from; i <= to; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        return numbers;
    }
}
