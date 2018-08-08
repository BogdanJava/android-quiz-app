package by.bogdan.geoquiz.model;

import java.io.Serializable;

public class Question implements Serializable {
    private final int mId;
    private final int mTextResId;
    private final boolean mAnswerTrue;

    public Question(int id, int textResId, boolean answerTrue) {
        mId = id;
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public int getId() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Question)) return false;
        Question obj = (Question) o;
        return this.mTextResId == obj.mTextResId;
    }
}
