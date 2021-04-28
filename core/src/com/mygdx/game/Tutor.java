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
	public Problem[] problems = {new Problem("5 - 3 = ?", "2"), new Problem("2 + 2 = ?", "4"), new Problem("3 + 2 = ?", "5", new String[] {"4","3","6"}), new Problem("If each student has 3 apples, how many apples do 5 students have", "15"), new Problem("You have 2 oranges, Lisa has 7 oranges, how many oranges do you have together","9", new String[] {"8","10","7"})};
	
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
	
	
	public Problem problemGenerator(String grade, String topic, boolean multipleChoiceOrNot) {
		int start = 1;
		int range = 0;
		int pattern = 0;
		char[] possibleOperations = {'+', '-', 'x', '/'};
		String[] names;
		String[] objectsForProblems;
		
		Random rand = new Random();
		int random = 0;
		double field1 = 0;
		double field2 = 0;
		double correctAnswer = 0;
		String problemText = "";
		String problemCorrectAnswer = "";
		Problem newProblem = new Problem();
		
		
		//determining grade & topic validation 
		if (grade.equals("Kindergarten")) {
			random = rand.nextInt(3); 
			
			if (random == 0) {		//Counting and Cardinality
				
				
			}
			
		} else if (grade.equals("1st Grade")) {
			
		} else if (grade.equals("2nd Grade")) {
			
		} else if (grade.equals("3rd Grade")) {
			
		} else if (grade.equals("4th Grade")) {
			
			if (topic.equals("Operations")) {					//choosing the subtopic in grade 4
				random = rand.nextInt(3);
				
				if (random == 0) {	//starting the first set of things needed to be test for grade 4
					range = 100;
					boolean isDivision = false;
					random = rand.nextInt(2);
					
					if (random == 0) {
						if (!multipleChoiceOrNot) {
							field1 = rand.nextInt(range + 1);
							field2 = rand.nextInt(range + 1);
							
							if (field1 == 0)
								field1 = 1;
							if (field2 == 0)
								field2 = 1;
							
							correctAnswer = field1 * field2;
							problemText = "" + correctAnswer + " = __ x " + field2;
							problemCorrectAnswer = Double.toString(field1);
							
							newProblem.setCorrectAnswer(problemCorrectAnswer);
							newProblem.setProblemText(problemText);
							newProblem.setCorrectAnswers(null);
								
							return newProblem;
						} else {
							field1 = rand.nextInt(range + 1);
							field2 = rand.nextInt(range + 1);
							String[] wrongAnswers = {"", "", ""};
							
							if (field1 == 0)
								field1 = 1;
							if (field2 == 0)
								field2 = 1;
							
							correctAnswer = field1 * field2;
							problemText = "" + correctAnswer + " = __ x " + field2;
							problemCorrectAnswer = Double.toString(field1);
							
							for (int i = 0; i < 2; i++) {
								wrongAnswers[i] = Integer.toString(rand.nextInt(100));
							}
							
							newProblem.setCorrectAnswer(problemCorrectAnswer);
							newProblem.setProblemText(problemText);
							newProblem.setCorrectAnswers(wrongAnswers);
							return newProblem;
							
						}
						
					
						
					} else if (random == 1) {
						random = rand.nextInt(2);
						
						if (random == 1) {
							isDivision = true;
						}
						field1 = rand.nextInt(range + 1);
						field2 = rand.nextInt(range + 1);
					
						if (field1 == 0)
							field1 = rand.nextInt(range + 1);
						if (field2 == 0)
							field2 = rand.nextInt(range + 1);
						
						if (!isDivision) {
							correctAnswer = field1 * field2;
							problemText = "John has + " + field1 + " apples and Bob has " + field2 + "apples, how many apples do they have?";
							problemCorrectAnswer = Double.toString(correctAnswer);
							
							newProblem.setCorrectAnswer(problemCorrectAnswer);
							newProblem.setProblemText(problemText);
									
							return newProblem;
						} else {
							if (field1 % field2 != 0) {
								field2--;
								
								while (field1 % field2 != 0) {
									field2--;
								}
							}
							
							correctAnswer = field1 / field2;
							problemText = "John has " + field1 + "tomatoes and wants to have " + field2 + "seperate even piles, how many piles will John have?";
							problemCorrectAnswer = Double.toString(correctAnswer);
							
							newProblem.setCorrectAnswer(problemCorrectAnswer);
							newProblem.setProblemText(problemText);
							
							return newProblem;
						}
				}
			}
			
		} else if (random == 1) {		//subtopic 2 of grade 4;
		
		}
			return null;
		}
		
		return null;
	}
}