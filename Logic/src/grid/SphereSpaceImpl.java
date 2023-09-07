package grid;

import grid.api.Location;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;

import java.util.*;
import java.util.List;
import java.util.function.Function;

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
    Set<Location> emptyCells;

    public SphereSpaceImpl(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new EntityInstance[rows][cols];
        emptyCells = createNewEmptyCellsTreeSet();
    }

    @Override
    public boolean makeRandomMove(EntityInstance entity) {
        List<Function<EntityInstance, Boolean>> moveMaker = createMoveList();
        boolean isSucceedToMove = false;

        Collections.shuffle(moveMaker);
        for (int i = ARRAY_FIRST_INDEX; i < NUM_OF_MOVES && !isSucceedToMove; i++) {
            isSucceedToMove = moveMaker.get(i).apply(entity);
        }

        return isSucceedToMove;
    }

    @Override
    public boolean setRandomLocation(EntityInstance entity) {
        Location oldLocation;
        Location newLocation = getRandomEmptyCell();
        boolean isThereFreeCell = newLocation != null;

        if (isThereFreeCell) {
            oldLocation = entity.getLocationInSpace();
            updateGridLocation(oldLocation, newLocation);
            entity.setLocationInSpace(newLocation);
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
        Location oldLocation = entity.getLocationInSpace();
        int newXPosition = (oldLocation.getX() + dx + cols) % cols;
        int newYPosition = (oldLocation.getY() + dy + rows) % rows;
        Location newLocation = new Location(newXPosition, newYPosition);
        boolean isSucceed = false;

        if (grid[newYPosition][newXPosition] == null) {
            updateGridLocation(oldLocation, newLocation);
            entity.setLocationInSpace(newLocation);
            isSucceed = true;
        }

        return isSucceed;
    }

    private TreeSet<Location> createNewEmptyCellsTreeSet() {
        TreeSet<Location> emptyCells = new TreeSet<>();

        for (int i = ARRAY_FIRST_INDEX; i < rows; i++) {
            for (int j = ARRAY_FIRST_INDEX; j < cols; j++) {
                emptyCells.add(new Location(i, j));
            }
        }

        return emptyCells;
    }

    private List<Function<EntityInstance, Boolean>> createMoveList() {
        List<Function<EntityInstance, Boolean>> moveMaker = new ArrayList<>();

        moveMaker.add(this::moveUp);
        moveMaker.add(this::moveDown);
        moveMaker.add(this::moveLeft);
        moveMaker.add(this::moveRight);

        return moveMaker;
    }

    private Location getRandomEmptyCell() {
        boolean isThereFreeCell = !emptyCells.isEmpty();
        Location randomCell = null;

        if (isThereFreeCell) {
            int randomIndex;
            Random random = new Random();
            List<Location> freeLocations = new ArrayList<>(emptyCells);

            randomIndex = random.nextInt(freeLocations.size());
            randomCell = freeLocations.get(randomIndex);
        }

        return randomCell;
    }

    void updateGridLocation(Location oldLocation, Location newLocation) {
        grid[newLocation.getY()][newLocation.getX()] = grid[oldLocation.getY()][oldLocation.getX()];
        grid[oldLocation.getY()][oldLocation.getX()] = null;
        emptyCells.remove(newLocation);
        emptyCells.add(oldLocation);
    }
}
