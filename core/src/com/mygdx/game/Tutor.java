package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	public ArrayList<Problem> problems = new ArrayList<Problem>(0);
	public ArrayList<Account> accounts = new ArrayList<Account>(0);
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
	
	public String getAccountSecretQuestion(String username) {
		if (accounts.size()>0) {
			for (Account a : accounts) {
				if (a.getUsername().equals(username)) {
					return a.getSecretQuestion();
				}
			}
		}
		return "Enter your username to see the secret question";
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
		//practice - 10 questions (ungraded)
		//test - 10 questions
		//exam 5 per topic
		if (lessonSelection.equals("Exam")) {
			//exam is all topics in the grade
			if(gradeSelection.equals("Kindergarten")) {
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Counting"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Operations"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Numbers"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Measurements"));
				}
			}
			else if(gradeSelection.equals("1st Grade")) {
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Operations"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Numbers"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Measurements"));
				}
			}
			else if(gradeSelection.equals("2nd Grade")) {
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Operations"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Numbers"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Measurements"));
				}
			}
			else if(gradeSelection.equals("3rd Grade")) {
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Operations"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Numbers"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Fractions"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Measurements"));
				}
			}
			else if(gradeSelection.equals("4th Grade")) {
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Operations"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Numbers"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Fractions"));
				}
				for(int i=0; i<5; i++) {
					problems.add(problemGenerator(gradeSelection, "Measurements"));
				}
			}
		}else {
			//practice and tests are all 1 topic
			for(int i=0; i<10; i++) {
				problems.add(problemGenerator(gradeSelection, topicSelection));
			}
		}
	}
	
	public Problem problemGenerator(String grade, String topic) {
		switch(topic) {
		case "Counting":
			return generateCountingProblem();
		case "Operations":
			return generateOperationsProblem(grade);
		case "Numbers":
			return generateNumbersProblem(grade);
		case "Measurements":
			return generateMeasurementsProblem(grade);
		case "Fractions":
			return generateFractionsProblem(grade);
		}
		return new Problem();
	}
	
	private Problem generateCountingProblem() {
		//no parameters since this is always Kindergarten
		Problem countingProblem = new Problem();
		String problemText = "";
		String correctAnswer = "";
		String wrongAnswers[] = new String[3];
		String countingObject = "";
		Image countingImage = new Image(new Texture(Gdx.files.internal("images/1Apple.png")));
		int answer = 0;
		int wrongAnswersInts[] = new int[3];
		int numberRange = 10;
		Random random = new Random();
		int randNumber = random.nextInt(numberRange-1)+1; //random number from 1 to 9
		int randObject = random.nextInt(3); //random number from 0 to 2
		switch (randObject) {
		case 0:
			//apples
			problemText = "How many apples are there?";
			countingObject = "apple";
			switch (randNumber) {
			case 1:
				answer = 1;
				countingImage = new Image(new Texture(Gdx.files.internal("images/1Apple.png")));
				break;
			case 2:
				answer = 2;
				countingImage = new Image(new Texture(Gdx.files.internal("images/2Apple.png")));
				break;
			case 3:
				answer = 3;
				countingImage = new Image(new Texture(Gdx.files.internal("images/3Apple.png")));
				break;
			case 4:
				answer = 4;
				countingImage = new Image(new Texture(Gdx.files.internal("images/4Apple.png")));
				break;
			case 5:
				answer = 5;
				countingImage = new Image(new Texture(Gdx.files.internal("images/5Apple.png")));
				break;
			case 6:
				answer = 6;
				countingImage = new Image(new Texture(Gdx.files.internal("images/6Apple.png")));
				break;
			case 7:
				answer = 7;
				countingImage = new Image(new Texture(Gdx.files.internal("images/7Apple.png")));
				break;
			case 8:
				answer = 8;
				countingImage = new Image(new Texture(Gdx.files.internal("images/8Apple.png")));
				break;
			case 9:
				answer = 9;
				countingImage = new Image(new Texture(Gdx.files.internal("images/9Apple.png")));
				break;
			}
			break;
		case 1:
			//oranges
			problemText = "How many oranges are there?";
			countingObject = "orange";
			switch (randNumber) {
			case 1:
				answer = 1;
				countingImage = new Image(new Texture(Gdx.files.internal("images/1Orange.png")));
				break;
			case 2:
				answer = 2;
				countingImage = new Image(new Texture(Gdx.files.internal("images/2Orange.png")));
				break;
			case 3:
				answer = 3;
				countingImage = new Image(new Texture(Gdx.files.internal("images/3Orange.png")));
				break;
			case 4:
				answer = 4;
				countingImage = new Image(new Texture(Gdx.files.internal("images/4Orange.png")));
				break;
			case 5:
				answer = 5;
				countingImage = new Image(new Texture(Gdx.files.internal("images/5Orange.png")));
				break;
			case 6:
				answer = 6;
				countingImage = new Image(new Texture(Gdx.files.internal("images/6Orange.png")));
				break;
			case 7:
				answer = 7;
				countingImage = new Image(new Texture(Gdx.files.internal("images/7Orange.png")));
				break;
			case 8:
				answer = 8;
				countingImage = new Image(new Texture(Gdx.files.internal("images/8Orange.png")));
				break;
			case 9:
				answer = 9;
				countingImage = new Image(new Texture(Gdx.files.internal("images/9Orange.png")));
				break;
			}
			break;
		case 2:
			//lemons
			problemText = "How many lemons are there?";
			countingObject = "lemon";
			switch (randNumber) {
			case 1:
				answer = 1;
				countingImage = new Image(new Texture(Gdx.files.internal("images/1Lemon.png")));
				break;
			case 2:
				answer = 2;
				countingImage = new Image(new Texture(Gdx.files.internal("images/2Lemon.png")));
				break;
			case 3:
				answer = 3;
				countingImage = new Image(new Texture(Gdx.files.internal("images/3Lemon.png")));
				break;
			case 4:
				answer = 4;
				countingImage = new Image(new Texture(Gdx.files.internal("images/4Lemon.png")));
				break;
			case 5:
				answer = 5;
				countingImage = new Image(new Texture(Gdx.files.internal("images/5Lemon.png")));
				break;
			case 6:
				answer = 6;
				countingImage = new Image(new Texture(Gdx.files.internal("images/6Lemon.png")));
				break;
			case 7:
				answer = 7;
				countingImage = new Image(new Texture(Gdx.files.internal("images/7Lemon.png")));
				break;
			case 8:
				answer = 8;
				countingImage = new Image(new Texture(Gdx.files.internal("images/8Lemon.png")));
				break;
			case 9:
				answer = 9;
				countingImage = new Image(new Texture(Gdx.files.internal("images/9Lemon.png")));
				break;
			}
			break;
		}
		randNumber = answer;
		for (int i=0; i<3; i++) {
			while(randNumber==answer||randNumber==wrongAnswersInts[0]||randNumber==wrongAnswersInts[1]||randNumber==wrongAnswersInts[2]) {
				randNumber = random.nextInt(numberRange-1)+1;
			}
			wrongAnswersInts[i]=randNumber;
			randNumber = answer;
		}
		correctAnswer = "" + answer;
		wrongAnswers = new String[] {"" + wrongAnswersInts[0], "" + wrongAnswersInts[1], "" + wrongAnswersInts[2]};
		//multiple choice or not
		randNumber = random.nextInt(2);
		if(randNumber == 0) {
			//multiple choice
			countingProblem = new Problem(problemText, correctAnswer, wrongAnswers, countingImage, countingObject);
		}else {
			//not multiple choice
			countingProblem = new Problem(problemText, correctAnswer, countingImage, countingObject);
		}
		return countingProblem;
	}
	
	private Problem generateOperationsProblem(String grade) {
		//generates an operations problem with range of numbers and operators based on grade
		Problem operationsProblem = new Problem();
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
		boolean goodNumbers = false;
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
			while (!goodNumbers) {
				answer = random.nextInt(numberRange)+1;
				possibleNumbers = findDivisors(answer);
				possibleNumbers.removeAll(Arrays.asList(1));
				possibleNumbers.removeAll(Arrays.asList(answer));
				if (possibleNumbers.size()>1) {
					goodNumbers = true;
				}
			}
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
			operator = "/";
			number1 = random.nextInt(numberRange)+1;
			possibleNumbers = findDivisors(number1);
			while (!goodNumbers) {
				number1 = random.nextInt(numberRange)+1;
				possibleNumbers = findDivisors(number1);
				possibleNumbers.removeAll(Arrays.asList(1));
				if (possibleNumbers.size()>1) {
					goodNumbers = true;
				}
			}
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
			String[] objects = new String[] {"apple", "orange", "lemon"};
			String name1 = "";
			String pronoun1 = "he";
			String pronoun2 = "his";
			randNumber = random.nextInt(names.length);
			name1 = names[randNumber];
			if (name1.equals("Sarah")||name1.equals("Alexa")||name1.equals("Maria")) {
				pronoun1 = "she";
				pronoun2 = "her";
			}
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
				problemText = number1 + " friends give " + name1 + " " + number2 + " " + objectPlural + ", how many " + objectPlural + " does " + name1 + " have now?";
				break;
			case "/":
				problemText = name1 + " has " + number1 + " " + objectPlural + ", " + pronoun1 + " split the " + objectPlural + " between " + number2 + " of " + pronoun2 + " friends, how many " + objectPlural + " do " + pronoun2 + " friends have now?";
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
			operationsProblem = new Problem(problemText, correctAnswer, wrongAnswers);
		}else {
			//not multiple choice
			operationsProblem = new Problem(problemText, correctAnswer);
		}
		return operationsProblem;
	}
	
	private Problem generateNumbersProblem(String grade) {
		//factoring problem
		Problem numbersProblem = new Problem();
		int numberRange = 10;
		Random random = new Random();
		int randNumber = 0;
		String problemText = "";
		String correctAnswer = "";
		String wrongAnswers[] = new String[3];
		int number = 0;
		String factor = "ones";
		int factorInt = 1;
		int answer = 0;
		int wrongAnswersInts[] = new int[3];
		String factors[] = new String[] {"ones", "tens", "hundreds", "thousands", "millions"};
		switch(grade) {
		case "Kindergarten":
			numberRange = 10;
			break;
		case "1st Grade":
			numberRange = 100;
			break;
		case "2nd Grade":
			numberRange = 1000;
			break;
		case "3rd Grade":
			numberRange = 10000;
			break;
		case "4th Grade":
			numberRange = 10000000;
			break;
		}
		number = random.nextInt(numberRange);
		if (number<10) {
			factor = factors[0];
		}else if (number<100) {
			randNumber = random.nextInt(2);
			factor = factors[randNumber];
		}else if (number<1000) {
			randNumber = random.nextInt(3);
			factor = factors[randNumber];
		}else if (number<1000000) {
			randNumber = random.nextInt(4);
			factor = factors[randNumber];
		}else if (number<10000000) {
			randNumber = random.nextInt(5);
			factor = factors[randNumber];
		}
		switch (factor) {
		case "ones":
			factorInt = 1;
			break;
		case "tens":
			factorInt = 10;
			break;
		case "hundreds":
			factorInt = 100;
			break;
		case "thousands":
			factorInt = 1000;
			break;
		case "millions":
			factorInt = 1000000;
			break;
		}
		problemText = "How many " + factor + " fit into " + number + "?";
		answer = number/factorInt;
		randNumber = answer;
		for (int i=0; i<3; i++) {
			while(randNumber==answer||randNumber==wrongAnswersInts[0]||randNumber==wrongAnswersInts[1]||randNumber==wrongAnswersInts[2]) {
				randNumber = random.nextInt(numberRange/factorInt);
			}
			wrongAnswersInts[i]=randNumber;
			randNumber = answer;
		}
		correctAnswer = "" + answer;
		wrongAnswers = new String[] {"" + wrongAnswersInts[0], "" + wrongAnswersInts[1], "" + wrongAnswersInts[2]};
		//multiple choice or not
		randNumber = random.nextInt(2);
		if(randNumber == 0) {
			//multiple choice
			numbersProblem = new Problem(problemText, correctAnswer, wrongAnswers);
		}else {
			//not multiple choice
			numbersProblem = new Problem(problemText, correctAnswer);
		}
		return numbersProblem;
	}
	
	private Problem generateMeasurementsProblem(String grade) {
		Problem measurementsProblem = new Problem();
		
		int typesOfProblems = 0;
		Random rand = new Random();
		int randProblemChooser = 0;
		int randImageChooser = 0;
		Image measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test1.png")));	//TODO need images to fill
		int measurment1 = 0;
		Image measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test1.png")));	//TODO need car images, building images, people, etc
		int measurment2 = 0;
		int prevImage = 0;
		boolean selectedImage = false;
		String problemText = "";
		String correctAnswer = "";
		String[] wa = new String[1];
		
		switch(grade) {
		case "Kindergarten":
			
			randProblemChooser = rand.nextInt(4)+1;		//4 types of subproblems
			
			switch(randProblemChooser) {
			case 1:
				//generates "Which car is longer questions"
				problemText = "Which car is longer?";
				randImageChooser = rand.nextInt(5)+1;
				
				for (int i = 0; i < 2; i++) {
					while(randImageChooser == prevImage) {
						randImageChooser = rand.nextInt(4)+1;
					}
					switch (randImageChooser) {
					case 1:
						if (selectedImage && prevImage != 1) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test1.png"))); //TODO fill image 
							measurment2 = 1;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test1.png"))); //TODO fill image 
							prevImage = 1;
							selectedImage = true;
							measurment1 = 1;
							break;
						}
					case 2:
						if (selectedImage && prevImage != 2) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test2.png"))); //TODO fill image 
							measurment2 = 2;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test2.png"))); //TODO fill image 
							prevImage = 2;
							selectedImage = true;
							measurment1 = 2;
							break;
						}
					case 3:
						if (selectedImage && prevImage != 3) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test3.png"))); //TODO fill image 
							measurment2 = 3;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test3.png"))); //TODO fill image 
							prevImage = 3;
							selectedImage = true;
							measurment1 = 3;
							break;
						}
					case 4:
						if (selectedImage && prevImage != 4) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test4.png"))); //TODO fill image 
							measurment2 = 4;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test4.png"))); //TODO fill image 
							prevImage = 4;
							selectedImage = true;
							measurment1 = 4;
							break;
						}
					case 5:
						if (selectedImage && prevImage != 5) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test5.png"))); //TODO fill image 
							measurment2 = 5;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test5.png"))); //TODO fill image 
							prevImage = 5;
							selectedImage = true;
							measurment1 = 5;
							break;
						}
					}
					randImageChooser = rand.nextInt(4)+1;
					
				}
				if (measurment1 > measurment2) {
					correctAnswer = "left";
					wa[0] = "right";
				} else {
					correctAnswer = "right";
					wa[0] = "left";
				}
					
				//System.out.println("Measurement 1 is: " + measurment1 + ", Measurement 2 is: " + measurment2 + ", the correct answer is: " + correctAnswer);
				return measurementsProblem = new Problem(problemText, correctAnswer, wa, measurementImage1, measurementImage2);
				
				//The problem screen is only displaying measurementImage2, not measurementImage1, also the choices at the bottom of the screen vary by correct answer.
				
				
			case 2: 
				//generates "Which car is shorter questions"
				problemText = "Which car is shorter?";
				randImageChooser = rand.nextInt(5)+1;
				
				for (int i = 0; i < 2; i++) {
					while(randImageChooser == prevImage) {
						randImageChooser = rand.nextInt(4)+1;
					}
					switch (randImageChooser) {
					case 1:
						if (selectedImage && prevImage != 1) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test1.png"))); //TODO fill image 
							measurment2 = 1;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test1.png"))); //TODO fill image 
							prevImage = 1;
							selectedImage = true;
							measurment1 = 1;
							break;
						}
					case 2:
						if (selectedImage && prevImage != 2) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test2.png"))); //TODO fill image 
							measurment2 = 2;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test2.png"))); //TODO fill image 
							prevImage = 2;
							selectedImage = true;
							measurment1 = 2;
							break;
						}
					case 3:
						if (selectedImage && prevImage != 3) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test3.png"))); //TODO fill image 
							measurment2 = 3;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test3.png"))); //TODO fill image 
							prevImage = 3;
							selectedImage = true;
							measurment1 = 3;
							break;
						}
					case 4:
						if (selectedImage && prevImage != 4) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test4.png"))); //TODO fill image 
							measurment2 = 4;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test4.png"))); //TODO fill image 
							prevImage = 4;
							selectedImage = true;
							measurment1 = 4;
							break;
						}
					case 5:
						if (selectedImage && prevImage != 5) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/Test5.png"))); //TODO fill image 
							measurment2 = 5;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/Test5.png"))); //TODO fill image 
							prevImage = 5;
							selectedImage = true;
							measurment1 = 5;
							break;
						}
					}
					randImageChooser = rand.nextInt(4)+1;
				}
				if (measurment1 < measurment2) {
					correctAnswer = "left";
					wa[0] = "right";
				} else {
					correctAnswer = "right";
					wa[0] = "left";
				}
				//System.out.println("Measurement 1 is: " + measurment1 + ", Measurement 2 is: " + measurment2 + ", the correct answer is: " + correctAnswer);
				return measurementsProblem = new Problem(problemText, correctAnswer, wa, measurementImage1, measurementImage2);
				
				
			case 3: 
				//generates "Which image is heavier" questions for weight"
				problemText = "What weighs more?";
				randImageChooser = rand.nextInt(5)+1;
				
				for (int i = 0; i < 2; i++) {
					while(randImageChooser == prevImage) {
						randImageChooser = rand.nextInt(4)+1;
					}
					switch (randImageChooser) {
					case 1:
						if (selectedImage && prevImage != 1) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/1Weight.png"))); //TODO fill image 
							measurment2 = 1;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/1Weight.png"))); //TODO fill image 
							prevImage = 1;
							selectedImage = true;
							measurment1 = 1;
							break;
						}
					case 2:
						if (selectedImage && prevImage != 2) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/2Weight.png"))); //TODO fill image 
							measurment2 = 2;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/2Weight.png"))); //TODO fill image 
							prevImage = 2;
							selectedImage = true;
							measurment1 = 2;
							break;
						}
					case 3:
						if (selectedImage && prevImage != 3) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/3Weight.png"))); //TODO fill image 
							measurment2 = 3;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/3Weight.png"))); //TODO fill image 
							prevImage = 3;
							selectedImage = true;
							measurment1 = 3;
							break;
						}
					case 4:
						if (selectedImage && prevImage != 4) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/4Weight.png"))); //TODO fill image 
							measurment2 = 4;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/4Weight.png"))); //TODO fill image 
							prevImage = 4;
							selectedImage = true;
							measurment1 = 4;
							break;
						}
					case 5:
						if (selectedImage && prevImage != 5) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/5Weight.png"))); //TODO fill image 
							measurment2 = 5;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/5Weight.png"))); //TODO fill image 
							prevImage = 5;
							selectedImage = true;
							measurment1 = 5;
							break;
						}
					}
					randImageChooser = rand.nextInt(4)+1;
					
				}
				if (measurment1 > measurment2) {
					correctAnswer = "left";
					wa[0] = "right";
				} else {
					correctAnswer = "right";
					wa[0] = "left";
				}
				//System.out.println("Measurement 1 is: " + measurment1 + ", Measurement 2 is: " + measurment2 + ", the correct answer is: " + correctAnswer);
				return measurementsProblem = new Problem(problemText, correctAnswer, wa, measurementImage1, measurementImage2);
				
			case 4: 
				//generates "Which image weighs less? questions"
				problemText = "What weighs less?";
				randImageChooser = rand.nextInt(5)+1;
				
				for (int i = 0; i < 2; i++) {
					while(randImageChooser == prevImage) {
						randImageChooser = rand.nextInt(4)+1;
					}
					switch (randImageChooser) {
					case 1:
						if (selectedImage) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/1Weight.png"))); //TODO fill image 
							measurment2 = 1;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/1Weight.png"))); //TODO fill image 
							prevImage = 1;
							selectedImage = true;
							measurment1 = 1;
							break;
						}
					case 2:
						if (selectedImage) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/2Weight.png"))); //TODO fill image 
							measurment2 = 2;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/2Weight.png"))); //TODO fill image 
							prevImage = 2;
							selectedImage = true;
							measurment1 = 2;
							break;
						}
					case 3:
						if (selectedImage) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/3Weight.png"))); //TODO fill image 
							measurment2 = 3;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/3Weight.png"))); //TODO fill image 
							prevImage = 3;
							selectedImage = true;
							measurment1 = 3;
							break;
						}
					case 4:
						if (selectedImage) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/4Weight.png"))); //TODO fill image 
							measurment2 = 4;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/4Weight.png"))); //TODO fill image 
							prevImage = 4;
							selectedImage = true;
							measurment1 = 4;
							break;
						}
					case 5:
						if (selectedImage) {
							measurementImage2 = new Image(new Texture(Gdx.files.internal("images/5Weight.png"))); //TODO fill image 
							measurment2 = 5;
							break;
						} else {
							measurementImage1 = new Image(new Texture(Gdx.files.internal("images/5Weight.png"))); //TODO fill image 
							prevImage = 5;
							selectedImage = true;
							measurment1 = 5;
							break;
						}
					}
					randImageChooser = rand.nextInt(4)+1;
					
				}
				if (measurment1 < measurment2) {
					correctAnswer = "left";
					wa[0] = "right";
				} else {
					correctAnswer = "right";
					wa[0] = "left";
				}	
				//System.out.println("Measurement 1 is: " + measurment1 + ", Measurement 2 is: " + measurment2 + ", the correct answer is: " + correctAnswer);
				return measurementsProblem = new Problem(problemText, correctAnswer, wa, measurementImage1, measurementImage2);
				
			}
			break;
		case "1st Grade":
			randProblemChooser = rand.nextInt(4)+1;
			
			switch(randProblemChooser) {
			case 1:
				
			}
			
			
			
			
			break;
		case "2nd Grade":
			typesOfProblems = 3;
			break;
		case "3rd Grade":
			typesOfProblems = 3;
			break;
		case "4th Grade": 
			typesOfProblems = 2;
			break;
		}
		return measurementsProblem;
	}
	
	private Problem generateFractionsProblem(String grade) {
		Problem fractionsProblem = new Problem();
		//TODO add image version
		Random random = new Random();
		int randNumber = 0;
		String problemText = "";
		String correctAnswer = "";
		String wrongAnswers[] = new String[3];
		int denominators[] = new int[0];
		int number1num = 0;
		int number1den = 1;
		String operator = "";
		int number2num = 0;
		int number2den = 1;
		int answernum = 0;
		int answerden = 0;
		int wrongAnswersInts[] = new int[3];
		int numberRange = 100;
		switch(grade) {
		case "3rd Grade":
			//comparing fractions, only denominators 2, 3, 4, 6, 8
			wrongAnswers = new String[2];
			denominators = new int[] {2,3,4,6,8};
			//same numerator or denominator
			randNumber = random.nextInt(2);
			if(randNumber == 0) {
				//numerator
				randNumber = random.nextInt(10);
				number1num = randNumber;
				number2num = randNumber;
				randNumber = random.nextInt(denominators.length);
				number1den = denominators[randNumber];
				randNumber = random.nextInt(denominators.length);
				number2den = denominators[randNumber];
			}else {
				//denominator
				randNumber = random.nextInt(denominators.length);
				number1den = denominators[randNumber];
				number2den = denominators[randNumber];
				randNumber = random.nextInt(10);
				number1num = randNumber;
				randNumber = random.nextInt(10);
				number2num = randNumber;
			}
			if ((Double.valueOf(number1num)/Double.valueOf(number1den))==(Double.valueOf(number2num)/Double.valueOf(number2den))) {
				correctAnswer = "=";
				wrongAnswers[0] = ">";
				wrongAnswers[1] = "<";
			}else if ((Double.valueOf(number1num)/Double.valueOf(number1den))>(Double.valueOf(number2num)/Double.valueOf(number2den))) {
				correctAnswer = ">";
				wrongAnswers[0] = "=";
				wrongAnswers[1] = "<";
			}else {
				correctAnswer = "<";
				wrongAnswers[0] = ">";
				wrongAnswers[1] = "=";
			}
			problemText = "Compare the fractions: " + number1num + "/" + number1den + " ? " + number2num + "/" + number2den;
			fractionsProblem = new Problem(problemText, correctAnswer, wrongAnswers);
			break;
		case "4th Grade":
			//fraction operations
			denominators = new int[] {2,3,4,6,8,10,12,100};
			number1den = random.nextInt(denominators.length);
			number2den = number1den;
			answerden = number1den;
			randNumber = random.nextInt(2);
			switch(randNumber) {
			case 0:
				//addition
				operator = "+";
				answernum = random.nextInt(numberRange)+1;
				number1num = random.nextInt(answernum);
				number2num = answernum-number1num;
				randNumber = answernum;
				for (int i=0; i<3; i++) {
					while(randNumber==answernum||randNumber==wrongAnswersInts[0]||randNumber==wrongAnswersInts[1]||randNumber==wrongAnswersInts[2]) {
						randNumber = random.nextInt(numberRange)+1;
					}
					wrongAnswersInts[i]=randNumber;
					randNumber = answernum;
				}
				break;
			case 1:
				//subtraction
				operator = "-";
				number1num = random.nextInt(numberRange)+1;
				number2num = random.nextInt(number1num);
				answernum = number1num-number2num;
				randNumber = answernum;
				for (int i=0; i<3; i++) {
					while(randNumber==answernum||randNumber==wrongAnswersInts[0]||randNumber==wrongAnswersInts[1]||randNumber==wrongAnswersInts[2]) {
						randNumber = random.nextInt(numberRange)+1;
					}
					wrongAnswersInts[i]=randNumber;
					randNumber = answernum;
				}
				break;
			}
			correctAnswer = answernum + "/" + answerden;
			wrongAnswers = new String[] {wrongAnswersInts[0] + "/" + answerden, "" + wrongAnswersInts[1] + "/" + answerden, "" + wrongAnswersInts[2] + "/" + answerden};
			problemText = number1num + "/" + number1den +" " + operator + " " + number2num + "/" + number2den;
			fractionsProblem = new Problem(problemText, correctAnswer, wrongAnswers);
			break;
		}
		return fractionsProblem;
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