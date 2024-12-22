package ge.bog.banking.Utils;

import java.util.Random;

public class RandomNumberGenerator {
    public  static String generateRandomCombination(int length) {
        Random random = new Random();
        StringBuilder combination = new StringBuilder();

        // Generate a random number combination
        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10);  // Generate a random digit between 0 and 9
            combination.append(randomNumber);
        }

        return combination.toString();
    }
    public static int generateRandomAccountpin() {
        Random random = new Random();

        // Generate a random 4-digit number between 1000 and 9999
        int pin = 1000 + random.nextInt(9000);  // random.nextInt(9000) gives a value between 0 and 8999
        return pin;
    }
}
