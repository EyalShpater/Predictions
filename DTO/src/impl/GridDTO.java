package impl;

import api.DTO;

public class GridDTO implements DTO {
    private final int row;
    private final int col;

    public GridDTO(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
