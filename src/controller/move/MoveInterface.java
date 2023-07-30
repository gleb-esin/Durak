package controller.move;

import model.Player;
import model.Table;

public interface MoveInterface {
    abstract void move(Player player, Table table);

}
