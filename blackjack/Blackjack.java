package blackjack;

import java.util.Scanner;

/**
 * @author HiddenMotives
 *
 * Simple Blackjack Game
 *
 * Released under the GNU General Public License v3.0
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */
public class Blackjack {

    static boolean dealers_turn = false; // Give first turn to player
    static Game BlackJack = new Game(5000);
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\033[H\033[2J");
            System.out.println("         Blackjack          |");
            System.out.println("-----------------------------");
            System.out.println();

            if (!(BlackJack.setBet(BlackJack.getBet()))) {
                BlackJack.showCashInfo();
                System.out.print("How much would you like to bet? $");
                BlackJack.setBet(input.nextInt());
            } else {
                BlackJack.showCashInfo();

                if (dealers_turn) {

                    BlackJack.showHandInfo(dealers_turn);

                    int dealer_total = BlackJack.getHandTotal(true); // Sum of Dealer Cards
                    int total = BlackJack.getHandTotal(false); // Sum of Player Cards

                    if ((dealer_total > 21) || (dealer_total == 21) || ((dealer_total == total) && (dealer_total >= 17)) || (dealer_total > total)) {
                        if (dealer_total > 21) {
                            System.out.println("Dealer Bust!");
                            BlackJack.roundWin();
                        } else if (dealer_total == 21) {
                            System.out.println("Dealer Blackjack!");
                            BlackJack.roundLose();

                        } else if ((dealer_total == total) && (dealer_total >= 17)) {
                            System.out.println("Push");
                            System.out.println("Balance: $" + BlackJack.getCash());
                            System.out.println("Seems like we got a tie, no change in your balance.");

                        } else if (dealer_total > total) {
                            System.out.println("Dealer Wins!");
                            BlackJack.roundLose();
                        }
                        dealers_turn = false;
                        BlackJack.reset();
                        System.out.println();
                        System.out.print("Press Enter to Continue...");
                        new Scanner(System.in).nextLine();
                    } else {
                        BlackJack.hitme(dealers_turn);
                        System.out.println("Dealer -> Hits : " + BlackJack.getHand(dealers_turn).get(BlackJack.getHand(dealers_turn).size() - 1));
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            System.out.println("Skipping...");
                        }
                    }

                } else {
                    BlackJack.showHandInfo(dealers_turn);

                    int total = BlackJack.getHandTotal(dealers_turn); // Sum of Player Cards

                    if ((total > 21) || (total == 21)) {
                        if (total > 21) {
                            System.out.println("Bust!");
                            BlackJack.roundLose();

                        } else if (total == 21) {
                            System.out.println("Blackjack!");
                            BlackJack.roundWin();
                        }

                        BlackJack.reset();
                        System.out.println();
                        System.out.print("Press Enter to Continue...");
                        new Scanner(System.in).nextLine();

                    } else {
                        System.out.print("(H)it Me, (S)tand, (Q)uit : ");
                        String stdin = input.next().toUpperCase();
                        if ("H".equals(stdin)) {
                            BlackJack.hitme(dealers_turn);
                        } else if ("S".equals(stdin)) {
                            dealers_turn = true;
                        } else if ("Q".equals(stdin)) {
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }
}
