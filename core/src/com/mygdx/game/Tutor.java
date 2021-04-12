package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
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
	
	public int answerCounter = 0;
	public int problemNumber = 0;
	public int additionAchievement = 0;
	public Problem[] problems = {new Problem("5 + 3 = ?", "8"), new Problem("2 + 2 = ?", "4"), new Problem("3 + 2 = ?", "5", new String[] {"4","3","6"})};
	public ArrayList<Account> accounts = new ArrayList<Account>(0);
	public Account currentUser = new Account();
	
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
	
	public void addAccount(String username, String password, String secretQuestion, String secretAnswer, String fullName) {
		if (!checkDuplicateUsername(username)) accounts.add(new Account(username, password, secretQuestion, secretAnswer, fullName));
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
	
}
