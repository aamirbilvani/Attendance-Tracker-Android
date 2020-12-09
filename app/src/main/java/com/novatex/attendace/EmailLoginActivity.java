package com.novatex.attendace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.novatex.attendace.api.ApiCallRequest;
import com.novatex.attendace.responses.LoginResponse;
import com.novatex.attendace.utilities.Constant;
import com.novatex.attendace.utilities.NumericKeyBoardTransformationMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.novatex.attendace.utilities.Constant.GENERIC_ERROR_API;
import static com.novatex.attendace.utilities.Utility.initLoggedInParam;
import static com.novatex.attendace.utilities.Utility.navigateToActivity;

public class EmailLoginActivity extends AppCompatActivity implements ApiCallRequest.CallBackListener, View.OnClickListener {


    private ApiCallRequest callRequest;

    private EditText editTextCNIC, pass;
    private Button buttonLogin, buttonCreate;
    private ProgressDialog dialog;
    private TextView textViewCNICError, textViewPasswordError, textViewForgotPassword;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        //To intialize screen
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonLogin:
                   if (isValid()) {
              //  dialog.setMessage("Loggin In, please wait.");
               // dialog.show();
                //Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

                //textViewPasswordError.setVisibility(View.VISIBLE);

                //textViewPasswordError.setText("ok 53");
                callRequest.requestLogin(editTextCNIC.getText().toString(), pass.getText().toString());
                //login();
                 }
                break;
            case R.id.buttonCreate:

                navigateToActivity(this, SignUpActivity.class);
                break;


            default:
                break;
        }
    }

    @Override
    public void callBack(int requestType, Object object) {

        switch (requestType) {
            case Constant.REQUEST_LOGIN:

                LoginResponse loginResponse = (LoginResponse) object;
                initLoggedInParam(this,loginResponse.getResponse().getData());
                login();
                break;

            case Constant.REQUEST_FAILED:
                try {
                    Toast.makeText(getApplicationContext(), (String) object, Toast.LENGTH_SHORT).show();

                    //textViewPasswordError.setVisibility(View.VISIBLE);
                    //textViewPasswordError.setText("error 86" + (String) object);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), GENERIC_ERROR_API, Toast.LENGTH_SHORT).show();

                    //textViewPasswordError.setVisibility(View.VISIBLE);
                    //textViewPasswordError.setText(GENERIC_ERROR_API + "91");
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), GENERIC_ERROR_API, Toast.LENGTH_SHORT).show();

                //textViewPasswordError.setVisibility(View.VISIBLE);
                //textViewPasswordError.setText(GENERIC_ERROR_API + "98");
                break;

        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    private void init() {
        //initializing components
        editTextCNIC = findViewById(R.id.editTextCNIC);
        pass = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        dialog = new ProgressDialog(this);
        textViewCNICError = findViewById(R.id.textViewCNICError);
        textViewPasswordError = findViewById(R.id.textViewPasswordError);
        textViewPasswordError = findViewById(R.id.textViewPasswordError);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        buttonCreate = findViewById(R.id.buttonCreate);
        //initializing callRequest for API
        callRequest = new ApiCallRequest(getApplicationContext());
        callRequest.setCallBackListener(this);

        //setting buttons onClickListeners
        buttonLogin.setOnClickListener(this);
        buttonCreate.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);

        editTextCNIC.setTransformationMethod(new NumericKeyBoardTransformationMethod());

    }


    private void login() {
        try {
            Intent i = new Intent(this, MarkAttendanceActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid() {
        //initailly its set to true, if any validation error occurs it should be set to false
        //if at the end of the func flag is true it means the form is valid else if its false then it means the form requires some validations
        //flag will be returned in this function that means if the form is valid this func will return true else false.
        boolean flag = true;

        //checking if editTextCNIC is empty or not in cnic format
        if (editTextCNIC.getText().toString().trim().equals("")) {

            textViewCNICError.setText("Please enter CNIC its a mandatory field");
            textViewCNICError.setVisibility(View.VISIBLE);
            flag = false;

        } else {
            final Pattern VALID_CNIC_REGEX = Pattern.compile("^[0-9]{13}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_CNIC_REGEX.matcher(editTextCNIC.getText().toString().trim());
            if (!matcher.find()) {
                textViewCNICError.setText("CNIC is not in correct format, it should be 13 digits without dashes");
                textViewCNICError.setVisibility(View.VISIBLE);
                flag = false;
            } else {
                textViewCNICError.setVisibility(View.INVISIBLE);
            }
        }

        //checking if pass is empty
        if (pass.getText().toString().trim().equals("")) {
            textViewPasswordError.setVisibility(View.VISIBLE);
            flag = false;

        } else {
            textViewPasswordError.setVisibility(View.INVISIBLE);
        }

        return flag;

    }


}
