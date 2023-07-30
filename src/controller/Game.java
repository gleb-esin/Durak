package controller;

import controller.move.AttackMove;
import controller.move.DefenceMove;
import controller.move.MoveInterface;
import controller.move.ThrowMove;
import controller.moveValidator.ThrowValidator;
import model.Player;
import model.Table;

import static view.ClearConsole.clearConsole;
import static view.Printer.print;
/**
 * This class provides main business logic of game */
public class Game {
    private Table table;
    private DeckController deckController;
    private PlayerController playerController;

    public Game(Table table, DeckController deckController, PlayerController playerController) {
        this.table = table;
        this.deckController = deckController;
        this.playerController = playerController;
    }

    public void start() {
        MoveInterface attackMove = new AttackMove();
        MoveInterface defenceMove = new DefenceMove();
        MoveInterface throwMove = new ThrowMove();
        ThrowValidator moveValidator = new ThrowValidator();
        TableController tableController = new TableController(this.table);
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
            if(playerController.isPlayerWinner(attacker, deckController.getDeck())) break gameloop;

            //defence move
            clearConsole(defender.getName());
            print("Отбивается " + defender.getName() + ", козырь " + deckController.getDeck().getTrump());
            print(table);
            print(defender);
            defenceMove.move(defender, tableController);
            if (defender.getRole().equals("binder")) {
                playerController.setBinder(defender);
            } else {
                if(playerController.isPlayerWinner(defender, deckController.getDeck())) break gameloop;
            }
            //throw move
            if (!playerController.isGameOver()) {
                for (Player thrower : playerController.getQueue()) {
                    throwloop:
                    while (moveValidator.isThrowPossible(tableController.getAll(), thrower.getPlayerHand()) && !defender.getPlayerHand().isEmpty()) {
                        clearConsole(thrower.getName());
                        print(table);
                        int numberOfUnbeatenCards = table.getUnbeatenCards().size();
                        print(thrower.getName() + ", Вы можете подкинуть. Козырь " + deckController.getDeck().getTrump());
                        print(thrower);
                        throwMove.move(thrower, tableController);
                        if (numberOfUnbeatenCards == table.getUnbeatenCards().size()) break throwloop;
                        if (!playerController.isGameOver() && !table.getUnbeatenCards().isEmpty()) {
                            if (!defender.getRole().equals("binder")) {
                                clearConsole(defender.getName());
                                print("Отбивается " + defender.getName() + ", козырь " + deckController.getDeck().getTrump());
                                print(table);
                                print(defender);
                                defenceMove.move(defender, tableController);
                            } else {
                                playerController.setBinder(defender);
                                break throwloop;
                            }
                        }
                    }
                    if(playerController.isPlayerWinner(thrower, deckController.getDeck())) break gameloop;
                }
            }
            if (playerController.getBinder() != null) {
                for (Player thrower : playerController.getQueue()) {
                    if (moveValidator.isThrowPossible(tableController.getAll(), thrower.getPlayerHand())) {
                        clearConsole(thrower.getName());
                        print(table);
                        print(thrower.getName() + ", Вы можете подкинуть. Козырь " + deckController.getDeck().getTrump());
                        print(thrower);
                        throwMove.move(thrower, tableController);
                        if(playerController.isPlayerWinner(thrower, deckController.getDeck())) break gameloop;
                    }
                }
                print(playerController.getBinder().getName() + " забирает карты " + tableController.getAll());
                playerController.getBinder().getPlayerHand().addAll(tableController.getAll());
            }
            tableController.clear();
            deckController.fillUpTheHands(playerController.getQueue(), defender);
            playerController.changeTurn();
        }
        print(playerController.getWinner().getName() + " победитель!");
    }
}
