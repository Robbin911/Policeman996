package com.example.policeman996.recycler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.policeman996.R;
import com.example.policeman996.Utils.JumpUtils;
import com.example.policeman996.model.bean.CompanyData;
import com.example.policeman996.presenter.Interface.IUserPageView;
import java.util.List;

public class UserCompanyAdapter extends BaseQuickAdapter<CompanyData, BaseViewHolder> {

  private IUserPageView mView;
  public UserCompanyAdapter(int layoutResId, @Nullable List<CompanyData> data) {
    super(layoutResId, data);
  }

  public void setmView(IUserPageView mView) {
    this.mView = mView;
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    UserCompanyAdapter mAdapter = this;
    holder.itemView.findViewById(R.id.company_layout).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
          return;
        }

        mView.selectCompany(mAdapter.getData().get(position));

      }
    });
  }


  @Override
  protected void convert(BaseViewHolder helper, CompanyData item) {
    helper.setText(R.id.company_name, item.getCompanyName());
  }
}
