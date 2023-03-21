package ru.tinkoff.edu.java.parser;

final class StackOverflowAnswer extends ParserAnswer {

    private long questionID;

    public StackOverflowAnswer(long questionID) {
        super();
        this.questionID = questionID;
    }

    public String toString(){
        return String.format("questionID: %s\n", questionID);
    }
}
