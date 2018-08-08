package by.bogdan.geoquiz.utils;

import android.content.Context;
import android.content.Intent;

import by.bogdan.geoquiz.controller.CheatActivity;
import by.bogdan.geoquiz.model.Question;

public class ActivityUtils {
    public static final String EXTRA_QUESTION =
            "by.bogdan.geoquiz.question";
    public static final String EXTRA_ANSWER_SHOWN =
            "by.bogdan.geoquiz.answer_shown";

    public static Intent newIntent(Context context, Question question) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_QUESTION, question);
        return intent;
    }

    public static boolean hasUserCheated(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
