import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // Only 10 mistakes are allowed until the player loses.
    private static final int MAX_MISTAKES = 10;

    public static void main(String[] args) {

        //Read in the entire file into a comma separated string named text.
        StringBuilder text = new StringBuilder();

        // Create a new fileReader and pass it the movies.txt file
        try {
            FileReader fileReader = new FileReader("movies.txt");

            // Create a new bufferedReader for reading in the file
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read in each line of the file. If the line is null, then we've reached the end of the file.
            String line = bufferedReader.readLine();
            while (line != null) {
                //Use the indexOf method to check for apostrophes. If the index of the apostrophe equals -1 then no
                // apostrophe was found.
                //Concatenate the latest word with a comma to text.
                if (!line.contains("'")) text.append(line).append(",");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("ERROR: There was a problem reading the file.\n" + e.getMessage());
            System.exit(1);
        }

        // split the text on commas into a string array
        String[] titles = text.toString().split(",");

        // Randomly pick a movie title from the "movies.txt" file.
        // First create a new random number generator.
        Random random = new Random();
        // Then pick a random number between 0 and one less than the number of titles in the array. We go one less
        // because the last is blank since every title was followed by a comma.
        int randomNumber = random.nextInt(titles.length - 1);
        // This is the title the player will try to guess.
        String titleToGuess = titles[randomNumber];

        // Create an array of letters in the alphabet to keep track of which letters the player has guessed
        String[] lettersGuessed = new String[26];

        //Current index into lettersGuessed
        int lettersIndex = 0;

        // We need to keep track of the number of mistakes the player has made.
        int mistakes = 0;

        // Output simple rules for the player
        System.out.println("Guess the name of the film.");
        System.out.println("You have 10 lives.");
        System.out.println("Solve the puzzle before you run out of lives to win.");

        // Allow the player to guess a letter
        Scanner in = new Scanner(System.in);

        // Set a flag to track if the player has won
        boolean hasWon = false;

        // Create a temporary variable to store the player's guess
        CharSequence guess;

        // This is the main loop
        while (mistakes < getMaxMistakes() && !hasWon) {
            // Tell the player how many lives they have lost
            System.out.println("You have lost " + mistakes + " lives.");

            // display the title to guess. All unguessed letters are hidden by asterisks
            displayTitle(titleToGuess, lettersGuessed);


            // Display the letters guessed so far
            System.out.println("You have guessed: ");
            // Fix the lettersGuessed array so that null values are not printed to screen
            for (String aLettersGuessed : lettersGuessed) {
                if (aLettersGuessed != null)
                    System.out.print(aLettersGuessed + ", ");
            }

            // Wait for the player to type a guess.
            guess = in.next().toLowerCase();

            // Add the player's guess to the list of guessed letters
            lettersGuessed[lettersIndex] = guess.toString();
            lettersIndex++;

            // If the player guesses wrong, they lose a life
            if (!titleToGuess.toLowerCase().contains(guess)) {
                mistakes++;
                System.out.println("That is incorrect. The letter " + guess.toString() + " is not in the film title.");

            }
            // If player reveals the full title before they reach the limit of mistakes (10), they win.
            hasWon = gameWon(titleToGuess, lettersGuessed);

        }

        // If player mistakes reaches 10, they lose.
        if (hasWon) System.out.println("Well done! You've won the game by correctly guessing that the title is "
                + titleToGuess + "!");
        else {
            System.out.println("Too bad. You've lost. The correct answer is: " + titleToGuess + ".");
            System.out.println("Better luck next time.");
        }

    }

    // Returns true if every letter in titleToGuess is in the lettersGuessed array
    private static boolean gameWon(String titleToGuess, String[] lettersGuessed) {
        // Loop over each letter in titleToGuess
        for (int i = 0; i < titleToGuess.length(); i++) {
            // if lettersGuessed does not contain the character at i then return false
            char temp = titleToGuess.charAt(i);
            if (!stringArrayContains(lettersGuessed, temp)) return false;
        }
        return true;
    }

    // Compare the player's guess with the hidden title
    private static void displayTitle(String titleToGuess, String[] lettersGuessed) {
        // Print out the title to guess
        System.out.println("Title to guess: ");

        // Loop through the title and compare with the letters guessed array.
        for (int i = 0; i < titleToGuess.length(); i++) {
            // if lettersGuessed matches any letter in the title, then print the letters. Otherwise print an asterisk.
            char temp = titleToGuess.charAt(i);
            if (stringArrayContains(lettersGuessed, temp)) System.out.print(titleToGuess.charAt(i));
            else switch (temp) {
                case ' ':
                    System.out.print(" ");
                    break;
                case ':':
                    System.out.print(":");
                    break;
                case '-':
                    System.out.print("-");
                    break;
                default:
                    System.out.print("*");
                    break;
            }
        }
        System.out.println("\n");
    }

    // returns true if a letter is an element of the string array else returns false
    private static boolean stringArrayContains(String[] lettersGuessed, char temp) {
        for (String aLettersGuessed : lettersGuessed) {
            if (aLettersGuessed != null && aLettersGuessed.equalsIgnoreCase(String.valueOf(temp))) return true;
            if (aLettersGuessed != null && temp == ' ' || temp == ':' || temp == '-') return true;
        }
        return false;
    }

    private static int getMaxMistakes() {
        return MAX_MISTAKES;
    }
}


