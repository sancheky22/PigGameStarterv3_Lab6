package edu.up.cs301.pig;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

import android.util.Log;

/**
 * class PigLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigLocalGame extends LocalGame {



    private PigGameState ourGameState;


    /**
     * This ctor creates a new game state
     */
    public PigLocalGame() {
        ourGameState = new PigGameState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if (ourGameState.getPlayerId() == playerIdx){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof PigHoldAction) {
            if (ourGameState.getPlayerId() == 1) {
                ourGameState.setPlayer1Score(ourGameState.getPlayer1Score() + ourGameState.getRunningScore());
                ourGameState.setRunningScore(0);
            } else {
                ourGameState.setPlayer0Score(ourGameState.getPlayer0Score() + ourGameState.getRunningScore());
                ourGameState.setRunningScore(0);
            }

            if (ourGameState.getPlayerId() == 1) {
                ourGameState.setPlayerId(0);
            } else {
                ourGameState.setPlayerId(1);
            }
            return true;

        } else if (action instanceof PigRollAction) {


            double rand = (Math.random() * ((6 - 1) + 1)) + 1;
            int random = (int) rand;
            ourGameState.setDieValue(random);

            if (ourGameState.getDieValue() != 1) {
                ourGameState.setRunningScore(ourGameState.getRunningScore() + ourGameState.getDieValue());
            } else if (ourGameState.getDieValue() == 1) {
                ourGameState.setRunningScore(0);
                if (ourGameState.getPlayerId() == 1) {
                    ourGameState.setPlayerId(0);
                } else {
                    ourGameState.setPlayerId(1);
                }
            }
            return true;
        }

        return false;
    }

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        PigGameState copy = new PigGameState(ourGameState);
        p.sendInfo(copy);
    }//sendUpdatedSate



    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        if(ourGameState.getPlayer0Score() >= 50){
            return "Player 1 won with a score of " + ourGameState.getPlayer0Score();
        } else if (ourGameState.getPlayer1Score() >= 50){
            return "Player 2 won with a score of " + ourGameState.getPlayer1Score();
        }
        return null;
    }

}// class PigLocalGame
