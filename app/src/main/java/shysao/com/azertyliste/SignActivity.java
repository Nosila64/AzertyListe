package shysao.com.azertyliste;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import shysao.com.azertyliste.WebService.HttpClientUtil;

public class SignActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    TextView prenomTV, nomTV, mailTV,lvlTV, asterixTV;
    EditText lnameET,fnameET,mailET;
    Spinner  lvlSpin;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        prenomTV = findViewById(R.id.prenomTV);
        nomTV = findViewById(R.id.nomTV);
        mailTV = findViewById(R.id.mailTV);
        lvlTV = findViewById(R.id.lvlTV);
        asterixTV = findViewById(R.id.asterix);

        prenomTV.setText(R.string.lname_ask);
        nomTV.setText(R.string.fname_ask);
        mailTV.setText(R.string.mail_ask);
        lvlTV.setText(R.string.lvl_ask);
        asterixTV.setText(R.string.asterix);

        lnameET = findViewById(R.id.lnameET);
        fnameET = findViewById(R.id.fnameET);
        mailET = findViewById(R.id.mailET);


        lvlSpin = findViewById(R.id.lvlSpin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
        lnameET.setOnFocusChangeListener(this);
        fnameET.setOnFocusChangeListener(this);
        mailET.setOnFocusChangeListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lvlArray, R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        lvlSpin.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignUp) {
            if(TextUtils.isEmpty(lnameET.getText()) || TextUtils.isEmpty(fnameET.getText())  || TextUtils.isEmpty(mailET.getText()))
            {
                if(TextUtils.isEmpty(lnameET.getText())) {
                    nomTV.setTextColor(Color.RED);
                }
                if(TextUtils.isEmpty(fnameET.getText())){
                    prenomTV.setTextColor(Color.RED);
                }
                if(TextUtils.isEmpty(mailET.getText())) {
                    mailTV.setTextColor(Color.RED);
                }
                Toast.makeText(SignActivity.this,"Les champs dotés d'un astérisque (*) sont obligatoires.",Toast.LENGTH_LONG).show();
            }
            else if(!this.isValidEmail(mailET.getText())) {
                mailTV.setTextColor(Color.RED);
                Toast.makeText(SignActivity.this,"Veuillez entrer une adresse email valide",Toast.LENGTH_LONG).show();
            }
            else
            {
                mailTV.setTextColor(Color.GRAY);
                nomTV.setTextColor(Color.GRAY);
                prenomTV.setTextColor(Color.GRAY);
                String lname = lnameET.getText().toString();
                String fname = fnameET.getText().toString();
                String mail = mailET.getText().toString();
                String lvl = lvlSpin.getSelectedItem().toString();
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                System.out.println(timeStamp);
                MyWebAsyncTask myWAT = new MyWebAsyncTask("http://192.168.43.61:3000/addPlayer","{\"lnameP\":\""+lname+"\",\"fnameP\":\""+fname+"\",\"mailP\":\""+mail+"\",\"lvlP\":\""+lvl+"\",\"hSignUPP\":\""+timeStamp+"\"}");
                myWAT.execute();
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v == lnameET) {
            if(!TextUtils.isEmpty(lnameET.getText())) {
                nomTV.setTextColor(Color.GRAY);
            }
        }
        if(v == fnameET) {
            if(!TextUtils.isEmpty(fnameET.getText())) {
                prenomTV.setTextColor(Color.GRAY);
            }
        }
        if(v == mailET) {
            if(!TextUtils.isEmpty(mailET.getText())) {
                mailTV.setTextColor(Color.GRAY);
            }
        }
    }

    public class MyWebAsyncTask extends AsyncTask {
        String resultat;
        String url;
        String param;
        Exception exception;

        public MyWebAsyncTask(String url,String param) {
            this.url = url;
            this.param = param;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                resultat = HttpClientUtil.sendPostOkHttpRequest(url,param);
            } catch (Exception e) {
                exception = e;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if(exception != null)
            {
                Toast.makeText(SignActivity.this,exception.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(SignActivity.this,"Inscription Validée",Toast.LENGTH_LONG).show();
                lnameET.getText().clear();
                fnameET.getText().clear();
                mailET.getText().clear();
            }
        }
    }

    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
