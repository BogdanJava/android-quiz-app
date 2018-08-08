package by.bogdan.geoquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidRuntimeException;
import android.widget.Button;
import android.widget.TextView;

import by.bogdan.geoquiz.R;
import by.bogdan.geoquiz.model.Question;

import static by.bogdan.geoquiz.utils.ActivityUtils.EXTRA_ANSWER_SHOWN;
import static by.bogdan.geoquiz.utils.ActivityUtils.EXTRA_QUESTION;

public class CheatActivity extends AppCompatActivity {
    private Question mQuestion;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        this.mQuestion = (Question) getIntent().getSerializableExtra(EXTRA_QUESTION);
        if (this.mQuestion == null)
            throw new AndroidRuntimeException("Question extra shouldn't be null");
        this.mShowAnswerButton = findViewById(R.id.show_answer_button);
        this.mAnswerTextView = findViewById(R.id.answer_text_view);

        this.mShowAnswerButton.setOnClickListener(view -> {
            boolean answerTrue = mQuestion.isAnswerTrue();
            if (answerTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
            setAnswerShownResult(true);
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intent);
    }
}
