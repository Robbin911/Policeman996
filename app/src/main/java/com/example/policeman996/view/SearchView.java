package com.example.policeman996.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import androidx.cardview.widget.CardView;
import com.example.policeman996.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SearchView extends RelativeLayout {

  private MaterialEditText mSearchText;
  private CardView mBtn;


  public SearchView(Context context) {
    super(context);
    initView();
  }

  public SearchView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.search_view, this, true);
    mSearchText = findViewById(R.id.search_edit);
    mBtn = findViewById(R.id.btn_search);
  }

  public String getKeyString() {
    String str = "";
    if (mSearchText != null && mSearchText.getText() != null) {
      str = mSearchText.getText().toString();
    }
    return str;
  }

  public void setOnClick(OnClickListener listener) {
    mBtn.setOnClickListener(listener);
  }

}
