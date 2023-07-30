package controller.move;

import controller.TableController;
import model.Player;
import model.Table;

public interface MoveInterface {
    abstract void move(Player player, TableController tableController);

}
