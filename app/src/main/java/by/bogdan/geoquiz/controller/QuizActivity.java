package by.bogdan.geoquiz.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import by.bogdan.geoquiz.R;
import by.bogdan.geoquiz.model.Question;
import by.bogdan.geoquiz.utils.ActivityUtils;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = QuizActivity.class.getName();
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String CHEATED_QUESTIONS_INDEX = "cheated_set";

    private final Question[] mQuestions = new Question[]{
            new Question(0, R.string.question_oceans, true),
            new Question(1, R.string.question_mideast, false),
            new Question(2, R.string.question_africa, false),
            new Question(3, R.string.question_americas, true),
            new Question(4, R.string.question_asia, true),
    };
    private int[] mCheatedQuestions = new int[5];
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);
        mCheatButton = findViewById(R.id.cheat_button);
        mQuestionTextView = findViewById(R.id.question_text_view);

        mTrueButton.setOnClickListener(view ->
                showAnswerToast(QuizActivity.this, getAnswerToastId(
                        mQuestions[mCurrentIndex], true
                )));
        mFalseButton.setOnClickListener(view ->
                showAnswerToast(QuizActivity.this, getAnswerToastId(
                        mQuestions[mCurrentIndex], false
                )));
        mCheatButton.setOnClickListener(view -> {
            Intent intent = ActivityUtils.newIntent(QuizActivity.this,
                    mQuestions[mCurrentIndex]);
            startActivityForResult(intent, REQUEST_CODE_CHEAT);
        });
        mPrevButton.setOnClickListener(view -> {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestions.length;
            if (mCurrentIndex < 0) mCurrentIndex += mQuestions.length;
            updateQuestion();
        });
        View.OnClickListener nextQuestionListener = view -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
            updateQuestion();
        };
        mNextButton.setOnClickListener(nextQuestionListener);
        mQuestionTextView.setOnClickListener(nextQuestionListener);
        if (savedInstanceState != null) {
            this.mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            this.mCheatedQuestions = savedInstanceState.getIntArray(CHEATED_QUESTIONS_INDEX);
        }
        updateQuestion();
    }

    private void updateQuestion() {
        Question currentQuestion = mQuestions[mCurrentIndex];
        mQuestionTextView.setText(currentQuestion.getTextResId());
    }

    private int getAnswerToastId(Question question, boolean answer) {
        return question.isAnswerTrue() == answer ? R.string.correct_toast : R.string.incorrect_toast;
    }

    private void showAnswerToast(Context context, int answerId) {
        int toastId = isQuestionCheated() ? R.string.judgment_toast : answerId;
        Toast.makeText(context, toastId, Toast.LENGTH_SHORT).show();
    }

    private boolean isQuestionCheated() {
        return mCheatedQuestions[mCurrentIndex] == 1;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState(Bundle) called");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putIntArray(CHEATED_QUESTIONS_INDEX, mCheatedQuestions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) return;
            boolean isCheater = ActivityUtils.hasUserCheated(data);
            int questionId = mQuestions[mCurrentIndex].getId();
            if (isCheater) {
                mCheatedQuestions[questionId] = 1;
            }
        }
    }
}
