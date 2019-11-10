public class AlyssasCell implements Cell{
    private int row, col, neighborMines;
    private boolean visible, mine, flag;

    public AlyssasCell(int row, int col){
        this.row = row;
        this.col = col;
        this.visible = false;
        this.mine = false;
        this.flag = false;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public boolean makeVisible(){
        visible = true;
        return true;
    }

    @Override
    public boolean isMine() {
        return mine;
    }

    public void placeMine(){
        mine = true;
    }
    @Override
    public boolean isFlagged() {
        return flag;
    }

    public void toggleFlag() {
        flag = !flag;
    }
    public int setNeighborMines(AlyssasCell[][] grid){
        for (int rowChange = -1; rowChange <= 1; rowChange ++) {
            for (int colChange = -1; colChange <= 1; colChange ++) {
                if (row + rowChange >= 0 && row + rowChange < grid.length && col + colChange >= 0 && col + colChange < grid[0].length){
                    if (grid[row + rowChange][col + colChange].isMine()) {
                        neighborMines ++;
                    }
                }
            }
        }
        return neighborMines;
    }
    @Override
    public int getNeighborMines() {
        return neighborMines;
    }
}
