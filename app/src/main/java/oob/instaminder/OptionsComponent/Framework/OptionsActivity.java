package oob.instaminder.OptionsComponent.Framework;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oob.instaminder.ApplicationComponent.BaseApplication;
import oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCase;
import oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase.Model.UserInformation;
import oob.instaminder.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCase;
import oob.instaminder.OptionsComponent.Domain.ViewInterface;
import oob.instaminder.OptionsComponent.Framework.DependencyInjection.DaggerOptionsComponentInterface;
import oob.instaminder.OptionsComponent.Framework.DependencyInjection.OptionsComponentInterface;
import oob.instaminder.OptionsComponent.Framework.DependencyInjection.OptionsComponentModule;
import oob.instaminder.R;

public class OptionsActivity extends AppCompatActivity implements ViewInterface {
    public static final int REQUEST_CODE_SAVE_USER_INFORMATION = 0;

    @BindView(R.id.nickInputWrapper)
    TextInputLayout nickInputWrapper;
    @BindView(R.id.nickInput)
    EditText nickInput;
    @BindView(R.id.passwordInputWrapper)
    TextInputLayout passwordInputWrapper;
    @BindView(R.id.passwordInput)
    EditText passwordInput;

    @Inject
    SaveUserInformationUseCase saveUserInformationUseCase;
    @Inject
    GetUserInformationUseCase getUserInformationUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        ButterKnife.bind(this);

        OptionsComponentInterface component = DaggerOptionsComponentInterface.builder()
                .baseApplicationComponentInterface(((BaseApplication) this.getApplication()).getComponent())
                .optionsComponentModule(new OptionsComponentModule(this))
                .build();
        component.inject(this);

        this.init();
    }

    private void init() {
        this.tintActionBarTextColor();
        this.setTitle();

        UserInformation userInformation = this.getUserInformationUseCase.get();

        if (!userInformation.getNick().isEmpty()) {
            this.setBackButton();
        }

        this.populateInputsWithSavedData(userInformation);
    }

    private void populateInputsWithSavedData(UserInformation userInformation) {
        this.nickInput.setText(userInformation.getNick());
        this.passwordInput.setText(userInformation.getPassword());
    }

    private void setTitle() {
        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar == null) {
            return;
        }
        actionBar.setTitle(R.string.options_component_title);
    }

    private void setBackButton() {
        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void tintActionBarTextColor() {
        Toolbar actionBarToolbar = this.findViewById(R.id.action_bar);
        if (actionBarToolbar == null) {
            return;
        }
        actionBarToolbar.setTitleTextColor(this.getResources().getColor(R.color.colorPrimary));
        actionBarToolbar.setBackgroundColor(this.getResources().getColor(R.color.colorAccentWhite));

        Drawable icon = actionBarToolbar.getNavigationIcon();
        if (icon == null) {
            return;
        }

        icon.setColorFilter(this.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.setResult(Activity.RESULT_OK);
        this.finish();
        return true;
    }

    @OnClick(R.id.btnSaveUserInformation)
    public void onBtnSaveUserInformationClicked() {
        String nick = this.nickInput.getText().toString();
        String password = this.passwordInput.getText().toString();

        this.clearInputsErrors();

        if (nick.isEmpty()) {
            this.showNickInputError();
            return;
        }

        if (password.isEmpty()) {
            this.showPasswordInputError();
            return;
        }

        this.saveUserInformationUseCase.save(nick, password);
        this.setResult(Activity.RESULT_OK);
        this.finish();
    }

    private void clearInputsErrors() {
        this.nickInputWrapper.setErrorEnabled(false);
        this.nickInputWrapper.setError("");
        this.passwordInputWrapper.setErrorEnabled(false);
        this.passwordInputWrapper.setError("");
    }

    private void showNickInputError() {
        this.showInputEmptyError(this.nickInputWrapper);
    }

    private void showPasswordInputError() {
        this.showInputEmptyError(this.passwordInputWrapper);
    }

    private void showInputEmptyError(TextInputLayout wrapper) {
        wrapper.setErrorEnabled(true);
        wrapper.setError(this.getString(R.string.options_component_empty_input_error_message));
    }
}
