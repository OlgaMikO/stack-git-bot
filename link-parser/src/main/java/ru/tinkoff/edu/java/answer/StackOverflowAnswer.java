package ru.tinkoff.edu.java.answer;

public final class StackOverflowAnswer extends ParserAnswer {

    private long questionID;

    public StackOverflowAnswer(long questionID) {
        super();
        this.questionID = questionID;
    }

    @Override
    public String toString() {
        return "StackOverflowAnswer{" +
                "questionID=" + questionID +
                '}';
    }
}
