package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Game {

    public void playgame(int numgames, Player playerone, Player playertwo, Dice die, String[] strat) {

        if (strat.length != 3) {
            System.out.println("Incorrect number of command line arguments. Required 3.");
            return;
        } else if (!strat[0].equals("random") && !strat[0].equals("combo")) {
            System.out.println("Incorrect strategy for player one. Try \"random\" or \"combo\"");
            return;
        } else if (!strat[1].equals("random") && !strat[1].equals("combo")) {
            System.out.println("Incorrect strategy for player two. Try \"random\" or \"combo\"");
            return;
        } else if (!strat[2].equals("trace") && !strat[2].equals("notrace")) {
            System.out.println("Incorrect trace mode. Try \"trace\" or \"notrace\"");
            return;
        }

        final Logger LOGGER = LogManager.getLogger(Game.class);

        boolean done1 = true;
        boolean done2 = true;
        boolean done3 = true;

        if (strat[2].equals("trace")) {
            LOGGER.info("Trace mode activated");
            LOGGER.info("Player one has strategy "+strat[0]);
            LOGGER.info("Player two has strategy "+strat[1]);
        }

        for (int games = 0; games < numgames; games++) {
            if (strat[2].equals("trace")) {
                LOGGER.info("Game number "+(games+1));
            }
            // Instructions per game
            while (playerone.score < 6000 || playertwo.score < 6000) {

                if (playerone.score >= 6000 && playertwo.score < 6000) {

                    if (strat[2].equals("trace") && done1) {
                        LOGGER.info("Player one has reached 6000 or more points: "+playerone.score+"\nOnly player two is rolling now");
                        done1 = false;
                    }
                    // Player one is done. Player two is the only one playing now.

                    // Player two's turn
                    if (strat[1].equals("random")) {
                        playertwo.randomstrat(die, strat, "Player one");
                    } else if (strat[1].equals("combo")) {
                        playertwo.combostrat(die, strat, "Player two");
                    }


                } else if (playerone.score < 6000 && playertwo.score >= 6000) {
                    // Player two is done. Player one is the only one playing now.

                    if (strat[2].equals("trace") && done2) {
                        LOGGER.info("Player two has reached 6000 or more points: "+playertwo.score+"\nOnly player one is rolling now");
                        done2 = false;
                    }

                    // Player one's turn
                    if (strat[0].equals("random")) {
                        playerone.randomstrat(die, strat, "Player one");
                    } else if (strat[0].equals("combo")) {
                        playerone.combostrat(die, strat, "Player two");
                    }

                } else {
                    // When neither player has more than 6000 points

                    if (strat[2].equals("trace") && done3) {
                        LOGGER.info("Neither player has 6000 or more points yet");
                        done3 = false;
                    }

                    // Player one's turn
                    if (strat[0].equals("random")) {
                        playerone.randomstrat(die, strat, "Player one");
                    } else if (strat[0].equals("combo")) {
                        playerone.combostrat(die, strat, "Player two");
                    }

                    // Player two's turn
                    if (strat[1].equals("random")) {
                        playertwo.randomstrat(die, strat, "Player one");
                    } else if (strat[1].equals("combo")) {
                        playertwo.combostrat(die, strat, "Player two");
                    }

                }
            }

            if (strat[2].equals("trace")) {
                LOGGER.info("Game number "+(games+1)+" finished\nPlayer one has "+playerone.score+" and player two has "+playertwo.score);
                done3 = true;
                done2 = true;
                done1 = true;
            }

            playerone.setWins(playertwo);

        }
    }

    public void displaywins(Player playerone, Player playertwo, int numgames) {
        System.out.printf("Percentage of wins for player one: %.2f%%  \n",playerone.setWinrate(numgames));
        System.out.printf("Percentage of wins for player two: %.2f%%  \n",playertwo.setWinrate(numgames));
    }

}
