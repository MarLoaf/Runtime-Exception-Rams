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
		selectedAnswer = null;
	}
	
	public Problem(String pt, String ca) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = null;
		selectedAnswer = null;
	}
	
	public Problem() {
		problemText = "";
		problemText = "";
		wrongAnswers = new String[3];
		selectedAnswer = null;
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
	
	public void setWorrectAnswers(String[] wa) {
		wrongAnswers = wa;
	}
	
	public void setSelectedAnswer(String sa) {
		selectedAnswer = sa;
	}
}
