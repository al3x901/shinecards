package com.example.shinecards.model;


import java.util.ArrayList;

/**
 * Created by Lwdthe1 on 2/13/2015.
 */
public class Quiz extends ArrayList{

    public ArrayList<Question> quiz;
    public String category;
    public int difficulty;

    public Quiz(ArrayList<Question> quiz){
        if(quiz!=null) {
            this.quiz = quiz;
            this.category = getQuizCategory(quiz);
        }
    }

    public Quiz(ArrayList<Question> quizzes, String category, int difficulty) {
        if(quizzes!=null && quizzes.size()>0){
            if(category != null && isAValidDifficulty(difficulty)){
                this.quiz = Quiz.getCategoryDifficultyQuiz(quizzes, category, difficulty);
            } else if(category != null && notAValidDifficulty(difficulty)){
                this.quiz = Quiz.getCategoryQuiz(quizzes, category);
            } else if(category == null && isAValidDifficulty(difficulty)){
                this.quiz = Quiz.getDifficultyQuiz(quizzes, difficulty);
            }

            this.category = getQuizCategory(this.quiz);
            this.difficulty = getQuizDifficulty(this.quiz);
        }

    }

    private boolean notAValidDifficulty(int difficulty) {
        return difficulty != Question.DIFFICULTY_INT_LOW &&
        difficulty != Question.DIFFICULTY_INT_MEDIUM &&
        difficulty != Question.DIFFICULTY_INT_HIGH;
    }

    private boolean isAValidDifficulty(int difficulty) {
        return difficulty == Question.DIFFICULTY_INT_LOW ||
                difficulty == Question.DIFFICULTY_INT_MEDIUM ||
                difficulty == Question.DIFFICULTY_INT_HIGH;
    }

    public ArrayList<Question> getQuiz(){
        return this.quiz;
    }
    public String getCategory(){
        return this.category;
    }
    public int getDifficulty(){
        return this.difficulty;
    }

    private String getQuizCategory(ArrayList<Question> quiz) {
        if( quiz != null && quiz.size()>0 ) return quiz.get(0).getCategory();
        else return null;
    }

    private int getQuizDifficulty(ArrayList<Question> quiz) {
        if( quiz != null && quiz.size()>0 ) return quiz.get(0).getDifficulty();
        else return 0;
    }

    /**
     * Returns an ArrayList of Question objects which have the given {@code category}
     * @param quizzes the ArrayList of Question objects
     * @param category the desired category to retrieve questions by
     * @return An ArrayList of Question objects which have the given {@code category}
     */
    public static ArrayList<Question> getCategoryQuiz(ArrayList<Question> quizzes, String category){
        ArrayList<Question> categoryQuiz = new ArrayList<Question>();
        for(Question question: quizzes){
            if( question.matchesCategoryIgC(category) ){
                categoryQuiz.add(question);
            }
        }
        return categoryQuiz;
    }

    /**
     * Returns an ArrayList of Question objects which have the given {@code difficulty}
     * @param quizzes the ArrayList of Question objects
     * @param difficulty the desired difficulty to retrieve questions by
     * @return An ArrayList of Question objects which have the given {@code difficulty}
     */
    public static ArrayList<Question> getDifficultyQuiz(ArrayList<Question> quizzes, int difficulty){
        ArrayList<Question> difficultyQuiz = new ArrayList<Question>();
        for(Question question: quizzes){
            if( question.matchesDifficulty(difficulty)  ){
                difficultyQuiz.add(question);
            }
        }
        return difficultyQuiz;
    }

    /**
     * Returns an ArrayList of Question objects which have the given {@code category} and {@code difficulty}
     * @param quizzes the ArrayList of Question objects
     * @param category the desired category to retrieve questions by
     * @param difficulty the desired difficulty to retrieve questions by
     * @return An ArrayList of Question objects which have the given {@code category} and {@code difficulty}
     */
    public static ArrayList<Question> getCategoryDifficultyQuiz(ArrayList<Question> quizzes, String category, int difficulty){
        ArrayList<Question> categoryDifficultyQuiz = new ArrayList<Question>();
        for(Question question: quizzes){
            if( question.matchesCategoryIgC(category) && question.matchesDifficulty(difficulty) ){
                categoryDifficultyQuiz.add(question);
            }
        }
        return categoryDifficultyQuiz;
    }

}
