package controller;

import controller.move.AttackMove;
import controller.move.DefenceMove;
import controller.move.MoveInterface;
import controller.move.ThrowMove;
import controller.moveValidator.ThrowValidator;
import model.Player;
import model.Table;

import static controller.moveValidator.ThrowValidator.isThrowPossible;
import static view.ClearConsole.clearConsole;
import static view.Printer.print;

/**
 * This class provides main business logic of game
 */
public class Game {
    private TableController tableController;
    private DeckController deckController;
    private PlayerController playerController;

    public Game(TableController tableController, DeckController deckController, PlayerController playerController) {
        this.tableController = tableController;
        this.deckController = deckController;
        this.playerController = playerController;
    }

    public void start() {
        MoveInterface attackMove = new AttackMove();
        MoveInterface defenceMove = new DefenceMove();
        MoveInterface throwMove = new ThrowMove();
        Table table = tableController.getTable();
        gameloop:
        while (!playerController.isGameOver()) {
            //attack move
            Player attacker = playerController.getAttacker();
            Player defender = playerController.getDefender();
            clearConsole(attacker.getName());
            print("Ход " + attacker.getName() + " под " + defender.getName()
                    + ", козырь " + deckController.getDeck().getTrump());
            print(attacker);
            attackMove.move(attacker, tableController);
            if (playerController.isPlayerWinner(attacker, deckController.getDeck())) break gameloop;

            //defence move
            defenseInit(defenceMove);
            if (defender.getRole().equals("binder")) playerController.setBinder(defender);
            if (playerController.isPlayerWinner(defender, deckController.getDeck())) break gameloop;

            //throw move
            if (!playerController.isGameOver()) {
                for (Player thrower : playerController.getQueue()) {
                    boolean throwerCanThrow = isThrowPossible(tableController.getAll(), thrower.getPlayerHand()) && !defender.getPlayerHand().isEmpty();
                    throwloop:
                    while (throwerCanThrow) {
                        int numberOfUnbeatenCards = table.getUnbeatenCards().size();
                        clearConsole(thrower.getName());
                        print(tableController.getTable());
                        print(thrower.getName() + ", Вы можете подкинуть. Козырь " + deckController.getDeck().getTrump());
                        print(thrower);
                        throwMove.move(thrower, tableController);
                        if (playerController.isPlayerWinner(thrower, deckController.getDeck())) break gameloop;
                        boolean throwerDidntThrow = numberOfUnbeatenCards == table.getUnbeatenCards().size();
                        if (throwerDidntThrow) break;
                        if (!playerController.isGameOver() && !table.getUnbeatenCards().isEmpty()) {
                            if (!defender.getRole().equals("binder")) {
                                defenseInit(defenceMove);
                                if (defender.getRole().equals("binder")) {
                                    playerController.setBinder(defender);
                                } else {
                                    if (playerController.isPlayerWinner(defender, deckController.getDeck()))
                                        break gameloop;
                                }
                            }
                        }
                        throwerCanThrow = isThrowPossible(tableController.getAll(), thrower.getPlayerHand()) && !defender.getPlayerHand().isEmpty();
                    }
                    if (!throwerCanThrow) print(thrower.getName() + ", не может подкинуть.");
                    if (defender.getRole().equals("binder")) {
                        print(playerController.getBinder().getName() + " забирает карты " + tableController.getAll());
                        playerController.getBinder().getPlayerHand().addAll(tableController.getAll());
                    }
                }
            }
            tableController.clear();
            deckController.fillUpTheHands(playerController.getQueue(), defender);
            playerController.changeTurn();
        }
        print(playerController.getWinner().getName() + " победитель!");
    }

    private void defenseInit(MoveInterface defenceMove) {
        clearConsole(playerController.getDefender().getName());
        print(tableController.getTable());
        print("Отбивается " + playerController.getDefender().getName() + ", козырь " + deckController.getDeck().getTrump());
        print(playerController.getDefender());
        defenceMove.move(playerController.getDefender(), tableController);
    }
}


