/*
 * Copyright (c) 2013
 * Jasper Reddin
 */

package mathquizgame;

import java.util.Random;

/**
 *
 * @author jasper
 */
public class MathOperator {

	static Random generator = new Random();
	static int number1;
	static int number2;
	static int total;
	static char operationToUse;
	static int numberOfTimesWillBePlayed;
	static float pointsWorth;

	static int customMinRange;
	static int customMaxRange;
	static char customOperation;

	// Operation characters
	public static final char PLUS_OPERATION_CHAR = '+';
	public static final char SUBTRACT_OPERATION_CHAR = '-';
	public static final char MULTIPLY_OPERATION_CHAR = 'x';
	public static final char DIVIDE_OPERATION_CHAR = '/';

	// Difficulty fields
	public static final int ELEMENTARY_DIFFICULTY = 0;
	public static final int MIDDLE_SCHOOL_DIFFICULTY = 1;
	public static final int HIGH_SCHOOL_DIFFICULTY = 2;
	public static final int CUSTOM_DIFFICULTY = 3;

	public static void operate(int userAnswer){

		if (MathQuizGame.numberOfTimesPlayed <= numberOfTimesWillBePlayed - 1){
			if (userAnswer == total){
				EnterText("You are correct!");
				MathQuizGame.score += pointsWorth;

				newNumbers();
			} else {
				EnterText("Wrong. Correct Answer: " + total);

				newNumbers();

			}
			MathQuizGame.setQuestionState(0);
		} else {

			if (total == userAnswer){
				EnterText("You are correct!");
				MathQuizGame.score += pointsWorth;
			}else{
				EnterText("Wrong. Correct Answer: " + total);
			}
			if(MathQuizGame.getTimerRunning()){
				MathQuizGame.timer.stop();
			}
			MathQuizGame.endGame();

		}
	}

	public static void operate(int userAnswer, boolean showCorrectMessage) {

		if (MathQuizGame.numberOfTimesPlayed <= numberOfTimesWillBePlayed - 1){
			if (userAnswer == total){
				if(showCorrectMessage) EnterText("You are correct!");
				MathQuizGame.score += pointsWorth;

				newNumbers();
			} else {
				if(showCorrectMessage) EnterText("Wrong. Correct Answer: " + total);

				newNumbers();

			}
			MathQuizGame.setQuestionState(0);
		} else {

			if (total == userAnswer){
				if(showCorrectMessage) EnterText("You are correct!");
				MathQuizGame.score += pointsWorth;
			}else{
				if(showCorrectMessage) EnterText("Wrong. Correct Answer: " + total);
			}
			if(MathQuizGame.getTimerRunning()){
				MathQuizGame.timer.stop();
			}
			MathQuizGame.endGame();
		}

	}

	public static void startGameElementary(){
		resetFields();
		setDifficulty(ELEMENTARY_DIFFICULTY);
		EnterText("Starting a new game set in the Elementary Difficulty.");
		if(MathQuizGame.getTimerRunning()){
			MathQuizGame.timer.start();
		}
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		GenerateNewNumbers(0);
		determineQuestionToAsk();
		//MathQuizGame.number1 = number1;
		//MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}
	public static void startGameMiddle(){
		resetFields();
		setDifficulty(MIDDLE_SCHOOL_DIFFICULTY);
		EnterText("Starting a new game set in the Middle School Difficulty.");
		if(MathQuizGame.getTimerRunning()){
			MathQuizGame.timer.start();
		}
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		GenerateNewNumbers(1);
		determineQuestionToAsk();
		//MathQuizGame.number1 = number1;
		//MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}
	public static void startGameHigh(){
		resetFields();
		setDifficulty(HIGH_SCHOOL_DIFFICULTY);
		EnterText("Starting a new game set in the High School Difficulty.");
		if(MathQuizGame.getTimerRunning()){
			MathQuizGame.timer.start();
		}
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		GenerateNewNumbers(2);
		determineQuestionToAsk();
		//total = number1 + number2;
		//MathQuizGame.number1 = number1;
		//MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}
	public static void startGameCustom(String[] customArgs) throws NumbersAreSameException{
		resetFields();
		setDifficulty(CUSTOM_DIFFICULTY);
		EnterText("Starting a new game set in the Custom Difficulty.");
		if(MathQuizGame.getTimerRunning()){
			MathQuizGame.timer.start();
		}
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);

		int maxRangeLocal = Integer.parseInt(customArgs[0]);
		int minRangeLocal;
		// If User only specifies one number and that's it, the program should automatically set
		// the minimum range to 0 and the max to whatever the user specified.
		if(customArgs.length > 1){minRangeLocal = Integer.parseInt(customArgs[1]);} else { minRangeLocal = 0; }

		if(maxRangeLocal < minRangeLocal){
			customMaxRange = minRangeLocal;
			customMinRange = maxRangeLocal;
		}else if (maxRangeLocal > minRangeLocal){
			customMaxRange = maxRangeLocal;
			customMinRange = minRangeLocal;
		}else if (maxRangeLocal == minRangeLocal){
			throw new NumbersAreSameException("Numbers cannot be same.");
		}

		char[] customOperationArray = null;

		if(customArgs.length > 2){
			customOperationArray = customArgs[2].toCharArray();
		} else{
			customOperationArray[0] = '+';
		}
		customOperation = customOperationArray[0];

		if(customArgs.length > 3){
			int CustomNumberOfTimesToBePlayedLocal = Integer.parseInt(customArgs[3]);
			numberOfTimesWillBePlayed = CustomNumberOfTimesToBePlayedLocal;
			pointsWorth = 100 / numberOfTimesWillBePlayed;
		}else if (customArgs.length == 3){
			numberOfTimesWillBePlayed = 10;
			pointsWorth = 10;
		}

		GenerateNewNumbers(3);
		determineQuestionToAsk();
		//MathQuizGame.number1 = number1;
		//MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}



	private static void EnterText(String txt){
		MathQuizGame.EnterText(txt);
	}

	private static void GenerateNewNumbers(int difficulty){
		switch (difficulty) {
			case 0:
				number1 = generator.nextInt(11);
				number2 = generator.nextInt(11);
				operationToUse = '+';
				numberOfTimesWillBePlayed = 10;
				pointsWorth = 10;
				break;
			case 1:
				number1 = generator.nextInt(21);
				number2 = generator.nextInt(21);
				if(number1<=10&&number2<=10){
					operationToUse = randomOp('+','x',' ',' ');
				}else{
					operationToUse = '+';
				}

				numberOfTimesWillBePlayed = 10;
				pointsWorth = 10;
				break;
			case 2:
				number1 = generator.nextInt(41) - 20;
				number2 = generator.nextInt(41) - 20;
				operationToUse = randomOp('+','-','x',' ');
				numberOfTimesWillBePlayed = 10;
				pointsWorth = 10;
				break;
			case 3:
				number1 = generator.nextInt(customMaxRange - customMinRange) + customMinRange;
				number2 = generator.nextInt(customMaxRange - customMinRange) + customMinRange;
				operationToUse = customOperation;
				break;
			default:
				EnterText("Error: Difficulty out of range: " + difficulty);
				break;
		}
	}

	private static void setDifficulty(int newDifficulty){
		MathQuizGame.setDifficulty(newDifficulty);
	}

	private static void resetFields(){
		MathQuizGame.numberOfTimesPlayed = 1;
		MathQuizGame.score = 0;
		total = 0;
		number1 = 0;
		number2 = 0;
	}

	private static char randomOp(char op1, char op2, char op3, char op4){
		char[] ops = new char[4];
		ops[0] = op1;
		ops[1] = op2;
		ops[2] = op3;
		ops[3] = op4;

		if(op4 == ' '){
			int randomIndex = generator.nextInt(3);
			return ops[randomIndex];
		}else if (op3 == ' '){
			int randomIndex = generator.nextInt(2);
			return ops[randomIndex];
		}else{
			int randomIndex = generator.nextInt(4);
			return ops[randomIndex];
		}
	}

	private static void determineQuestionToAsk(){
		if(operationToUse == '+'){
			total = number1 + number2;
			EnterText("What is " + number1 + " + " + number2 + "?");
		}else if (operationToUse == '-'){
			total = number1 - number2;
			EnterText("What is " + number1 + " - " + number2 + "?");
		}else{
			total = number1 * number2;
			EnterText("What is " + number1 + " x " + number2 + "?");
		}
	}
	private static void newNumbers(){
		MathQuizGame.numberOfTimesPlayed += 1;
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		GenerateNewNumbers(MathQuizGame.difficultyLevel);
		determineQuestionToAsk();
		//MathQuizGame.number1 = number1;
		//MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}
}
