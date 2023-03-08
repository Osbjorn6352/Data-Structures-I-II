package algs11;

public class Square {
    final int label;
    final boolean isBlack;
    
    public Square(boolean isBlack, int label) {
    	this.label = label; this.isBlack = isBlack;
    }
    
    public int getLabel() { return label;}
    public boolean getBlack() { return isBlack; }
}
