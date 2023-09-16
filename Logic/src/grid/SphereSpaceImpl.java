package grid;

import definition.entity.impl.EntityDefinitionImpl;
import grid.api.Location;
import grid.api.SphereSpace;
import instance.entity.api.EntityInstance;
import instance.entity.impl.EntityInstanceImpl;

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
    public Location placeEntityRandomlyInWorld(EntityInstance entity) {
        Location newLocation = getRandomEmptyCell();

        if (newLocation != null) {
            grid[newLocation.getY()][newLocation.getX()] = entity;
            emptyCells.remove(newLocation);
        }

        return newLocation;
    }

    @Override
    public void removeEntityFromSpace(EntityInstance entityToRemove) {
        Location locationToUpdate = entityToRemove.getLocationInSpace();

        entityToRemove.setLocationInSpace(null);

        grid[locationToUpdate.getY()][locationToUpdate.getX()] = null;
        emptyCells.add(locationToUpdate);
    }

    @Override
    public List<EntityInstance> getNearbyEntities(EntityInstance target, int radius) {
        Location targetLocation = target.getLocationInSpace();
        Map<Integer, EntityInstance> neighbours = new HashMap<>();

        int leftColIndex = (targetLocation.getX() - radius + (this.cols * radius)) % this.cols;
        int rightColIndex = (targetLocation.getX() + radius) % this.cols;
        int upperRowIndex = (targetLocation.getY() - radius + (this.rows * radius)) % this.rows;
        int lowerRowIndex = (targetLocation.getY() + radius) % this.rows;

        for (int i = upperRowIndex; i != (lowerRowIndex + 1) % this.rows; i = (i + 1) % this.rows) {
            for (int j = leftColIndex; j != (rightColIndex + 1) % this.cols; j = (j + 1) % this.cols) {
                if (grid[i][j] != null) {
                    neighbours.put(grid[i][j].getId(), grid[i][j]);
                }
            }
        }

        return new ArrayList<>(neighbours.values());
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
        boolean isSucceed = false;

        if (oldLocation != null) {
            int newXPosition = (oldLocation.getX() + dx + cols) % cols;
            int newYPosition = (oldLocation.getY() + dy + rows) % rows;
            Location newLocation = new Location(newXPosition, newYPosition);

            if (grid[newYPosition][newXPosition] == null) {
                updateGridLocation(oldLocation, newLocation);
                entity.setLocationInSpace(newLocation);
                isSucceed = true;
            }
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

    private void updateGridLocation(Location oldLocation, Location newLocation) {
        grid[newLocation.getY()][newLocation.getX()] = grid[oldLocation.getY()][oldLocation.getX()];
        grid[oldLocation.getY()][oldLocation.getX()] = null;
        emptyCells.remove(newLocation);
        emptyCells.add(oldLocation);
    }


    //todo: delete
    public static void main(String[] args) {
        EntityInstance e1 = new EntityInstanceImpl(new EntityDefinitionImpl("e1"), 1);
        EntityInstance e2 = new EntityInstanceImpl(new EntityDefinitionImpl("e2"), 1);
        SphereSpaceImpl space = new SphereSpaceImpl(10, 10);

        e1.setLocationInSpace(new Location(5, 4));
        space.getNearbyEntities(e1, 1);

//        space.setNewRandomLocation(e1);
//        space.setNewRandomLocation(e2);

//        space.moveRight(e1);
//        space.moveRight(e1);
//        space.moveUp(e1);
//        space.moveUp(e1);
//        space.moveDown(e1);
//        space.moveDown(e1);
//        space.moveLeft(e1);
//        space.moveLeft(e1);
    }
}
