package com.example.policeman996.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.cardview.widget.CardView;
import com.example.policeman996.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SurveyView extends RelativeLayout {

  private MaterialEditText mScoreText;
  private CardView mSubmitBtn;
  private ImageView mCloseBtn;


  public SurveyView(Context context) {
    super(context);
    initView();
  }

  public SurveyView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.survey_dialog, this, true);
    mScoreText = findViewById(R.id.score_edit);
    mSubmitBtn = findViewById(R.id.btn_submit);
    mCloseBtn = findViewById(R.id.close_btn);

    mScoreText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().equals("")) {
          return;
        }
        int score = Integer.parseInt(s.toString());
        if (score > 10) {
          mScoreText.setText("10");
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

  }

  public int getScore() {
    String str = "";
    if (mScoreText != null && mScoreText.getText() != null) {
      str = mScoreText.getText().toString();
    }
    int score = Integer.parseInt(str);
    return score;
  }

  public void setOnClick(OnClickListener listener) {
    mCloseBtn.setOnClickListener(listener);
    mSubmitBtn.setOnClickListener(listener);
  }

}