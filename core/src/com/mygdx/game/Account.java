package com.mygdx.game;

public class Account {
	
	private String username;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String fullName;
	
	//achievements, 0 - nothing, 1 - bronze, 2 - silver, 3 - gold
	private int kindergartenCounting;
	private int kindergartenOperations;
	private int kindergartenNumbers;
	private int kindergartenMeasurements;
	private int kindergartenExam;

	private int grade1Operations;
	private int grade1Numbers;
	private int grade1Measurements;
	private int grade1Exam;

	private int grade2Operations;
	private int grade2Numbers;
	private int grade2Measurements;
	private int grade2Exam;

	private int grade3Operations;
	private int grade3Numbers;
	private int grade3Fractions;
	private int grade3Measurements;
	private int grade3Exam;

	private int grade4Operations;
	private int grade4Numbers;
	private int grade4Fractions;
	private int grade4Measurements;
	private int grade4Exam;
	
	private String[] latestAchievements; // none - nothing, 1 - bronze, 2 - silver, 3 - gold, 6 fields: grade1, topic1, grade2, topic2, grade3, topic3
	
	public Account(String u, String p, String sq, String sa, String fn, int kc, int ko, int kn, int km, int ke, int o1, int n1, int m1, int e1, int o2, int n2, int m2, int e2, int o3, int n3, int f3, int m3, int e3, int o4, int n4, int f4, int m4, int e4, String[] la) {
		username = u;
		password = p;
		secretQuestion = sq;
		secretAnswer = sa;
		fullName = fn;
		
		kindergartenCounting = kc;
		kindergartenOperations = ko;
		kindergartenNumbers = kn;
		kindergartenMeasurements = km;
		kindergartenExam = ke;

		grade1Operations = o1;
		grade1Numbers = n1;
		grade1Measurements = m1;
		grade1Exam = e1;

		grade2Operations = o2;
		grade2Numbers = n2;
		grade2Measurements = m2;
		grade2Exam = e2;

		grade3Operations = o3;
		grade3Numbers = n3;
		grade3Fractions = f3;
		grade3Measurements = m3;
		grade3Exam = e3;

		grade4Operations = o4;
		grade4Numbers = n4;
		grade4Fractions = f4;
		grade4Measurements = m4;
		grade4Exam = e4;
		
		latestAchievements = la;
	}
	
	public Account(String u, String p, String sq, String sa, String fn) {
		username = u;
		password = p;
		secretQuestion = sq;
		secretAnswer = sa;
		fullName = fn;
		
		kindergartenCounting = 0;
		kindergartenOperations = 0;
		kindergartenNumbers = 0;
		kindergartenMeasurements = 0;
		kindergartenExam = 0;

		grade1Operations = 0;
		grade1Numbers = 0;
		grade1Measurements = 0;
		grade1Exam = 0;

		grade2Operations = 0;
		grade2Numbers = 0;
		grade2Measurements = 0;
		grade2Exam = 0;

		grade3Operations = 0;
		grade3Numbers = 0;
		grade3Fractions = 0;
		grade3Measurements = 0;
		grade3Exam = 0;

		grade4Operations = 0;
		grade4Numbers = 0;
		grade4Fractions = 0;
		grade4Measurements = 0;
		grade4Exam = 0;
		
		latestAchievements = new String[] {"none","none","none","none","none","none"};
	}
	
	public Account() {
		username = "";
		password = "";
		secretQuestion = "";
		secretAnswer = "";
		fullName = "";
		
		kindergartenCounting = 0;
		kindergartenOperations = 0;
		kindergartenNumbers = 0;
		kindergartenMeasurements = 0;
		kindergartenExam = 0;

		grade1Operations = 0;
		grade1Numbers = 0;
		grade1Measurements = 0;
		grade1Exam = 0;

		grade2Operations = 0;
		grade2Numbers = 0;
		grade2Measurements = 0;
		grade2Exam = 0;

		grade3Operations = 0;
		grade3Numbers = 0;
		grade3Fractions = 0;
		grade3Measurements = 0;
		grade3Exam = 0;

		grade4Operations = 0;
		grade4Numbers = 0;
		grade4Fractions = 0;
		grade4Measurements = 0;
		grade4Exam = 0;
		
		latestAchievements = new String[] {"none","none","none","none","none","none"};
	}
	
	public int getAchievement(String grade, String topic) {
		switch(grade) {
		case "Kindergarten":
			switch(topic) {
			case "Counting":
				return kindergartenCounting;
			case "Operations":
				return kindergartenOperations;
			case "Numbers":
				return kindergartenNumbers;
			case "Measurements":
				return kindergartenMeasurements;
			case "Exam":
				return kindergartenExam;
			}
			break;
		case "1st Grade":
			switch(topic) {
			case "Operations":
				return grade1Operations;
			case "Numbers":
				return grade1Numbers;
			case "Measurements":
				return grade1Measurements;
			case "Exam":
				return grade1Exam;
			}
			break;
		case "2nd Grade":
			switch(topic) {
			case "Operations":
				return grade2Operations;
			case "Numbers":
				return grade2Numbers;
			case "Measurements":
				return grade2Measurements;
			case "Exam":
				return grade2Exam;
			}
			break;
		case "3rd Grade":
			switch(topic) {
			case "Operations":
				return grade3Operations;
			case "Numbers":
				return grade3Numbers;
			case "Fractions":
				return grade3Fractions;
			case "Measurements":
				return grade3Measurements;
			case "Exam":
				return grade3Exam;
			}
			break;
		case "4th Grade":
			switch(topic) {
			case "Operations":
				return grade4Operations;
			case "Numbers":
				return grade4Numbers;
			case "Fractions":
				return grade4Fractions;
			case "Measurements":
				return grade4Measurements;
			case "Exam":
				return grade4Exam;
			}
			break;
		}
		return -1;
	}
	
	public String[] getGreatestAchievements() {
		String[] ga = new String[] {"none","none","none","none","none","none"};
		int[] allAchievements = {kindergartenCounting, kindergartenOperations, kindergartenNumbers, kindergartenMeasurements, kindergartenExam, grade1Operations, grade1Numbers, grade1Measurements, grade1Exam, grade2Operations, grade2Numbers, grade2Measurements, grade2Exam, grade3Operations, grade3Numbers, grade3Fractions, grade3Measurements, grade3Exam, grade4Operations, grade4Numbers, grade4Fractions, grade4Measurements, grade4Exam};
		for (int i = allAchievements.length-1; i>-1; i--) {
			if (allAchievements[i]==3) {
				switch(i) {
				case 0:
					ga = addToGreatestAchievements("Kindergarten", "Counting", ga);
					break;
				case 1:
					ga = addToGreatestAchievements("Kindergarten", "Operations", ga);
					break;
				case 2:
					ga = addToGreatestAchievements("Kindergarten", "Numbers", ga);
					break;
				case 3:
					ga = addToGreatestAchievements("Kindergarten", "Measurements", ga);
					break;
				case 4:
					ga = addToGreatestAchievements("Kindergarten", "Exam", ga);
					break;
				case 5:
					ga = addToGreatestAchievements("1st Grade", "Operations", ga);
					break;
				case 6:
					ga = addToGreatestAchievements("1st Grade", "Numbers", ga);
					break;
				case 7:
					ga = addToGreatestAchievements("1st Grade", "Measurements", ga);
					break;
				case 8:
					ga = addToGreatestAchievements("1st Grade", "Exam", ga);
					break;
				case 9:
					ga = addToGreatestAchievements("2nd Grade", "Operations", ga);
					break;
				case 10:
					ga = addToGreatestAchievements("2nd Grade", "Numbers", ga);
					break;
				case 11:
					ga = addToGreatestAchievements("2nd Grade", "Measurements", ga);
					break;
				case 12:
					ga = addToGreatestAchievements("2nd Grade", "Exam", ga);
					break;
				case 13:
					ga = addToGreatestAchievements("3rd Grade", "Operations", ga);
					break;
				case 14:
					ga = addToGreatestAchievements("3rd Grade", "Numbers", ga);
					break;
				case 15:
					ga = addToGreatestAchievements("3rd Grade", "Fractions", ga);
					break;
				case 16:
					ga = addToGreatestAchievements("3rd Grade", "Measurements", ga);
					break;
				case 17:
					ga = addToGreatestAchievements("3rd Grade", "Exam", ga);
					break;
				case 18:
					ga = addToGreatestAchievements("4th Grade", "Operations", ga);
					break;
				case 19:
					ga = addToGreatestAchievements("4th Grade", "Numbers", ga);
					break;
				case 20:
					ga = addToGreatestAchievements("4th Grade", "Fractions", ga);
					break;
				case 21:
					ga = addToGreatestAchievements("4th Grade", "Measurements", ga);
					break;
				case 22:
					ga = addToGreatestAchievements("4th Grade", "Exam", ga);
					break;
				}
			}
		}
		for (int i = allAchievements.length-1; i>-1; i--) {
			if (allAchievements[i]==2) {
				switch(i) {
				case 0:
					ga = addToGreatestAchievements("Kindergarten", "Counting", ga);
					break;
				case 1:
					ga = addToGreatestAchievements("Kindergarten", "Operations", ga);
					break;
				case 2:
					ga = addToGreatestAchievements("Kindergarten", "Numbers", ga);
					break;
				case 3:
					ga = addToGreatestAchievements("Kindergarten", "Measurements", ga);
					break;
				case 4:
					ga = addToGreatestAchievements("Kindergarten", "Exam", ga);
					break;
				case 5:
					ga = addToGreatestAchievements("1st Grade", "Operations", ga);
					break;
				case 6:
					ga = addToGreatestAchievements("1st Grade", "Numbers", ga);
					break;
				case 7:
					ga = addToGreatestAchievements("1st Grade", "Measurements", ga);
					break;
				case 8:
					ga = addToGreatestAchievements("1st Grade", "Exam", ga);
					break;
				case 9:
					ga = addToGreatestAchievements("2nd Grade", "Operations", ga);
					break;
				case 10:
					ga = addToGreatestAchievements("2nd Grade", "Numbers", ga);
					break;
				case 11:
					ga = addToGreatestAchievements("2nd Grade", "Measurements", ga);
					break;
				case 12:
					ga = addToGreatestAchievements("2nd Grade", "Exam", ga);
					break;
				case 13:
					ga = addToGreatestAchievements("3rd Grade", "Operations", ga);
					break;
				case 14:
					ga = addToGreatestAchievements("3rd Grade", "Numbers", ga);
					break;
				case 15:
					ga = addToGreatestAchievements("3rd Grade", "Fractions", ga);
					break;
				case 16:
					ga = addToGreatestAchievements("3rd Grade", "Measurements", ga);
					break;
				case 17:
					ga = addToGreatestAchievements("3rd Grade", "Exam", ga);
					break;
				case 18:
					ga = addToGreatestAchievements("4th Grade", "Operations", ga);
					break;
				case 19:
					ga = addToGreatestAchievements("4th Grade", "Numbers", ga);
					break;
				case 20:
					ga = addToGreatestAchievements("4th Grade", "Fractions", ga);
					break;
				case 21:
					ga = addToGreatestAchievements("4th Grade", "Measurements", ga);
					break;
				case 22:
					ga = addToGreatestAchievements("4th Grade", "Exam", ga);
					break;
				}
			}
		}
		for (int i = allAchievements.length-1; i>-1; i--) {
			if (allAchievements[i]==1) {
				switch(i) {
				case 0:
					ga = addToGreatestAchievements("Kindergarten", "Counting", ga);
					break;
				case 1:
					ga = addToGreatestAchievements("Kindergarten", "Operations", ga);
					break;
				case 2:
					ga = addToGreatestAchievements("Kindergarten", "Numbers", ga);
					break;
				case 3:
					ga = addToGreatestAchievements("Kindergarten", "Measurements", ga);
					break;
				case 4:
					ga = addToGreatestAchievements("Kindergarten", "Exam", ga);
					break;
				case 5:
					ga = addToGreatestAchievements("1st Grade", "Operations", ga);
					break;
				case 6:
					ga = addToGreatestAchievements("1st Grade", "Numbers", ga);
					break;
				case 7:
					ga = addToGreatestAchievements("1st Grade", "Measurements", ga);
					break;
				case 8:
					ga = addToGreatestAchievements("1st Grade", "Exam", ga);
					break;
				case 9:
					ga = addToGreatestAchievements("2nd Grade", "Operations", ga);
					break;
				case 10:
					ga = addToGreatestAchievements("2nd Grade", "Numbers", ga);
					break;
				case 11:
					ga = addToGreatestAchievements("2nd Grade", "Measurements", ga);
					break;
				case 12:
					ga = addToGreatestAchievements("2nd Grade", "Exam", ga);
					break;
				case 13:
					ga = addToGreatestAchievements("3rd Grade", "Operations", ga);
					break;
				case 14:
					ga = addToGreatestAchievements("3rd Grade", "Numbers", ga);
					break;
				case 15:
					ga = addToGreatestAchievements("3rd Grade", "Fractions", ga);
					break;
				case 16:
					ga = addToGreatestAchievements("3rd Grade", "Measurements", ga);
					break;
				case 17:
					ga = addToGreatestAchievements("3rd Grade", "Exam", ga);
					break;
				case 18:
					ga = addToGreatestAchievements("4th Grade", "Operations", ga);
					break;
				case 19:
					ga = addToGreatestAchievements("4th Grade", "Numbers", ga);
					break;
				case 20:
					ga = addToGreatestAchievements("4th Grade", "Fractions", ga);
					break;
				case 21:
					ga = addToGreatestAchievements("4th Grade", "Measurements", ga);
					break;
				case 22:
					ga = addToGreatestAchievements("4th Grade", "Exam", ga);
					break;
				}
			}
		}
		return ga;
	}
	
	private String[] addToGreatestAchievements(String grade, String topic, String[] ga) {
		if (ga[0].equals("none")) {
			ga[0]=grade;
			ga[1]=topic;
		} else if (ga[2].equals("none")) {
			ga[2]=grade;
			ga[3]=topic;
		} else if (ga[4].equals("none")) {
			ga[4]=grade;
			ga[5]=topic;
		}
		return ga;
	}
	
	public void gainAchievement(String grade, String topic, int a) {
		switch(grade) {
		case "Kindergarten":
			switch(topic) {
			case "Counting":
				if (kindergartenCounting < a) kindergartenCounting = a;
				break;
			case "Operations":
				if (kindergartenOperations < a) kindergartenOperations = a;
				break;
			case "Numbers":
				if (kindergartenNumbers < a) kindergartenNumbers = a;
				break;
			case "Measurements":
				if (kindergartenMeasurements < a) kindergartenMeasurements = a;
				break;
			case "Exam":
				if (kindergartenExam < a) kindergartenExam = a;
				break;
			}
			break;
		case "1st Grade":
			switch(topic) {
			case "Operations":
				if (grade1Operations < a) grade1Operations = a;
				break;
			case "Numbers":
				if (grade1Numbers < a) grade1Numbers = a;
				break;
			case "Measurements":
				if (grade1Measurements < a) grade1Measurements = a;
				break;
			case "Exam":
				if (grade1Exam < a) grade1Exam = a;
				break;
			}
			break;
		case "2nd Grade":
			switch(topic) {
			case "Operations":
				if (grade2Operations < a) grade2Operations = a;
				break;
			case "Numbers":
				if (grade2Numbers < a) grade2Numbers = a;
				break;
			case "Measurements":
				if (grade2Measurements < a) grade2Measurements = a;
				break;
			case "Exam":
				if (grade2Exam < a) grade2Exam = a;
				break;
			}
			break;
		case "3rd Grade":
			switch(topic) {
			case "Operations":
				if (grade3Operations < a) grade3Operations = a;
				break;
			case "Numbers":
				if (grade3Numbers < a) grade3Numbers = a;
				break;
			case "Fractions":
				if (grade3Fractions < a) grade3Fractions = a;
				break;
			case "Measurements":
				if (grade3Measurements < a) grade3Measurements = a;
				break;
			case "Exam":
				if (grade3Exam < a) grade3Exam = a;
				break;
			}
			break;
		case "4th Grade":
			switch(topic) {
			case "Operations":
				if (grade4Operations < a) grade4Operations = a;
				break;
			case "Numbers":
				if (grade4Numbers < a) grade4Numbers = a;
				break;
			case "Fractions":
				if (grade4Fractions < a) grade4Fractions = a;
				break;
			case "Measurements":
				if (grade4Measurements < a) grade4Measurements = a;
				break;
			case "Exam":
				if (grade4Exam < a) grade4Exam = a;
				break;
			}
			break;
		}
		updateLatestAchievements(grade, topic);
	}
	
	private void updateLatestAchievements(String grade, String topic) {
		// none - nothing, 1 - bronze, 2 - silver, 3 - gold, 6 fields: grade1, topic1, grade2, topic2, grade3, topic3
		if (latestAchievements[0].equals("none")) {
			latestAchievements[0] = grade;
			latestAchievements[1] = topic;
		}
		else if (!(latestAchievements[0].equals(grade)&&latestAchievements[1].equals(topic))&&!(latestAchievements[2].equals(grade)&&latestAchievements[3].equals(topic))&&!(latestAchievements[4].equals(grade)&&latestAchievements[5].equals(topic))) {
			//if the new achievement isn't on the list yet, add it and move everything over
			latestAchievements[5] = latestAchievements[3];
			latestAchievements[4] = latestAchievements[2];
			latestAchievements[3] = latestAchievements[1];
			latestAchievements[2] = latestAchievements[0];
			latestAchievements[1] = topic;
			latestAchievements[0] = grade;
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSecretQuestion() {
		return secretQuestion;
	}
	
	public String getSecretAnswer() {
		return secretAnswer;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	//no field can contain "," or "." so if they are contained in the string, these methods do not allow them through
	public void setUsername(String u) {
		if (!u.contains(",") && !u.contains(".")) username = u;
	}
	
	public void setPassword(String p) {
		if (!p.contains(",") && !p.contains(".")) password = p;
	}
	
	public void setSecretQuestion(String sq) {
		if (!sq.contains(",") && !sq.contains(".")) secretQuestion = sq;
	}
	
	public void setSecretAnswer(String sa) {
		if (!sa.contains(",") && !sa.contains(".")) secretAnswer = sa;
	}
	
	public void setFullName(String fn) {
		if (!fn.contains(",") && !fn.contains(".")) fullName = fn;
	}
	
	//getters for achievements
	public int getkindergartenCounting() {
		return kindergartenCounting;
	}
	
	public int getkindergartenOperations() {
		return kindergartenOperations;
	}
	
	public int getkindergartenNumbers() {
		return kindergartenNumbers;
	}
	
	public int getkindergartenMeasurements() {
		return kindergartenMeasurements;
	}
	
	public int getkindergartenExam() {
		return kindergartenExam;
	}
	
	public int getGrade1Operations() {
		return grade1Operations;
	}
	
	public int getGrade1Numbers() {
		return grade1Numbers;
	}
	
	public int getGrade1Measurements() {
		return grade1Measurements;
	}
	
	public int getGrade1Exam() {
		return grade1Exam;
	}
	
	public int getGrade2Operations() {
		return grade2Operations;
	}
	
	public int getGrade2Numbers() {
		return grade2Numbers;
	}
	
	public int getGrade2Measurements() {
		return grade2Measurements;
	}
	
	public int getGrade2Exam() {
		return grade2Exam;
	}
	
	public int getGrade3Operations() {
		return grade3Operations;
	}
	
	public int getGrade3Numbers() {
		return grade3Numbers;
	}
	
	public int getGrade3Fractions() {
		return grade3Fractions;
	}
	
	public int getGrade3Measurements() {
		return grade3Measurements;
	}
	
	public int getGrade3Exam() {
		return grade3Exam;
	}
	
	public int getGrade4Operations() {
		return grade4Operations;
	}
	
	public int getGrade4Numbers() {
		return grade4Numbers;
	}
	
	public int getGrade4Fractions() {
		return grade4Fractions;
	}
	
	public int getGrade4Measurements() {
		return grade4Measurements;
	}
	
	public int getGrade4Exam() {
		return grade4Exam;
	}
	
	//setters for achievements
	public void setkindergartenCounting(int a) {
		kindergartenCounting = a;
	}
	
	public void setkindergartenOperations(int a) {
		kindergartenOperations = a;
	}
	
	public void setkindergartenNumbers(int a) {
		kindergartenNumbers = a;
	}
	
	public void setkindergartenMeasurements(int a) {
		kindergartenMeasurements = a;
	}
	
	public void setkindergartenExam(int a) {
		kindergartenExam = a;
	}
	
	public void setGrade1Operations(int a) {
		grade1Operations = a;
	}
	
	public void setGrade1Numbers(int a) {
		grade1Numbers = a;
	}
	
	public void setGrade1Measurements(int a) {
		grade1Measurements = a;
	}
	
	public void setGrade1Exam(int a) {
		grade1Exam = a;
	}
	
	public void setGrade2Operations(int a) {
		grade2Operations = a;
	}
	
	public void setGrade2Numbers(int a) {
		grade2Numbers = a;
	}
	
	public void setGrade2Measurements(int a) {
		grade2Measurements = a;
	}
	
	public void setGrade2Exam(int a) {
		grade2Exam = a;
	}
	
	public void setGrade3Operations(int a) {
		grade3Operations = a;
	}
	
	public void setGrade3Numbers(int a) {
		grade3Numbers = a;
	}
	
	public void setGrade3Fractions(int a) {
		grade3Fractions = a;
	}
	
	public void setGrade3Measurements(int a) {
		grade3Measurements = a;
	}
	
	public void setGrade3Exam(int a) {
		grade3Exam = a;
	}
	
	public void setGrade4Operations(int a) {
		grade4Operations = a;
	}
	
	public void setGrade4Numbers(int a) {
		grade4Numbers = a;
	}
	
	public void setGrade4Fractions(int a) {
		grade4Fractions = a;
	}
	
	public void setGrade4Measurements(int a) {
		grade4Measurements = a;
	}
	
	public void setGrade4Exam(int a) {
		grade4Exam = a;
	}
	
	public String[] getLatestAchievements() {
		return latestAchievements;
	}
	
	public void setLatestAchievements(String[] la) {
		latestAchievements = la;
	}
}
