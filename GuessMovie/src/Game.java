import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Scanner scanner;
    private String movieName;
    private String guessedMovie;
    private String wrongLetters = "";
    private int numOFGuesses = 10;
    private boolean hasWon;
    private char userInput;

    Game (){
        movieName = getMovieName();
        movieName = movieName.toLowerCase();
        guessedMovie = movieName.replaceAll("[A-Za-z0-9]", "*"); // Replaces all characters in the string with an asterisk.
        System.out.println("Welcome to the movie guessing game.");
        System.out.println("You have " + numOFGuesses + " to guess the correct movie name shown below.");
        System.out.println(guessedMovie);
        System.out.println("Please enter lower case letters ONLY.");
    }


     // Method which reads the movie.txt file and coverts it into an Array of Strings.

    private String getMovieName () {
        int randomNumber = (int) (Math.random() * 26);
        String movies = "";

        try {
            File movieFile  = new File("movies.txt");
            scanner = new Scanner(movieFile);
        }
        catch (Exception e) {
            System.out.println("File not found");
        }

        List<String> movieList = new ArrayList<String>();

        while (scanner.hasNextLine()) {
            movies = scanner.nextLine();
            movieList.add(movies);
        }
        scanner.close();

        String [] movieArray = movieList.toArray(new String [0]);

        String randomMovie = movieArray [randomNumber];
        return randomMovie;
    }


     //Method which checks if the letter entered by the user matches any letter in the movie name which is being guessed.

    void checkLetter () {
        boolean correctLetter = false;
        scanner = new Scanner(System.in);
        userInput = scanner.next().toLowerCase().charAt(0);
        char newLetter = userInput;

        // Checks if movie contains the inputted letter and replaces the "*" with the letter.
        for (int i = 0; i < movieName.length(); i++) {
            if (movieName.charAt(i) == newLetter) {
                if (guessedMovie.charAt(i) == newLetter && !(hasWon)) {
                    System.out.println("You have already used the letter " + newLetter);
                }
                guessedMovie = replaceChar(guessedMovie, i, newLetter);
                correctLetter = true;
            }
        }
        System.out.println(guessedMovie);

        if (correctLetter) {        // If guess is correct.
            hasWon = true;
            for (int i = 0; i < movieName.length(); i++) {      // Check if there are 0 '*' in the string.
                if (guessedMovie.charAt(i) == '*') {            // If true, player has not won.
                    hasWon = false;
                }
            }
        } else {                     // If guess is wrong
            hasWon = false;
            for (int i = 0; i < wrongLetters.length(); i++) {
                if (wrongLetters.charAt(i) == newLetter) {      // Checks if a wrong letter is repeated.
                    System.out.println("You have already used " + newLetter + " which is wrong.");
                    correctLetter = true;
                    break;
                }
            }
            // Updates wrongLetter string when guess is wrong.
            if (!correctLetter) {
                wrongLetters = wrongLetters + newLetter + " ";
                numOFGuesses--;
                System.out.println("Guess was wrong.");
                System.out.println("You have " + numOFGuesses + " guesses left.");
            }
            System.out.println("Wrong Letters: " + wrongLetters);
        }
    }

    /**
     * Method which will replace the "*" in movie
     * if the user guesses a correct letter.
     * @param str the string which is being replaced.
     * @param index the position of the character in the string.
     * @param replaceChar the character which will replace the old in the movie name
     * @return random number in the range of 1 and 26.
     */

    private String replaceChar(String str, int index, char replaceChar) {
        if (str == null) {
            return str;
        }
        else if (index < 0 || index >= str.length()) {
            return str;
        }
        char [] movieChars = str.toCharArray();
        movieChars [index] = replaceChar;
        return String.valueOf(movieChars);
    }


    boolean endGame () {
        boolean gameOver = false;
        if (numOFGuesses == 0) gameOver = true;
        if (hasWon) gameOver = true;
        return gameOver;
    }

    void messages () {
        if (hasWon) {
            System.out.println("Congratulation!! You have won the game");
            System.out.println("You have guessed " + movieName.toLowerCase() + " correctly.");
        }
        else if (numOFGuesses == 0) {
            System.out.println("Sorry you are out of guesses.");
            System.out.println("The movie to be guessed was " + movieName.toLowerCase() + ".");
        }
    }
}