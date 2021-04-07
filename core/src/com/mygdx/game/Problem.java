package com.mygdx.game;

public class Problem {

	private String problemText;
	private String correctAnswer;
	private String wrongAnswers[];
	
	public Problem(String pt, String ca, String wa[]) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = wa;
	}
	
	public Problem(String pt, String ca) {
		problemText = pt;
		correctAnswer = ca;
		wrongAnswers = null;
	}
	
	public Problem() {
		problemText = "";
		problemText = "";
		wrongAnswers = new String[3];
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
	
	public void setProblemText(String pt) {
		problemText = pt;;
	}
	
	public void setCorrectAnswer(String ca) {
		correctAnswer = ca;
	}
	
	public void setWorrectAnswers(String[] wa) {
		wrongAnswers = wa;
	}
}
