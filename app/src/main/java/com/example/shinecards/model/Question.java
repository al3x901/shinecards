package com.example.shinecards.model;

/**
 * A class for storing data on questions.
 * Questions can have a question, answer, category, and difficulty
 */
public class Question {
    String category, question, answer;
    int difficulty;
    String heading = "";

    public final static int DIFFICULTY_INT_LOW = 3279;
    public final static int DIFFICULTY_INT_MEDIUM = 6333;
    public final static int DIFFICULTY_INT_HIGH = 4444;

    public Question(){ }

    public Question(String category, int difficulty, String heading, String question, String answer){
        this.category = category;
        this.difficulty = difficulty;
        this.heading = heading;
        this.question = question;
        this.answer = answer;
    }

    public String getCategory(){ return category; }
    public int getDifficulty(){ return difficulty; }
    public String getHeading(){ return heading; }
    public String getQuestion(){ return question; }
    public String getAnswer(){ return answer; }

    public void setCategory(String category){ this.category = category; }
    public void setDifficulty(int difficulty){ this.difficulty = difficulty; }
    public void setHeading(String heading){ this.heading = heading; }
    public void setQuestion(String question){ this.question = question; }
    public void setAnswer(String answer){ this.answer = answer; }

    public boolean matchesCategory(String s){ return category.equals(s); }
    public boolean matchesDifficulty(int i){ return difficulty == i; }
    public boolean matchesQuestion(String s){ return question.equals(s); }
    public boolean matchesAnswer(String s){ return answer.equals(s); }

    public boolean matchesCategoryIgC(String s){ return category.equalsIgnoreCase(s); }
    public boolean matchesHeadingIgC(String s){ return heading.equalsIgnoreCase(s); }
    public boolean matchesQuestionIgC(String s){ return question.equalsIgnoreCase(s); }
    public boolean matchesAnswerIgC(String s){ return answer.equalsIgnoreCase(s); }

    public String toString(){
        String toString = "";
        if( !heading.isEmpty() && heading != null ){ toString += "Heading: " + heading + "\n";}
        toString += "Question: " + question
                + "\nAnswer: " + answer
                + " \nCategory: " + category
                + " \nDifficulty: " + difficulty;
        return toString;
    }

}
