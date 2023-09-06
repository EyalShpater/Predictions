package grid;

import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SphereSpaceImpl implements SphereSpace {
    private static final int MOVE_UP = 1;
    private static final int MOVE_DOWN = -1;
    private static final int MOVE_LEFT = -1;
    private static final int MOVE_RIGHT = 1;
    private static final int NO_MOVE = 0;
    private static final int ARRAY_FIRST_INDEX = 0;
    private static final int NUM_OF_MOVES = 4;

    int rows;
    int cols;
    EntityInstance[][] grid;
    Set<Point> emptyCells;

    public SphereSpaceImpl(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new EntityInstance[rows][cols];
        emptyCells = createNewEmptyCellsTreeSet();
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
        Point newLocation = getRandomEmptyCell();
        boolean isThereFreeCell = newLocation != null;

        if (isThereFreeCell) {
            emptyCells.remove(newLocation);
            entity.setLocationInSpace(newLocation);
            grid[newLocation.y][newLocation.x] = entity;
        }

        return isThereFreeCell;
    }

    private boolean moveUp(EntityInstance entity) {
        return move(entity, NO_MOVE, MOVE_UP);
    }

    private boolean moveDown(EntityInstance entity) {
        return move(entity, NO_MOVE, MOVE_DOWN);
    }

    private boolean moveLeft(EntityInstance entity) {
        return move(entity, MOVE_LEFT, NO_MOVE);
    }

    private boolean moveRight(EntityInstance entity) {
        return move(entity, MOVE_RIGHT, NO_MOVE);
    }

    private boolean move(EntityInstance entity, int dx, int dy) {
        Point position = entity.getLocationInSpace();
        int newXPosition = (position.x + dx + cols) % cols;
        int newYPosition = (position.y + dy + rows) % rows;
        boolean isSucceed = false;

        if (grid[newYPosition][newXPosition] == null) {
            grid[position.y][position.x] = null;
            grid[newYPosition][newXPosition] = entity;

            entity.setLocationInSpace(newXPosition, newYPosition);

            emptyCells.remove(new Point(position.x, position.y));
            emptyCells.add(new Point(newXPosition, newYPosition));

            isSucceed = true;
        }

        return isSucceed;
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

    private TreeSet<Point> createNewEmptyCellsTreeSet() {
        TreeSet<Point> emptyCells = new TreeSet<>(createPointComparator());

        for (int i = ARRAY_FIRST_INDEX; i < rows; i++) {
            for (int j = ARRAY_FIRST_INDEX; j < cols; j++) {
                emptyCells.add(new Point(i, j));
            }
        }

        return emptyCells;
    }

    private Comparator<Point> createPointComparator() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int difference = Integer.compare(p1.x, p2.x);

                if (difference == 0) {
                    difference = Integer.compare(p1.y, p2.y);
                }

                return difference;
            }
        };
    }

    private Point getRandomEmptyCell() {
        Point randomCell = null;
        boolean isThereFreeCell = !emptyCells.isEmpty();

        if (isThereFreeCell) {
            List<Point> freeLocations = new ArrayList<>(emptyCells);

            Collections.shuffle(freeLocations);
            randomCell = freeLocations.get(ARRAY_FIRST_INDEX);
        }

        return randomCell;
    }
}
