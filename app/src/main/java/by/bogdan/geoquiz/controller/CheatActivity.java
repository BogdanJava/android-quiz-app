package by.bogdan.geoquiz.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import by.bogdan.geoquiz.R;
import by.bogdan.geoquiz.model.Question;

import static by.bogdan.geoquiz.utils.ActivityUtils.EXTRA_ANSWER_SHOWN;
import static by.bogdan.geoquiz.utils.ActivityUtils.EXTRA_QUESTION;

public class CheatActivity extends AppCompatActivity {

    public static final String ANSWER_SHOWN_STATE =
            "by.bogdan.geoquiz.answer_shown_state";

    private Question mQuestion;
    private TextView mAnswerTextView;
    private TextView mApiLevelTextView;
    private Button mShowAnswerButton;
    private boolean isAnswerShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        this.mQuestion = (Question) getIntent().getSerializableExtra(EXTRA_QUESTION);
        if (this.mQuestion == null)
            throw new AndroidRuntimeException("Question extra shouldn't be null");
        this.mShowAnswerButton = findViewById(R.id.show_answer_button);
        this.mAnswerTextView = findViewById(R.id.answer_text_view);
        this.mApiLevelTextView = findViewById(R.id.api_level_text_view);

        this.mShowAnswerButton.setOnClickListener(view -> {
            boolean answerTrue = mQuestion.isAnswerTrue();
            if (answerTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
            isAnswerShown = true;
            setAnswerShownResult();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final int cx = mShowAnswerButton.getWidth() / 2;
                final int cy = mShowAnswerButton.getHeight() / 2;
                float radius = mShowAnswerButton.getWidth();
                final Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mAnswerTextView.setVisibility(View.VISIBLE);
                        mShowAnswerButton.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            } else {
                mAnswerTextView.setVisibility(View.VISIBLE);
                mShowAnswerButton.setVisibility(View.INVISIBLE);
            }
        });

        mApiLevelTextView.setText(getString(R.string.api_level, Build.VERSION.SDK_INT));
        if (savedInstanceState != null) {
            this.isAnswerShown = savedInstanceState.getBoolean(ANSWER_SHOWN_STATE, false);
        }
        setAnswerShownResult();
    }

    private void setAnswerShownResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ANSWER_SHOWN_STATE, isAnswerShown);
    }
}
