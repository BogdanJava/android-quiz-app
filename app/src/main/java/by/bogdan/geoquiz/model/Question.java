package by.bogdan.geoquiz.model;

import java.io.Serializable;

public class Question implements Serializable {
    private final int mTextResId;
    private final boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

}
