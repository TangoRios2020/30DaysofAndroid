package com.tango.a01_geoquiz;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWERED = "answered";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;

    // add a arraylist to hold answered questions
    private ArrayList<Integer> mAnsweredQuestions = new ArrayList<>();

    // 定义一个数据源, 以后会挪到 adapter 中.
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;
    private int mNumberOfCorrectAnswers = 0;
    private int mNumberOfIncorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_main);

        /* onCreate 处检查是否有之前保存的值
         * 在Bundle中存储和恢复的数据类型只能是基本类型, 以及可以实现 Serializable或Parcelable接口的对象
         * 常见的做法: 覆盖 onSaveInstanceState(Bundle)方法，
         * 在Bundle对象中，保存当前activity的小的或
         * 暂存状态的数据;覆盖onStop()方法，保存永久性数据，如用户编辑的文字等。
         * onStop()方法调用完，activity 随时会被系统销毁，所以用它保存永久性数据。
         */
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mAnsweredQuestions = savedInstanceState.getIntegerArrayList(KEY_ANSWERED);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTrueButton.setClickable(false);
                mFalseButton.setClickable(false);
                checkAnswer(true);
                mAnsweredQuestions.add(mCurrentIndex);
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTrueButton.setClickable(false);
                mFalseButton.setClickable(false);
                checkAnswer(false);
                mAnsweredQuestions.add(mCurrentIndex);
            }
        });

        mPreviousButton = findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCurrentIndex < 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                }
                updateQuestion();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    // 在屏幕发生旋转之前, 先保存 currentIndex
    // 此处方法位置也好
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        // 以integer 对应一个 ArrayList
        savedInstanceState.putIntegerArrayList(KEY_ANSWERED, mAnsweredQuestions);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if (mAnsweredQuestions.contains(mCurrentIndex)) {
            mTrueButton.setClickable(false);
            mFalseButton.setClickable(false);
        } else {
            mTrueButton.setClickable(true);
            mFalseButton.setClickable(true);
        }
    }

    @SuppressLint("StringFormatInvalid")
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            mNumberOfCorrectAnswers += 1;
            messageResId = R.string.correct_toast;
        } else {
            mNumberOfIncorrectAnswers += 1;
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        if ((mNumberOfCorrectAnswers + mNumberOfIncorrectAnswers) == mQuestionBank.length) {
            double percentageOfCorrectAnswers = ((double) mNumberOfCorrectAnswers / (double) mQuestionBank.length) * 100;
            Toast.makeText(
                    this,
                    getString(R.string.amount_of_correct_answers) + " " + Integer.toString(mNumberOfCorrectAnswers) + "\n" +
                          getString(R.string.amount_of_correct_answers) + Double.toString(percentageOfCorrectAnswers),
                    Toast.LENGTH_LONG).show();
        }
    }
}
