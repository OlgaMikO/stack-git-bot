package ru.tinkoff.edu.java.answer;

public final class StackOverflowAnswer extends ParserAnswer {

    private final long questionID;

    public StackOverflowAnswer(long questionID) {
        super("StackOverflowAnswer");
        this.questionID = questionID;
    }

    public long getQuestionID() {
        return questionID;
    }

    @Override
    public String toString() {
        return "StackOverflowAnswer{" +
                "questionID=" + questionID +
                '}';
    }
}
