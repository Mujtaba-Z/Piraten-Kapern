package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;

public class Player {

    final Logger LOGGER = LogManager.getLogger(Player.class);
    private HashMap<String, Integer> rollrecord = new HashMap<String, Integer>();

    int score = 0;
    private int numofrolls = 8;

    private int temprolls = 8;

    private int wins = 0;

    private double winrate = 0.0;

    public void setinitialHand() {
        rollrecord.put("MONKEY", 0);
        rollrecord.put("PARROT", 0);
        rollrecord.put("GOLD", 0);
        rollrecord.put("DIAMOND", 0);
        rollrecord.put("SABER", 0);
        rollrecord.put("SKULL", 0);
    }

    private void setRollrecord(Faces droll) {

        if (droll.toString().equals("GOLD")) {
            rollrecord.put("GOLD", rollrecord.get("GOLD") + 1);
        } else if (droll.toString().equals("DIAMOND")) {
            rollrecord.put("DIAMOND", rollrecord.get("DIAMOND") + 1);
        } else if (droll.toString().equals("SKULL")) {
            rollrecord.put("SKULL", rollrecord.get("SKULL") + 1);
        } else if (droll.toString().equals("SABER")) {
            rollrecord.put("SABER", rollrecord.get("SABER") + 1);
        } else if (droll.toString().equals("PARROT")) {
            rollrecord.put("PARROT", rollrecord.get("PARROT") + 1);
        } else if (droll.toString().equals("MONKEY")) {
            rollrecord.put("MONKEY", rollrecord.get("MONKEY") + 1);
        }

    }

    private Cards drawcard() {
        int howManyCards = Cards.values().length;
        Random bag = new Random();
        return Cards.values()[bag.nextInt(howManyCards)];
    }

    private void RegularRoll(Dice die) {
        Faces result;
        for (int roll = 0; roll < this.numofrolls; roll++) {
            result = die.roll();
            if (result.toString().equals("SKULL")) {
                rollrecord.put("SKULL", rollrecord.get("SKULL") + 1);
                temprolls -= 1;
            } else {
                this.setRollrecord(result);
            }
        }
        numofrolls = 0;
    }

    private void setscores(String card) {
        if (rollrecord.get(card) == 3) {
            score += 100;
        } else if (rollrecord.get(card) == 4) {
            score += 200;
        } else if (rollrecord.get(card) == 5) {
            score += 500;
        } else if (rollrecord.get(card) == 6) {
            score += 1000;
        } else if (rollrecord.get(card) == 7) {
            score += 2000;
        } else if (rollrecord.get(card) == 8) {
            score += 4000;
        }
    }

    private void endturn(Cards card) {
        score += 100*(rollrecord.get("GOLD")+ rollrecord.get("DIAMOND"));

        // If the player drew a Monkey Business card, then the amount of monkeys and parrots rolled are all joined under monkeys
        if (card.equals(Cards.MONKEYBUSINESS1) || card.equals(Cards.MONKEYBUSINESS2) || card.equals(Cards.MONKEYBUSINESS3) || card.equals(Cards.MONKEYBUSINESS4)) {
            rollrecord.put("MONKEY", rollrecord.get("MONKEY") + rollrecord.get("PARROT"));
            rollrecord.put("PARROT", 0);
        }

        setscores("PARROT");
        setscores("GOLD");
        setscores("DIAMOND");
        setscores("MONKEY");
        setscores("SABER");

        setinitialHand();
        this.numofrolls = 8;
        this.temprolls = 8;
    }

    private int SeaBattle(Cards card, Dice die, String[] args, String playerID){
        int numofsabers = 0;
        int bonus = 0;

        // Checking to see if the card drawn is eligible for the seabattle
        if (card.equals(Cards.SEABATTLE1) || card.equals(Cards.SEABATTLE2)) {
            numofsabers = 2;
            bonus = 300;
        } else if (card.equals(Cards.SEABATTLE3) || card.equals(Cards.SEABATTLE4)) {
            numofsabers = 3;
            bonus = 500;
        } else if (card.equals(Cards.SEABATTLE5) || card.equals(Cards.SEABATTLE6)) {
            numofsabers = 4;
            bonus = 1000;
        } else return -1;

        if (args[2].equals("trace")) {
            LOGGER.info(playerID+" has engaged in a seabattle with a "+bonus+" on the line for "+numofsabers+" sabers");
        }

        // If card is a seabattle, then the following strategy ensues: player rerolls all die that are not sabers by order of importance (least important is monkey/parrot, and most important is gold/diamond) until
        // the required number of sabers are reached or 3 skulls have been accumulated.
        while (rollrecord.get("SKULL") < 3) {
            RegularRoll(die);

            // If the player gets the required number of sabers, they get the bonus and end their turn.
            if (rollrecord.get("SABER") >= numofsabers) {
                score += bonus;
                int trueskulls = rollrecord.get("SKULL");
                if (args[2].equals("trace")) {
                    LOGGER.info("The seabattle was won!");
                    LOGGER.info(playerID+"'s turn has ended");
                    LOGGER.info("At the end of the turn, "+rollrecord.get("SKULL")+" skulls were rolled, "+rollrecord.get("GOLD")+" golds were rolled, "+rollrecord.get("DIAMOND")+" diamonds were rolled, "+rollrecord.get("SABER")+" sabers were rolled, "+rollrecord.get("MONKEY")+" monkeys were rolled, and "+rollrecord.get("PARROT")+" parrots were rolled.");
                }
                rollrecord.put("SKULL", 3);
                return trueskulls;
            }
            // If the player does not have the required number of sabers, then they roll other die until they do, or they have 3 or more skulls.
            if (rollrecord.get("MONKEY") == 0 && rollrecord.get("PARROT") == 0) {
                if ((rollrecord.get("GOLD") != 0 && rollrecord.get("GOLD") < rollrecord.get("DIAMOND")) || rollrecord.get("DIAMOND") == 0) {
                    numofrolls += rollrecord.get("GOLD");
                    rollrecord.put("GOLD", 0);
                } else {
                    numofrolls += rollrecord.get("DIAMOND");
                    rollrecord.put("DIAMOND", 0);
                }
            } else {
                numofrolls += rollrecord.get("MONKEY") + rollrecord.get("PARROT");
                rollrecord.put("PARROT", 0);
                rollrecord.put("MONKEY", 0);
            }
        }

        if (args[2].equals("trace")) {
            LOGGER.info("The seabattle was lost.");
        }

        return -2;
    }

    void randomstrat(Dice die, String[] args, String playerID) {
        // Player's turn. Only lasts so long as he hasn't rolled a sum total of 3 skulls
        Faces result;
        Cards card = drawcard();
        int trueskulls = SeaBattle(card, die, args, playerID);
        while (rollrecord.get("SKULL") < 3) {
            // Rolling 8 dice, or however many he has left
            for (int roll = 0; roll < this.numofrolls; roll++) {
                result = die.roll();
                if (result.toString().equals("SKULL")) {
                    rollrecord.put("SKULL", rollrecord.get("SKULL") + 1);
                    temprolls -= 1;
                }
            }
            // Calculating random number of rolls
            if (temprolls < 2) {
                this.numofrolls = 0;
                rollrecord.put("SKULL", 3);
            }
            try {
                this.numofrolls = temprolls - ThreadLocalRandom.current().nextInt(0, temprolls - 1);
            } catch (Exception e) {
            }
        }
        if (trueskulls == -1) {
            for (int roll = 0; roll <= (8 - rollrecord.get("SKULL")); roll++) {
                result = die.roll();
                this.setRollrecord(result);
            }
            if (args[2].equals("trace")) {
                LOGGER.info(playerID+"'s turn has ended");
                LOGGER.info("At the end of the turn, "+rollrecord.get("SKULL")+" skulls were rolled, "+rollrecord.get("GOLD")+" golds were rolled, "+rollrecord.get("DIAMOND")+" diamonds were rolled, "+rollrecord.get("SABER")+" sabers were rolled, "+rollrecord.get("MONKEY")+" monkeys were rolled, and "+rollrecord.get("PARROT")+" parrots were rolled.");
            }
        } else if (trueskulls == -2) {
            setinitialHand();
        }

        endturn(card);

    }

    void combostrat(Dice die, String[] args, String playerID) {
        Faces result;
        Cards card = drawcard();
        int trueskulls = SeaBattle(card, die, args, playerID);
        while (rollrecord.get("SKULL") < 3) {
            // Rolling 8 dice, or however many he has left
            RegularRoll(die);

            if (rollrecord.get("SABER") < 3) {
                numofrolls += rollrecord.get("SABER");
                rollrecord.put("SABER", 0);
            }
            if (rollrecord.get("PARROT") < 3){
                numofrolls += rollrecord.get("PARROT");
                rollrecord.put("PARROT", 0);
            }
            if (rollrecord.get("MONKEY") < 3){
                numofrolls += rollrecord.get("MONKEY");
                rollrecord.put("MONKEY", 0);
            }
            if ((rollrecord.get("MONKEY") == 0 || rollrecord.get("MONKEY") >= 3) && (rollrecord.get("SABER") == 0 || rollrecord.get("SABER") >= 3) && (rollrecord.get("PARROT") == 0 || rollrecord.get("PARROT") >= 3)) {
                rollrecord.put("SKULL", 3);
            }
        }
        if (trueskulls == -2) {
            setinitialHand();
        } else if (trueskulls == -1){
            if (args[2].equals("trace")) {
                LOGGER.info(playerID+"'s turn has ended");
                LOGGER.info("At the end of the turn, " + rollrecord.get("SKULL") + " skulls were rolled, " + rollrecord.get("GOLD") + " golds were rolled, " + rollrecord.get("DIAMOND") + " diamonds were rolled, " + rollrecord.get("SABER") + " sabers were rolled, " + rollrecord.get("MONKEY") + " monkeys were rolled, and " + rollrecord.get("PARROT") + " parrots were rolled.");
            }
        }
        rollrecord.put("SKULL", trueskulls);
        endturn(card);

    }

    void setWins(Player p2) {
        if (this.score > p2.score) {
            this.wins++;
        } else if (p2.score > this.score) {
            p2.wins++;
        }
        this.score = 0;
        p2.score = 0;
    }

    double setWinrate(double numofgames) {
        winrate = ((wins*100.0)/numofgames);
        return winrate;
    }

}
