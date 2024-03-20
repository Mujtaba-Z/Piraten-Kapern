import pk.*;

public class PiratenKarpen {
    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        Player playerone = new Player();
        Dice die = new Dice();
        Player playertwo = new Player();
        Game PK = new Game();

        playerone.setinitialHand();
        playertwo.setinitialHand();

        // Play 42 games
        PK.playgame(42, playerone, playertwo, die, args);
        PK.displaywins(playerone, playertwo, 42);

    }
}
