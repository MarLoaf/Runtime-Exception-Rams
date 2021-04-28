package com.mygdx.game;

public class Problem {

	private String problemText;
	private String correctAnswer;
	private String wrongAnswers[];
	private String selectedAnswer;
	
	public Problem(String pt, String ca, String wa[], String sa) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = sa;
	}
	
	public Problem(String pt, String ca, String wa[]) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
		selectedAnswer = "";
	}
	
	public Problem(String pt, String ca) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = null;
		selectedAnswer = "";
	}
	
	public Problem() {
		problemText = "";
		correctAnswer = "";
		wrongAnswers = new String[3];
		selectedAnswer = "";
	}
	
	public boolean checkAnswer() {
		if (selectedAnswer.equals(correctAnswer)) return true;
		return false;
	}
	
	public String getProblemText() {
		return problemText;
	}
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	public String[] getWrongAnswers() {
		return wrongAnswers;
	}
	
	public String getSelectedAnswer() {
		return selectedAnswer;
	}
	
	public void setProblemText(String pt) {
		problemText = pt;;
	}
	
	public void setCorrectAnswer(String ca) {
		correctAnswer = ca;
	}
	
	public void setCorrectAnswers(String[] wa) {
		wrongAnswers = wa;
	}
	
	public void setSelectedAnswer(String sa) {
		selectedAnswer = sa;
	}
}
