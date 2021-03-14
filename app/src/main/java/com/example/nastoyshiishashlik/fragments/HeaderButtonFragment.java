package com.example.nastoyshiishashlik.fragments;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.ui.MainActivity;

import butterknife.OnClick;

public class HeaderButtonFragment extends BaseFragment {
    private static final String TAG = HeaderButtonFragment.class.getCanonicalName();

    @Override
    public int getViewId() {
        return R.layout.fragment_header_button;
    }

    @Override
    public void onViewCreated(View view) {

    }

    @OnClick(R.id.header_button__iv_logo)
    public void onClickLogo(){
        Intent intent = new Intent(context, MainActivity.class);
        context.finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.header_button__iv_phone)
    public void onClickPhone(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+380672515945"));
        startActivity(intent);
    }

    @OnClick(R.id.header_button__iv_menu)
    public void onClickMenu(){
        PopupDialogFragment fragment = PopupDialogFragment.newInstance();
        fragment.show(getChildFragmentManager(), "lol");
    }
}
