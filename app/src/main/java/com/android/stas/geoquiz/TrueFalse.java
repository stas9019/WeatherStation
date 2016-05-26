package com.android.stas.geoquiz;

/**
 * Created by root on 12.04.16.
 */
public class TrueFalse {

    private int mQuestionNumber;

    private boolean mAnswer;



    public TrueFalse(int mQuestionNumber, boolean mIsTrue) {
        this.mQuestionNumber = mQuestionNumber;
        this.mAnswer = mIsTrue;
    }

    public int getQuestionReference() {
        return mQuestionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        mQuestionNumber = questionNumber;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }


}
