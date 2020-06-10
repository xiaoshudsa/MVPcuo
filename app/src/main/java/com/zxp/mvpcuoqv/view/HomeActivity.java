package com.zxp.mvpcuoqv.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.base.BaseMvpActivity;
import com.zxp.mvpcuoqv.model.CommonHomeModel;

public class HomeActivity extends BaseMvpActivity<CommonHomeModel> implements NavController.OnDestinationChangedListener {




    private NavController navController;

    @Override
    protected int getlayout() {
        return R.layout.activity_home;
    }

    @Override
    public CommonHomeModel setModel() {
        return null;
    }

    @Override
    public void setUpView() {
        navController = Navigation.findNavController(this, R.id.project_fragment_control);
        navController.addOnDestinationChangedListener(this);
    }

    @Override
    public void setUpData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pa) {

    }


    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        String s = destination.getLabel().toString();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
