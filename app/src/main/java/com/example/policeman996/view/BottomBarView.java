package com.example.policeman996.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.policeman996.R;

public class BottomBarView extends RelativeLayout {

  private RelativeLayout mTabCheck;
  private ImageView mIvCheck;
  private RelativeLayout mTabCompany;
  private ImageView mIvCompany;

  public BottomBarView(Context context) {
    super(context);
    initView();
  }

  public BottomBarView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.bottom_bar_layout, this, true);
    mTabCheck = findViewById(R.id.tab_check);
    mIvCheck = findViewById(R.id.iv_check);

    mTabCompany = findViewById(R.id.tab_company);
    mIvCompany = findViewById(R.id.iv_company);

  }

  private void resetSelect() {
    if (mIvCompany != null && mIvCheck != null) {
      mIvCompany.setImageDrawable(getResources().getDrawable(R.drawable.company_unselected));
      mIvCheck.setImageDrawable(getResources().getDrawable(R.drawable.check_unselected));
    }
  }

  public void setSelect(int index) {
    resetSelect();
    switch (index) {
      case 0:
        mIvCheck.setImageDrawable(getResources().getDrawable(R.drawable.check_selected));
        break;
      case 1:
        mIvCompany.setImageDrawable(getResources().getDrawable(R.drawable.company_selected));
        break;
    }
  }

  public void setOnClick(OnClickListener listener) {
    mTabCheck.setOnClickListener(listener);
    mTabCompany.setOnClickListener(listener);
  }

}