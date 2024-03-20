# A1 - Piraten Karpen

  * Author: Mujtaba (Syed) Zaidi
  * Email: zaidis40@mcmaster.ca

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode:
    * `mvn -q exec:java` (here, `-q` tells maven to be _quiet_)
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar` 
  * Activate trace mode: Make the third argument after `java -jar target/piraten-karpen-jar-with-dependencies.jar` either trace to activate or notrace to inactivate.
    * Example `java -jar target/piraten-karpen-jar-with-dependencies.jar random combo trace` makes the first player with random strategy, second player with combo strategy, and trace mode on.

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * Feature works in an integrated test with all other features as intended.

### Backlog 

| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01 | Creation of 42 game loops |  D  | 01/10/23 | 01/10/23 |
| x   | F02 | Update dice class to include score calculating functionality for the players | D |  01/11/23  |  01/11/23
| x   | F03 | Construction of turn method - how each turn of a player works |  D  |  01/11/23  |  01/11/23
| x   | F04 | Implementation of rolling 3-skulls to end turn | D |  01/11/23  |  01/11/23
| x   | F05 | Player keeping random dice at their turn | D |  01/11/23  | 01/11/23
| Y   | F06 | Calculation of wins per player and displaying win rate | D |  01/11/23  |  01/11/23
| Y   | F07 | Organizing player entities into pk file classes | D |  01/13/23  |  01/13/23
| Y   | F08 | Cleanup of repeated code into different classes - implement turn method in player class | D |  01/13/23  |  01/13/23
| Y   | F09 | Create Game class to play each game | D |  01/18/23  | 01/18/23
| Y   | F10 | Changing Piraten Karpen class main method to only include object declarations and method calls | D |  01/19/23  |  01/19/23
| Y   | F11 | Create Game class to play each game | D |  01/18/23  | 01/19/23
| Y   | F12 | Create hashmap tracking a players roll results | D |  01/19/23  | 01/20/23
| Y   | F13 | Re-implement random strategy to work with player's results hashmap | D |  01/20/23  | 01/20/23
| Y   | F14 | Implement combo strategy from random strategy framework | D |  01/21/23  | 01/22/23
| Y   | F15 | Create enum of card deck with NOPs and SeaBattle cards only with draw method| D |  01/28/23  | 01/28/23
| Y   | F16 | Create strategy for any player that gets a SeaBattle card | D |  01/28/23  | 01/28/23
| Y   | F17 | Add Monkey Business cards into card deck | D |  01/28/23  | 01/28/23
| Y   | F18 | Implement Monkey Business card effects into score calculation | D |  01/28/23  | 01/28/23
| ... | ... | ... |

