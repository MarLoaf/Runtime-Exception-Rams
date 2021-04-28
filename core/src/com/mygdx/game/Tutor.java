package com.mygdx.game;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.views.AchievementsScreen;
import com.mygdx.game.views.CreateAccountScreen;
import com.mygdx.game.views.HomeScreen;
import com.mygdx.game.views.LoadingScreen;
import com.mygdx.game.views.LoginScreen;
import com.mygdx.game.views.PasswordResetScreen;
import com.mygdx.game.views.ProblemEntryScreen;
import com.mygdx.game.views.ProblemScreen;
import com.mygdx.game.views.RecoverAccountScreen;
import com.mygdx.game.views.ResultsScreen;
import com.mygdx.game.views.TutorialScreen;

public class Tutor extends Game {

	private AchievementsScreen achievementsScreen;
	private CreateAccountScreen createAccountScreen;
	private HomeScreen homeScreen;
	private LoadingScreen loadingScreen;
	private LoginScreen loginScreen;
	private PasswordResetScreen passwordResetScreen;
	private ProblemEntryScreen problemEntryScreen;
	private ProblemScreen problemScreen;
	private RecoverAccountScreen recoverAccountScreen;
	private ResultsScreen resultsScreen;
	private TutorialScreen tutorialScreen;

	public String gradeSelection = "";
	public String topicSelection = "";
	public String lessonSelection = "";
	public int answerCounter = 0;
	public int problemNumber = 0;
	//public Problem[] problems = {new Problem("5 - 3 = ?", "2"), new Problem("2 + 2 = ?", "4"), new Problem("3 + 2 = ?", "5", new String[] {"4","3","6"}), new Problem("If each student has 3 apples, how many apples do 5 students have", "15"), new Problem("You have 2 oranges, Lisa has 7 oranges, how many oranges do you have together","9", new String[] {"8","10","7"})};
	public ArrayList<Problem> problems = new ArrayList<Problem>(0);
	
	/*problem sets
	public Problem[] kindergartenCounting;
	public Problem[] kindergartenOperations;
	public Problem[] kindergartenNumbers;
	public Problem[] kindergartenMeasurements;

	public Problem[] grade1Operations;
	public Problem[] grade1Numbers;
	public Problem[] grade1Measurements;

	public Problem[] grade2Operations;
	public Problem[] grade2Numbers;
	public Problem[] grade2Measurements;

	public Problem[] grade3Operations;
	public Problem[] grade3Numbers;
	public Problem[] grade3Fractions;
	public Problem[] grade3Measurements;

	public Problem[] grade4Operations;
	public Problem[] grade4Numbers;
	public Problem[] grade4Fractions = {new Problem("Which of the following is an improper fraction?", "7/3", new String[] {"3/15", "3/7", "1/2"})};
	public Problem[] grade4Measurements;
	*/
	
	public ArrayList<Account> accounts = new ArrayList<Account>(0);
	//public Account currentUser = new Account("","","","","Test User");
	public Account currentUser;
	
	public final static int ACHIEVEMENTS = 0;
	public final static int CREATEACCOUNT = 1;
	public final static int HOME = 2;
	public final static int LOGIN = 3;
	public final static int PASSRESET = 4;
	public final static int PROBLEMENTRY = 5;
	public final static int PROBLEM = 6;
	public final static int RECOVERACC = 7;
	public final static int RESULTS = 8;
	public final static int TUTORIAL = 9;
	
	private final String key = "xxW/O4V6rjg=";
	
	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}
	
	public void changeScreen(int screen) {
		switch(screen) {
		case ACHIEVEMENTS:
			//if(achievementsScreen == null)
			achievementsScreen = new AchievementsScreen(this);
			this.setScreen(achievementsScreen);
			break;
		case CREATEACCOUNT:
			//if(createAccountScreen == null)
			createAccountScreen = new CreateAccountScreen(this);
			this.setScreen(createAccountScreen);
			break;
		case HOME:
			//if(homeScreen == null)
			homeScreen = new HomeScreen(this);
			this.setScreen(homeScreen);
			break;
		case LOGIN:
			//if(loginScreen == null)
			loginScreen = new LoginScreen(this);
			this.setScreen(loginScreen);
			break;
		case PASSRESET:
			//if(passwordResetScreen == null)
			passwordResetScreen = new PasswordResetScreen(this);
			this.setScreen(passwordResetScreen);
			break;
		case PROBLEMENTRY:
			//if(problemEntryScreen == null)
			problemEntryScreen = new ProblemEntryScreen(this);
			this.setScreen(problemEntryScreen);
			break;
		case PROBLEM:
			//if(problemScreen == null) 
			problemScreen = new ProblemScreen(this);
			this.setScreen(problemScreen);
			break;
		case RECOVERACC:
			//if(recoverAccountScreen == null)
			recoverAccountScreen = new RecoverAccountScreen(this);
			this.setScreen(recoverAccountScreen);
			break;
		case RESULTS:
			//if(resultsScreen == null)
			resultsScreen = new ResultsScreen(this);
			this.setScreen(resultsScreen);
			break;
		case TUTORIAL:
			//if(tutorialScreen == null)
			tutorialScreen = new TutorialScreen(this);
			this.setScreen(tutorialScreen);
			break;
		}
	}
	
	public boolean checkDuplicateUsername(String username) {
		if (accounts.size()>0) {
			for (Account a : accounts) {
				if (a.getUsername().equals(username)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean getAccountViaSecret(String username, String secretAnswer) {
		if (accounts.size()>0) {
			for (Account a : accounts) {
				if (a.getUsername().equals(username) && a.getSecretAnswer().equals(secretAnswer)) {
					currentUser = a;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean loginAccount(String username, String password) {
		if (accounts.size()>0) {
			for (Account a : accounts) {
				if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
					currentUser = a;
					return true;
				}
			}
		}
		return false;
	}
	
	public void addAccountSecure(String username, String password, String secretQuestion, String secretAnswer, String fullName) {
		//adds account to database, secure version
		Account newAcc = new Account(username, password, secretQuestion, secretAnswer, fullName);
		if (!checkDuplicateUsername(username)) writeAccountSecure(newAcc);
	}
	
	public void readAccountsSecure() {
		//reads accounts from the DoNotEdit.txt file in assets into the accounts variable, secure version
		String accs;
		FileHandle file = Gdx.files.local("DoNotEdit.txt");
		accs = decrypt(file.readString());
		if(!accs.equals("")) {
			String[] splitAccs = accs.split("\\.");
			String[] splitAccParts;
			for(int i = 0; i < splitAccs.length; i++) {
				splitAccParts = splitAccs[i].split(",");
				if(!checkDuplicateUsername(splitAccParts[0])) {
					accounts.add(new Account(splitAccParts[0],splitAccParts[1],splitAccParts[2],splitAccParts[3],splitAccParts[4],Integer.parseInt(splitAccParts[5]),Integer.parseInt(splitAccParts[6]),Integer.parseInt(splitAccParts[7]),Integer.parseInt(splitAccParts[8]),Integer.parseInt(splitAccParts[9]),Integer.parseInt(splitAccParts[10]),Integer.parseInt(splitAccParts[11]),Integer.parseInt(splitAccParts[12]),Integer.parseInt(splitAccParts[13]),Integer.parseInt(splitAccParts[14]),Integer.parseInt(splitAccParts[15]),Integer.parseInt(splitAccParts[16]),Integer.parseInt(splitAccParts[17]),Integer.parseInt(splitAccParts[18]),Integer.parseInt(splitAccParts[19]),Integer.parseInt(splitAccParts[20]),Integer.parseInt(splitAccParts[21]),Integer.parseInt(splitAccParts[22]),Integer.parseInt(splitAccParts[23]),Integer.parseInt(splitAccParts[24]),Integer.parseInt(splitAccParts[25]),Integer.parseInt(splitAccParts[26]),Integer.parseInt(splitAccParts[27]), new String[] {splitAccParts[28],splitAccParts[29],splitAccParts[30],splitAccParts[31],splitAccParts[32],splitAccParts[33]}));
				}
			}
		}
	}
	
	public void writeAccountSecure(Account a) {
		//adds an account to the DoNotEdit.txt file, secure version
		accounts.add(a);
		rewriteAccountsSecure();
		readAccountsSecure();
	}
	
	public void updateAccountSecure(Account a) {
		//used to update all account info for a specified account, secure version
		readAccountsSecure();
		if (accounts.size()>0) {
			for (int i = 0; i < accounts.size(); i++) {
				if (accounts.get(i).getUsername().equals(a.getUsername())) {
					accounts.set(i, a);
				}
			}
		}
		rewriteAccountsSecure();
		readAccountsSecure();
	}
	
	public void rewriteAccountsSecure() {
		//rewrites all accounts from the accounts variable into the DoNotEdit.txt file (overwriting the file), secure version
		FileHandle file = Gdx.files.local("DoNotEdit.txt");
		file.writeString("",false);
		String toWrite = "";
		String encrypted;
		if (accounts.size()>0) {
			for (Account a : accounts) {
				if(!toWrite.equals("")) {
					toWrite += ".";
				}
				toWrite += a.getUsername() + "," + a.getPassword() + "," + a.getSecretQuestion() + "," + a.getSecretAnswer() + "," + a.getFullName() + "," + a.getkindergartenCounting() + "," + a.getkindergartenOperations() + "," + a.getkindergartenNumbers() + "," + a.getkindergartenMeasurements() + "," + a.getkindergartenExam() + "," + a.getGrade1Operations() + "," + a.getGrade1Numbers() + "," + a.getGrade1Measurements() + "," + a.getGrade1Exam() + "," + a.getGrade2Operations() + "," + a.getGrade2Numbers() + "," + a.getGrade2Measurements() + "," + a.getGrade2Exam() + "," + a.getGrade3Operations() + "," + a.getGrade3Numbers() + "," + a.getGrade3Fractions() + "," + a.getGrade3Measurements() + "," + a.getGrade3Exam() + "," + a.getGrade4Operations() + "," + a.getGrade4Numbers() + "," + a.getGrade4Fractions() + "," + a.getGrade4Measurements() + "," + a.getGrade4Exam() + "," + a.getLatestAchievements()[0] + "," + a.getLatestAchievements()[1] + "," + a.getLatestAchievements()[2] + "," + a.getLatestAchievements()[3] + "," + a.getLatestAchievements()[4] + "," + a.getLatestAchievements()[5];
			}
		}
		encrypted = encrypt(toWrite);
		file.writeString(encrypted, true);
	}
	
	private String encrypt(String originalString) {
		//encrypts a string, returns the encrypted string if successful, returns -1 if failed
		try{
			SecretKey myKey = new SecretKeySpec(Base64.getDecoder().decode(key), 0, Base64.getDecoder().decode(key).length, "DES");
			Cipher desCipher = Cipher.getInstance("DES");
			desCipher.init(Cipher.ENCRYPT_MODE, myKey);
			byte[] textDecrypted = originalString.getBytes("UTF-8");
			byte[] textEncrypted = desCipher.doFinal(textDecrypted);
			byte[] textEncoded = Base64.getEncoder().encode(textEncrypted);
			String s = new String(textEncoded);
			return s;
			}catch(Exception e) {
				System.out.println("Encryption Exception");
			}
		return "-1";
	}
	
	private String decrypt(String encryptedString) {
		//decrypts a string, returns the decrypted string if successful, returns -1 if failed
		try{
			SecretKey myKey = new SecretKeySpec(Base64.getDecoder().decode(key), 0, Base64.getDecoder().decode(key).length, "DES");
			Cipher desCipher = Cipher.getInstance("DES");
			desCipher.init(Cipher.DECRYPT_MODE, myKey);
			byte[] textEncrypted = Base64.getDecoder().decode(encryptedString.getBytes());
			byte[] textDecrypted = desCipher.doFinal(textEncrypted);
			String s = new String(textDecrypted, "UTF-8");
			return s;
			}catch(Exception e) {
				System.out.println("Decryption Exception");
			}
		return "-1";
	}
	
	public void generateProblems() {
		problems.clear();
		for(int i=0; i<5; i++) {
			//generates 5 problems
			problems.add(problemGenerator(gradeSelection, topicSelection));
		}
	}
	
	public Problem problemGenerator(String grade, String topic) {
		Problem randProblem = new Problem();
		int numberRange = 10;
		int operationRange = 2;
		Random random = new Random();
		int randNumber = 0;
		String problemText = "";
		String correctAnswer = "";
		String wrongAnswers[] = new String[3];
		int number1 = 0;
		String operator = "";
		int number2 = 0;
		int answer = 0;
		int wrongAnswersInts[] = new int[3];
		ArrayList<Integer> possibleNumbers;
		switch(grade) {
		case "Kindergarten":
			numberRange = 10;
			operationRange = 2;
			break;
		case "1st Grade":
			numberRange = 100;
			operationRange = 2;
			break;
		case "2nd Grade":
			numberRange = 100;
			operationRange = 4;
			break;
		case "3rd Grade":
			numberRange = 1000;
			operationRange = 4;
			break;
		case "4th Grade":
			numberRange = 1000000;
			operationRange = 4;
			break;
		}
		randNumber = random.nextInt(operationRange);
		switch(randNumber) {
		case 0:
			//addition
			operator = "+";
			answer = random.nextInt(numberRange)+1;
			number1 = random.nextInt(answer);
			number2 = answer-number1;
			randNumber = answer;
			for (int i=0; i<3; i++) {
				while(randNumber==answer||randNumber==wrongAnswersInts[0]||randNumber==wrongAnswersInts[1]||randNumber==wrongAnswersInts[2]) {
					randNumber = random.nextInt(numberRange)+1;
				}
				wrongAnswersInts[i]=randNumber;
				randNumber = answer;
			}
			break;
		case 1:
			//subtraction
			operator = "-";
			number1 = random.nextInt(numberRange)+1;
			number2 = random.nextInt(number1);
			answer = number1-number2;
			randNumber = answer;
			for (int i=0; i<3; i++) {
				while(randNumber==answer||randNumber==wrongAnswersInts[0]||randNumber==wrongAnswersInts[1]||randNumber==wrongAnswersInts[2]) {
					randNumber = random.nextInt(numberRange)+1;
				}
				wrongAnswersInts[i]=randNumber;
				randNumber = answer;
			}
			break;
		case 2:
			//multiplication
			operator = "x";
			answer = random.nextInt(numberRange)+1;
			possibleNumbers = findDivisors(answer);
			randNumber = random.nextInt(possibleNumbers.size());
			number1 = possibleNumbers.remove(randNumber);
			number2 = answer/number1;
			randNumber = answer;
			for (int i=0; i<3; i++) {
				while(randNumber==answer||randNumber==wrongAnswersInts[0]||randNumber==wrongAnswersInts[1]||randNumber==wrongAnswersInts[2]) {
					randNumber = random.nextInt(numberRange)+1;
				}
				wrongAnswersInts[i]=randNumber;
				randNumber = answer;
			}
			break;
		case 3:
			//division
			operator = ":";
			number1 = random.nextInt(numberRange)+1;
			possibleNumbers = findDivisors(number1);
			randNumber = random.nextInt(possibleNumbers.size());
			number2 = possibleNumbers.remove(randNumber);
			answer = number1/number2;
			randNumber = answer;
			for (int i=0; i<3; i++) {
				while(randNumber==answer||randNumber==wrongAnswersInts[0]||randNumber==wrongAnswersInts[1]||randNumber==wrongAnswersInts[2]) {
					randNumber = random.nextInt(numberRange)+1;
				}
				wrongAnswersInts[i]=randNumber;
				randNumber = answer;
			}
			break;
		}
		//word problem or not
		randNumber = random.nextInt(2);
		if(randNumber == 0 && number1!=0 && number2!=0) {
			//word problem (doesn't allow operands to be 0 because then the word problem seems weird)
			String[] names = new String[] {"John", "Sarah", "Alex", "Mario", "Alexa", "Mario", "Mark", "Maria"};
			String[] objects = new String[] {"apple", "orange", "lemon", "coin"};
			String name1 = "";
			randNumber = random.nextInt(names.length);
			name1 = names[randNumber];
			String name2 = name1;
			while (name1.equals(name2)) {
				randNumber = random.nextInt(names.length);
				name2 = names[randNumber];
			}
			String object1;
			String object2;
			String objectPlural;
			randNumber = random.nextInt(objects.length);
			object1 = objects[randNumber];
			object2 = objects[randNumber];
			objectPlural = objects[randNumber] + "s";
			if (number1>1) {
				object1 = objectPlural;
			}
			if (number2>1) {
				object2 = objectPlural;
			}
			switch(operator) {
			case "+":
				problemText = name1 + " has " + number1 + " " + object1 + " and " + name2 + " has " + number2 + " " + object2 + ", how many " + objectPlural + " do they have together?";
				break;
			case "-":
				problemText = name1 + " has " + number1 + " " + object1 + ", " + name2 + " took away " + number2 + " " + object2 + ", how many " + objectPlural + " does " + name1 + " have left?";
				break;
			case "x":
				problemText = number1 + " " + operator + " " + number2;
				break;
			case ":":
				problemText = number1 + " " + operator + " " + number2;
				break;
			}
		}else {
			//not word problem
			problemText = number1 + " " + operator + " " + number2;
		}
		correctAnswer = "" + answer;
		wrongAnswers = new String[] {"" + wrongAnswersInts[0], "" + wrongAnswersInts[1], "" + wrongAnswersInts[2]};
		//multiple choice or not
		randNumber = random.nextInt(2);
		if(randNumber == 0) {
			//multiple choice
			randProblem = new Problem(problemText, correctAnswer, wrongAnswers);
		}else {
			//not multiple choice
			randProblem = new Problem(problemText, correctAnswer);
		}
		return randProblem;
	}
	
	private ArrayList<Integer> findDivisors(int x) {
		ArrayList<Integer> output = new ArrayList<Integer>(0);
		for (int i=1; i<=Math.sqrt(x); i++) {
			if (x%i==0) {
				if (x/i==i) output.add(i);
				else {
					output.add(i);
					output.add(x/i);
				}
			}
		}
		return output;
	}
}