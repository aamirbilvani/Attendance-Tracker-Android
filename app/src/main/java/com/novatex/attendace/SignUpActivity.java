package com.novatex.attendace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.novatex.attendace.api.ApiCallRequest;
import com.novatex.attendace.models.Offices;
import com.novatex.attendace.responses.OfficesResponse;
import com.novatex.attendace.responses.SignUpResponse;
import com.novatex.attendace.utilities.Constant;
import com.novatex.attendace.utilities.Utility;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.novatex.attendace.utilities.Constant.GENERIC_ERROR_API;
import static com.novatex.attendace.utilities.Utility.navigateToActivity;

public class SignUpActivity extends AppCompatActivity implements ApiCallRequest.CallBackListener, View.OnClickListener {

    private ApiCallRequest callRequest;

    private ProgressDialog dialog;
    private OfficesResponse officesResponse;

    private EditText editTextCNIC, editTextFullName, editTextPhone,  editTextPassword, editTextConfirmPassword;
    private TextView textViewLogin, textViewCNICError, textViewFullNameError, textViewPhoneError, textViewPasswordError, textViewConfirmPasswordError, textViewOfficeError;
    private Button buttonSignUp;
    private Spinner spinnerOffice;

    private String phoneNumber;
    private ArrayList<String> brokerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
    }

    @Override
    public void callBack(int requestType, Object object) {
        switch (requestType) {
            case Constant.REQUEST_GET_OFFICES:
                officesResponse = (OfficesResponse) object;

                for(Offices o : officesResponse.getResponse().getData())
                {
                    brokerList.add(o.getName());
                }
                break;

            case Constant.REQUEST_SIGNUP:
                SignUpResponse signUpResponse = (SignUpResponse) object;

                Utility.initLoggedInParam(this,signUpResponse.getResponse().getData());

                login();
                break;
            case Constant.REQUEST_FAILED:
                try {
                    Toast.makeText(getApplicationContext(), (String) object, Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), GENERIC_ERROR_API, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), GENERIC_ERROR_API, Toast.LENGTH_SHORT).show();
                break;
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonSignUp:
                //if (isValid()) {
                    dialog.setMessage("Signing Up, please wait.");
                    dialog.show();

                    String office_id="0";

                    TextView textView = (TextView)spinnerOffice.getSelectedView();
                    String officeName = textView.getText().toString();

                    for(Offices o : officesResponse.getResponse().getData())
                    {
                        if(o.getName().equals(officeName))
                        {
                            office_id=o.getId()+"";
                        }
                    }
                    callRequest.requestRegister(editTextCNIC.getText().toString(),editTextPassword.getText().toString(), editTextFullName.getText().toString(), editTextPhone.getText().toString(),office_id);
               // }
                break;
            case R.id.textViewLogin:
                Intent i = new Intent(this, EmailLoginActivity.class);
                startActivity(i);
            default:
                break;
        }
    }

    private void init() {
        callRequest = new ApiCallRequest(getApplicationContext());
        callRequest.setCallBackListener(this);

        dialog = new ProgressDialog(this);

        editTextCNIC = findViewById(R.id.editTextCNIC);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        textViewCNICError = findViewById(R.id.textViewCNICError);
        textViewFullNameError = findViewById(R.id.textViewFullNameError);
        textViewPhoneError = findViewById(R.id.textViewPhoneError);
        textViewPasswordError = findViewById(R.id.textViewPasswordError);
        textViewConfirmPasswordError = findViewById(R.id.textViewConfirmPasswordError);
        textViewOfficeError = findViewById(R.id.textViewOfficeError);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.textViewLogin);
        spinnerOffice = findViewById(R.id.spinnerOffice);

        brokerList = new ArrayList<>();
        brokerList.add("-- Select Office --");

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        if (phoneNumber != null && phoneNumber != "") {
            editTextPhone.setText(phoneNumber);
            editTextPhone.setEnabled(false);
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, brokerList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout);

        spinnerOffice.setAdapter(arrayAdapter);

        buttonSignUp.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

        callRequest.requestGetOffices();

    }

    private boolean isValid() {
        //initailly its set to true, if any validation error occurs it should be set to false
        //if at the end of the func flag is true it means the form is valid else if its false then it means the form requires some validations
        //flag will be returned in this function that means if the form is valid this func will return true else false.
        boolean flag = true;

        //Username Mandatory check
        if (editTextCNIC.getText().toString().trim().equals("")) {
            textViewCNICError.setVisibility(View.VISIBLE);
            flag = false;

        } else {
            textViewCNICError.setVisibility(View.INVISIBLE);

        }

        //Email Mandatory check
        if (editTextFullName.getText().toString().trim().equals("")) {
            textViewFullNameError.setText("Please enter Email its a mandatory field");
            textViewFullNameError.setVisibility(View.VISIBLE);
            flag = false;

        } else {

            //Email correct format check

            final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(editTextFullName.getText().toString().trim());
            if (!matcher.find()) {
                textViewFullNameError.setText("Email is not in correct format");
                textViewFullNameError.setVisibility(View.VISIBLE);
                flag = false;
            } else {
                textViewFullNameError.setVisibility(View.INVISIBLE);
            }

        }


        //Phone Mandatory check
        if (editTextPhone.getText().toString().trim().equals("")) {
            textViewPhoneError.setText("Please enter Phone its a mandatory field");
            textViewPhoneError.setVisibility(View.VISIBLE);
            flag = false;

        } else {
            //Phone correct format check

            final Pattern VALID_PHONE_ADDRESS_REGEX = Pattern.compile("^((\\+\\d{1,3}(-| )?\\(?\\d\\)?(-| )?\\d{1,3})|(\\(?\\d{2,3}\\)?))(-| )?(\\d{3,4})(-| )?(\\d{4})(( x| ext)\\d{1,5}){0,1}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_PHONE_ADDRESS_REGEX.matcher(editTextPhone.getText().toString().trim());
            if (!matcher.find()) {
                textViewPhoneError.setText("Phone is not in correct format");
                textViewPhoneError.setVisibility(View.VISIBLE);
                flag = false;
            } else {
                textViewPhoneError.setVisibility(View.INVISIBLE);
            }


        }

        //Password Mandatory Check
        if (editTextPassword.getText().toString().trim().equals("")) {
            textViewPasswordError.setVisibility(View.VISIBLE);
            flag = false;

        } else {
            textViewPasswordError.setVisibility(View.INVISIBLE);

        }

        //Confirm Password Mandatory Check
        if (editTextConfirmPassword.getText().toString().trim().equals("")) {
            textViewConfirmPasswordError.setText("Please enter Confirm Password its a mandatory field");
            textViewConfirmPasswordError.setVisibility(View.VISIBLE);
            flag = false;

        } else {
            textViewConfirmPasswordError.setVisibility(View.INVISIBLE);

        }


        //Password and Confirm Password equality Check
        if (!editTextConfirmPassword.getText().toString().trim().equals(editTextPassword.getText().toString().trim())) {
            textViewConfirmPasswordError.setText("Password and Confirm Password should be same");
            textViewConfirmPasswordError.setVisibility(View.VISIBLE);
            flag = false;

        } else {
            textViewConfirmPasswordError.setVisibility(View.INVISIBLE);

        }


        //Broker Selected check
        if (spinnerOffice.getSelectedItemPosition() == 0) {
            textViewOfficeError.setText("Please select Office its a madatry field");
            textViewOfficeError.setVisibility(View.VISIBLE);
            flag = false;

        } else {
            textViewOfficeError.setVisibility(View.INVISIBLE);

        }

        return flag;
    }

}
