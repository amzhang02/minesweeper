class MineSweeper
{
  public static void main(String[] args) 
  {
    MineModel mineModel = new AlyssasMineModel();
    
    new MineView(mineModel, 600, 400);
  }
}
