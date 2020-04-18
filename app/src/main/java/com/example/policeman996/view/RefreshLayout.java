package com.example.policeman996.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.cardview.widget.CardView;
import com.example.policeman996.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sackcentury.shinebuttonlib.ShineButton;

public class RefreshLayout extends LinearLayout {

  private ShineButton mBtn;


  public RefreshLayout(Context context) {
    super(context);
    initView();
  }

  public RefreshLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.refresh_layout, this, true);
    mBtn = findViewById(R.id.refresh_icon);
  }

  public void setOnClick(OnClickListener listener) {
    mBtn.setOnClickListener(listener);
  }

  public void reset(){
    mBtn.setChecked(false);
  }

}
