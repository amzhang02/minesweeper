import java.util.Random;
public class AlyssasMineModel implements MineModel{
    private int numRows, numCols, numMines, numFlags;
    private long startTime;
    private AlyssasCell[][] grid;
    private boolean gameOver, playerDead, gameWon;
    Random generator = new Random();

    AlyssasMineModel(){
        newGame(10,20,20);
    }

    @Override
    public void newGame(int numRows, int numCols, int numMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        this.numFlags = 0;
        this.gameOver = false;
        this.playerDead = false;
        this.gameWon = false;
        this.startTime = System.currentTimeMillis()/1000;

        grid = new AlyssasCell[numRows][numCols];
        for (int row = 0; row < numRows; row += 1){
            for (int col = 0; col < numCols; col +=1){
                grid[row][col] = new AlyssasCell(row, col);
            }
        }

        for (int mines = 1; mines <= numMines; mines += 1){
            int randRow = generator.nextInt(numRows);
            int randCol = generator.nextInt(numCols);
            if (grid[randRow][randCol].isMine() == false){
                grid[randRow][randCol].placeMine();
            }
            else{
                while (grid[randRow][randCol].isMine() == true){
                    randRow = generator.nextInt(numRows);
                    randCol = generator.nextInt(numCols);
                }
                grid[randRow][randCol].placeMine();
            }
        }

        for (int row = 0; row < grid.length; row ++) {
            for (int col = 0; col < grid[0].length; col ++) {
                grid[row][col].setNeighborMines(grid);
            }
        }

        isGameStarted();
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    @Override
    public int getNumMines() {
        return numMines;
    }

    @Override
    public int getNumFlags() {
        return numFlags;
    }

    @Override
    public int getElapsedSeconds() {
        long currentTime = System.currentTimeMillis()/1000;
        return (int) (currentTime - startTime);
    }

    @Override
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    @Override
    public void stepOnCell(int row, int col) {
        grid[row][col].makeVisible();

        if(grid[row][col].isMine() && !grid[row][col].isFlagged()){
            playerDead = true;
            gameOver = true;
        }
        else {
            if (grid[row][col].getNeighborMines() == 0){
                for (int rowChange = -1; rowChange <= 1; rowChange ++) {
                    for (int colChange = -1; colChange <= 1; colChange ++) {
                        if (row + rowChange >= 0 && row + rowChange < grid.length && col + colChange >= 0 && col + colChange < grid[0].length){
                            if (!grid[row + rowChange][col + colChange].isVisible()) {
                                stepOnCell(row + rowChange,col + colChange);
                            }
                        }
                    }
                }
            }
        }
        checkForWinGame();
    }

    public void checkForWinGame(){
        gameWon = true;
        gameOver = true;
        for (int row = 0; row < numRows; row += 1){
            for (int col = 0; col < numCols; col +=1){
                if (!grid[row][col].isMine() && !grid[row][col].isVisible()){
                    gameWon = false;
                    gameOver = false;
                }
                if (grid[row][col].isMine() && !grid[row][col].isFlagged()){
                    gameWon = false;
                    gameOver = false;
                }
            }
        }
    }

    @Override
    public void placeOrRemoveFlagOnCell(int row, int col) {
        if (!grid[row][col].isVisible()){
            grid[row][col].toggleFlag();
        }
        checkForWinGame();
    }

    @Override
    public boolean isGameStarted() {
        return true;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public boolean isPlayerDead() {
        return playerDead;
    }

    @Override
    public boolean isGameWon() {
        return gameWon;
    }
}
