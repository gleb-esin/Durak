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

public class Round {
    private Table table;
    private Croupier croupier;
    private PlayerController playerController;

    public Round(Table table, Croupier croupier, PlayerController playerController) {
        this.table = table;
        this.croupier = croupier;
        this.playerController = playerController;
    }

    public void start() {
        MoveInterface attackMove = new AttackMove();
        MoveInterface defenceMove = new DefenceMove();
        MoveInterface throwMove = new ThrowMove();
        ThrowValidator moveValidator = new ThrowValidator();
        gameloop:
        while (!playerController.isGameOver()) {

            //attack move
            Player attacker = playerController.getAttacker();
            Player defender = playerController.getDefender();
            clearConsole(attacker.getName());
            print("Ход " + attacker.getName() + " под " + defender.getName()
                    + ", козырь " + croupier.getDeck().getTrump());
            print(attacker);
            attackMove.move(attacker, table);
            if(playerController.isPlayerWinner(attacker, croupier.getDeck())) break gameloop;

            //defence move
            clearConsole(defender.getName());
            print("Отбивается " + defender.getName() + ", козырь " + croupier.getDeck().getTrump());
            print(table);
            print(defender);
            defenceMove.move(defender, table);
            if (defender.getRole().equals("binder")) {
                playerController.setBinder(defender);
            } else {
                if(playerController.isPlayerWinner(defender, croupier.getDeck())) break gameloop;
            }
            //throw move
            if (!playerController.isGameOver()) {
                for (Player thrower : playerController.getQueue()) {
                    throwloop:
                    while (moveValidator.isThrowPossible(table.getAll(), thrower.getPlayerHand()) && !defender.getPlayerHand().isEmpty()) {
                        clearConsole(thrower.getName());
                        print(table);
                        int numberOfUnbeatenCards = table.getUnbeatenCards().size();
                        print(thrower.getName() + ", Вы можете подкинуть. Козырь " + croupier.getDeck().getTrump());
                        print(thrower);
                        throwMove.move(thrower, table);
                        if (numberOfUnbeatenCards == table.getUnbeatenCards().size()) break throwloop;
                        if (!playerController.isGameOver() && !table.getUnbeatenCards().isEmpty()) {
                            if (!defender.getRole().equals("binder")) {
                                clearConsole(defender.getName());
                                print("Отбивается " + defender.getName() + ", козырь " + croupier.getDeck().getTrump());
                                print(table);
                                print(defender);
                                defenceMove.move(defender, table);
                            } else {
                                playerController.setBinder(defender);
                                break throwloop;
                            }
                        }
                    }
                    if(playerController.isPlayerWinner(thrower, croupier.getDeck())) break gameloop;
                }
            }
            if (playerController.getBinder() != null) {
                for (Player thrower : playerController.getQueue()) {
                    if (moveValidator.isThrowPossible(table.getAll(), thrower.getPlayerHand())) {
                        clearConsole(thrower.getName());
                        print(table);
                        print(thrower.getName() + ", Вы можете подкинуть. Козырь " + croupier.getDeck().getTrump());
                        print(thrower);
                        throwMove.move(thrower, table);
                        if(playerController.isPlayerWinner(thrower, croupier.getDeck())) break gameloop;
                    }
                }
                print(playerController.getBinder().getName() + " забирает карты " + table.getAll());
                playerController.getBinder().getPlayerHand().addAll(table.getAll());
            }
            table.clear();
            croupier.fillUpTheHands(playerController.getQueue(), defender);
            playerController.changeTurn();
        }
        print(playerController.getWinner().getName() + " победитель!");
    }
}
