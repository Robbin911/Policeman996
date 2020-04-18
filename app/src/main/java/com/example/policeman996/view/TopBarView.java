package com.example.policeman996.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.policeman996.R;

public class TopBarView extends RelativeLayout {


  private ImageView mUserIcon;
  private TextView mTitle;
  private ImageView mSearchIcon;

  private LinearLayout mSearchKeyLayout;
  private TextView mSearchKey;

  private String mKey;

  public TopBarView(Context context) {
    super(context);
    initView();
  }

  public TopBarView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.top_bar_layout, this, true);
    mUserIcon = findViewById(R.id.iv_user);
    mTitle = findViewById(R.id.tv_title);
    mSearchIcon = findViewById(R.id.iv_search);
    mSearchKeyLayout = findViewById(R.id.title_search_key_layout);
    mSearchKey = findViewById(R.id.title_search_key);

  }

  public void setOnUserClick(OnClickListener listener) {
    mUserIcon.setOnClickListener(listener);
  }

  public void setOnSearchClick(OnClickListener listener) {
    mSearchIcon.setOnClickListener(listener);
  }


  public void setTitle(int index) {
    switch (index) {
      case 0:
        mTitle.setText(R.string.check);
        mSearchIcon.setVisibility(GONE);
        mSearchKeyLayout.setVisibility(GONE);
        break;
      case 1:
        mTitle.setText(R.string.company);
        mSearchIcon.setVisibility(VISIBLE);
        setSearchKey(mKey);
        break;
    }
  }

  public void setSearchKey(String key) {
    mKey = key;
    if (key.equals("")) {
      mSearchKeyLayout.setVisibility(GONE);
    } else {
      mSearchKeyLayout.setVisibility(VISIBLE);
      mSearchKey.setText(key);
    }
  }


}
