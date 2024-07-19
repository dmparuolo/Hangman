

	import javax.swing.*;
	import java.util.Random;
	import java.awt.*;
	import java.awt.event.*;
	import java.util.ArrayList;

	public class HangmanGame extends JFrame implements ActionListener {

	    private JTextField userInputField;
	    private JLabel wordToGuessLabel;
	    private String secretWord;
	    private String hiddenWord;
	    private JLabel guessedLetters;
	    private String guesses;
	    private JPanel panel;
	    private String actualHiddenWord;
	    private int remainingAttempts;
	    ArrayList<Character> guessList;

	    public HangmanGame() {
	        setTitle("Hangman Game");
	        setSize(400, 200);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setLocationRelativeTo(null);

	        // Initialize the GUI components
	        wordToGuessLabel = new JLabel("");
	        guesses = "Guessed Letters: ";
	        guessedLetters = new JLabel(guesses);
	        userInputField = new JTextField(10);
	        JButton guessButton = new JButton("Guess");
	        guessButton.addActionListener(this);
	        
	        
	        // Layout components using a panel
	        panel = new JPanel();
	        panel.setLayout(new FlowLayout());
	        panel.add(wordToGuessLabel);
	        panel.add(new JLabel("Enter a letter:"));
	        panel.add(userInputField);
	        panel.add(guessButton);
	        panel.add(guessedLetters);
	        

	        // Add the panel to the frame
	        
	        add(panel);

	        // Initialize the game
	        initializeGame();

	        setVisible(true);
	    }

	    private void initializeGame() {
	        // Initialize the secret word and hidden word
	    	
	    	String[] dictionary = {
	                "soccer", "basketball", "tennis", "volleyball", "baseball", 
	                "football", "hockey", "golf", "swimming", "cycling",
	                "athletics", "boxing", "wrestling", "surfing", "skiing",
	                "badminton", "rugby", "squash", "karate", "judo",
	                "archery", "rowing", "sailing", "climbing", "skateboarding",
	                "snowboarding", "biking", "triathlon", "canoeing", "fencing",
	                "handball", "kickboxing", "paddleboarding", "kayaking", "paragliding",
	                "bungee", "skydiving", "rock", "mountaineering", "paintball",
	                "skating", "roller", "bobsleigh", "figure", "luge",
	                "biathlon", "curling", "polo", "powerlifting", "bodybuilding",
	                "weightlifting", "wakeboarding", "windsurfing", "fishing", "hunting",
	                "shooting", "decathlon", "pentathlon", "skeleton", "bouldering",
	                "kiteboarding", "snowshoeing", "wushu", "sambo", "taekwondo",
	                "sumo", "jujitsu", "ninjutsu", "muay", "kendo",
	                "kung", "capoeira", "aikido", "hapkido", "bokator",
	                "savate", "krav", "jeet", "parkour", "slacklining",
	                "hiking", "caving", "mountain", "crossfit", "canyoning",
	                "base", "freediving", "snorkeling", "spearfishing", "sailing",
	                "rafting", "surfing", "windsurfing", "kitesurfing", "paragliding"
	                };
	    	
	    	Random rand = new Random();
	        int randomIndex = rand.nextInt(dictionary.length);
	        secretWord =  dictionary[randomIndex];
	        
	        hiddenWord = "";
	        
	        actualHiddenWord = "";
	        
	        guesses = "Guessed Letters: ";
	        
	        guessList = new ArrayList<>();
	        
	        for (int i = 0; i < secretWord.length(); i++) {
	        	hiddenWord += "_ ";
	        	actualHiddenWord += "_";
	        }

	        // Set the remaining attempts
	        remainingAttempts = 7;

	        // Update the displayed word
	        updateDisplayedWord();
	    }

	    private void updateDisplayedWord() {
	        wordToGuessLabel.setText(hiddenWord);
	        guessedLetters.setText(guesses);
	    }

		@Override
	    public void actionPerformed(ActionEvent e) {
	        if (e.getActionCommand().equals("Guess")) {
	            String userInput = userInputField.getText().toLowerCase().trim();
	            if (userInput.length() != 1 || !Character.isLetter(userInput.charAt(0))) {
	                JOptionPane.showMessageDialog(this, "Please enter a valid single letter.");
	            } else {
	                char guessedLetter = userInput.charAt(0);
	                boolean letterFound = false;
	                boolean alreadyGuessed = false;
	                if (guessList.contains(guessedLetter)) {
	                	JOptionPane.showMessageDialog(this, "This letter was already guessed, please guess again.");
	                	alreadyGuessed = true;
	                } else {
	                	guesses += guessedLetter + ", ";
	                	guessList.add(guessedLetter);
	                }
	                

	                // Check if the guessed letter is in the secret word
	                if (!alreadyGuessed) {
	                	for (int i = 0; i < secretWord.length(); i++) {
	                		if (secretWord.charAt(i) == guessedLetter) {
	                			actualHiddenWord = actualHiddenWord.substring(0,i) + secretWord.charAt(i) + actualHiddenWord.substring(i+1);
	                			letterFound = true;
	                		}
	                	}


	                	hiddenWord = "";
	                	for (int i = 0; i < actualHiddenWord.length(); i++) {
	                		hiddenWord += actualHiddenWord.charAt(i) + " ";
	                	}

	                	if (!letterFound) {
	                		remainingAttempts--;
	                		JOptionPane.showMessageDialog(this, "Incorrect guess! " + remainingAttempts + " attempts remaining.");
	                	}

	                	// Update the displayed word
	                	updateDisplayedWord();

	                	// Check if the game is won
	                	if (hiddenWord.indexOf("_") == -1) {
	                		JOptionPane.showMessageDialog(this, "Congratulations! You've guessed the word.");
	                		initializeGame(); // Restart the game
	                	}

	                	// Check if the game is lost
	                	if (remainingAttempts == 0) {
	                		JOptionPane.showMessageDialog(this, "Game over! The word was: " + secretWord);
	                		initializeGame(); // Restart the game
	                	}
	                }

	                // Clear the input field
	                userInputField.setText("");
	            }
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(HangmanGame::new);
	    }
	}

