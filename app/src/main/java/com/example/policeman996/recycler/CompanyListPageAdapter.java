package com.example.policeman996.recycler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.policeman996.R;
import com.example.policeman996.Utils.JumpUtils;
import com.example.policeman996.model.bean.CompanyData;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class CompanyListPageAdapter extends BaseQuickAdapter<CompanyData, BaseViewHolder> {

  public CompanyListPageAdapter(int layoutResId, @Nullable List<CompanyData> data) {
    super(layoutResId, data);
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    CompanyListPageAdapter mAdapter = this;
    holder.itemView.findViewById(R.id.company_layout).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
          return;
        }

        JumpUtils.startCompanyDetailActivity(holder.itemView.getContext(),
            mAdapter.getData().get(position));

      }
    });
  }


  @Override
  protected void convert(BaseViewHolder helper, CompanyData item) {
    helper.setText(R.id.company_id, item.getCompanyId() + "");
    helper.setText(R.id.company_name, item.getCompanyName());
    helper.setText(R.id.rate_tv, item.getRate() + "");
    int rate = item.getRate();
    if (rate < 40) {
      helper.setTextColor(R.id.rate_tv, helper.itemView.getResources().getColor(R.color.colorRed));
    } else if (rate >= 40 && rate < 70) {
      helper
          .setTextColor(R.id.rate_tv, helper.itemView.getResources().getColor(R.color.colorYellow));
    } else if (rate >= 70) {
      helper
          .setTextColor(R.id.rate_tv, helper.itemView.getResources().getColor(R.color.colorGreen));
    }
  }


}
