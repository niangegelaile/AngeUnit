package com.example.ange.module.add;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.ange.utils.ActivityUtils;
import com.example.ange.R;
import com.example.ange.ViewModelFactory;


/**
 * 首页:使用dagger注入presenter,
 * Created by liquanan on 2016/10/1.
 */
public class AddActivity extends AppCompatActivity  {


    EditText etAccount;

    EditText etPass;

    Button butLogin;

    private AddViewModel addViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addViewModel=obtainViewModel(this);
        AddFragment addFragment=obtainViewFragment();
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                addFragment, R.id.contentFrame);
    }




    void login() {
        String name = etAccount.getText().toString();
        String position = etPass.getText().toString();

    }

    void tip(final long id) {
        new AlertDialog.Builder(this)
                .setTitle("删除提示")
                .setView(getLayoutInflater().inflate(getDialogRes(), null))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    public int getDialogRes() {
        return R.layout.dialog_delete_tip;
    }

    public static AddViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        AddViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(AddViewModel.class);

        return viewModel;
    }
    @NonNull
    private AddFragment obtainViewFragment() {
        // View Fragment
        AddFragment addEditTaskFragment = (AddFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (addEditTaskFragment == null) {
            addEditTaskFragment = AddFragment.newInstance();

            // Send the task ID to the fragment
            Bundle bundle = new Bundle();
            bundle.putString(AddFragment.ARGUMENT_EDIT_TASK_ID,
                    getIntent().getStringExtra(AddFragment.ARGUMENT_EDIT_TASK_ID));
            addEditTaskFragment.setArguments(bundle);
        }
        return addEditTaskFragment;
    }
}
