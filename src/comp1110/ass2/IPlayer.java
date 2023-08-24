package comp1110.ass2;

public interface IPlayer {
    char getColor();
    int getDirhams();
    void setDirhams(int dirhams);
    int getRugs();
    void setRugs(int rugs);
    boolean isActive();
    void setActive(boolean active);
    String toPlayerString();
}
