package entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    private String title;
    @SerializedName("possible_answers")
    private List<String> possibleAnswers;
    @SerializedName("correct_answer")
    private String correctAnswer;

    public Question() {
    }

    public String getTitle() {
        return title;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", possibleAnswers=" + possibleAnswers +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
