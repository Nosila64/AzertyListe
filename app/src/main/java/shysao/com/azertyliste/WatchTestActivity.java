package shysao.com.azertyliste;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import java.util.ArrayList;

import shysao.com.azertyliste.WebService.HttpClientUtil;
import shysao.com.timepickerwithcenti2.view.MyTimePickerDialog;
import shysao.com.timepickerwithcenti2.view.TimePicker;

public class WatchTestActivity extends AppCompatActivity implements View.OnClickListener,AsyncResponse, MyTimePickerDialog.OnTimeSetListener, SwipeRefreshLayout.OnRefreshListener {
    TextView Status;
    int ValueItemMenu;
    TableLayout tableLayoutW;
    LinearLayout linearLayout, linearLayoutHome;
    TextView tv2,tv3,tv4,tv5;
    EditText ed1;
    String TestTimePicker;
    MyTimePickerDialog TP;
    Button btnValid,btnTime;
    SwipeRefreshLayout swipeRefreshLayout;
    static final int ID_CONSULT_PASSAGE = 1;
    static final int ID_CONSULT_PASSE = 2;
    String testResult;
    static final String url = "[adresseIP]:3000/api";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.WatchTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_test);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_ac);
        Status = findViewById(R.id.status);
        tableLayoutW = findViewById(R.id.tblLYW);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayout = findViewById(R.id.layout_main);
        linearLayoutHome = findViewById(R.id.layout_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,ID_CONSULT_PASSAGE,0,"En Attente");
        menu.add(0,ID_CONSULT_PASSE,0,"Joueurs Passés");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case ID_CONSULT_PASSAGE: {
                ValueItemMenu = ID_CONSULT_PASSAGE;
                MyWebAsyncTaskWAIT myWAT = new MyWebAsyncTaskWAIT("http://"+url+"/player");
                myWAT.delegate = this;
                myWAT.execute();
                break;
            }
            case ID_CONSULT_PASSE: {
                ValueItemMenu = ID_CONSULT_PASSE;
                MyWebAsyncTaskDONE myWAT = new MyWebAsyncTaskDONE("http://"+url+"/playerdone");
                myWAT.delegate = this;
                myWAT.execute();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinishWAIT(String resultat) {
        linearLayout.removeView(linearLayoutHome);
        tableLayoutW.removeAllViews();
        Status.setText("Joueurs en attente");
        Gson jsonResult = new Gson();
        ResultBean myResult = jsonResult.fromJson(resultat,ResultBean.class);
        ArrayList<PlayerBean> listePlayerBean = myResult.getResults();
        for(PlayerBean player: listePlayerBean) {
            TableRow tableRow1 = new TableRow(WatchTestActivity.this);
            tableRow1.setBackgroundColor(Color.WHITE);
            TableLayout.LayoutParams lp =
                    new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);

            lp.setMargins(10,10,10,10);
            tableRow1.setLayoutParams(lp);
            tv2 = new TextView(WatchTestActivity.this);
            tv2.setText(""+player.getIdplayer());
            tv2.setVisibility(View.INVISIBLE);
            tv3 = new TextView(WatchTestActivity.this); // création cellule
            tv3.setText(player.getFnameP()); // ajout du texte
            tv3.setGravity(Gravity.CENTER); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv3.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            // idem 2ème cellule
            tv4 = new TextView(WatchTestActivity.this);
            tv4.setText(player.getLnameP());
            tv4.setGravity(Gravity.CENTER);
            tv4.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            //idem 3ème cellule
            tv5= new TextView(WatchTestActivity.this);
            tv5.setGravity(Gravity.CENTER);
            tv5.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            //idem 4ème cellule
            btnTime = new Button(WatchTestActivity.this);
            btnTime.setText("Score");
            btnTime.setOnClickListener(WatchTestActivity.this);
            btnTime.setGravity(Gravity.CENTER);
            btnTime.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            //TODO:: AJOUTER UN BOUTON + ADD INTERACTION -> Valider passage -> ASYNC TASK POST
            btnValid = new Button(WatchTestActivity.this);
            btnValid.setText("✓");
            btnValid.setOnClickListener(WatchTestActivity.this);
            btnValid.setGravity(Gravity.CENTER);
            btnValid.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            // ajout des cellules à la ligne
            tableRow1.addView(tv2);
            tableRow1.addView(tv3);
            tableRow1.addView(tv4);
            tableRow1.addView(tv5);
            tableRow1.addView(btnTime);
            tableRow1.addView(btnValid);
            // ajout de la ligne au tableau
            tableLayoutW.addView(tableRow1);
        }
    }

    @Override
    public void processFinishDONE(String resultat) {
        linearLayout.removeView(linearLayoutHome);
        tableLayoutW.removeAllViews();
        Status.setText("Joueurs Passés");
        Gson jsonResult = new Gson();
        ResultBean myResult = jsonResult.fromJson(resultat, ResultBean.class);
        ArrayList<PlayerBean> listePlayerBean = myResult.getResults();
        for (PlayerBean player : listePlayerBean) {
            TableRow tableRow2 = new TableRow(WatchTestActivity.this);
            tableRow2.setBackgroundColor(Color.WHITE);
            TableLayout.LayoutParams lp =
                    new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);

            lp.setMargins(10,10,10,10);
            tableRow2.setLayoutParams(lp);
            tv3 = new TextView(WatchTestActivity.this); // création cellule
            tv3.setPadding(20,20,20,20);
            tv3.setText(player.getFnameP()); // ajout du texte
            tv3.setGravity(Gravity.CENTER); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv3.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            // idem 2ème cellule
            tv4 = new TextView(WatchTestActivity.this);
            tv4.setText(player.getLnameP());
            tv4.setGravity(Gravity.CENTER);
            tv4.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            //idem 3ème cellule
            tv5 = new TextView(this);
            tv5.setText(player.getScoreP());
            tv5.setGravity(Gravity.CENTER);
            tv5.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            //idem 4ème cellule
            tableRow2.addView(tv3);
            tableRow2.addView(tv4);
            tableRow2.addView(tv5);
            // ajout de la ligne au tableau
            tableLayoutW.addView(tableRow2);
        }
    }

    @Override
    public void onClick(View v) {
        Log.w("btn",v.toString());
        TableRow ligne = (TableRow)v.getParent();
        if(v == (Button)ligne.getChildAt(5)) {
            TextView text = (TextView)ligne.getChildAt(0);
            TextView tScore = (TextView) ligne.getChildAt(3);
            if(TextUtils.isEmpty(tScore.getText())) {
                Toast.makeText(this,"Merci de remplir le score avant de valider le passage",Toast.LENGTH_SHORT).show();
            }
            else {
                MyWebAsyncTaskWAITPOST myWATP = new MyWebAsyncTaskWAITPOST("http://"+url+"/player","{\"idPlayer\":\""+text.getText()+"\",\"scoreP\":\""+tScore.getText()+"\"}");
                myWATP.execute();
                tableLayoutW.removeView(ligne);
                Toast.makeText(this,"Passage Validé !",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == (Button)ligne.getChildAt(4)) {
            TextView tScore = (TextView) ligne.getChildAt(3);
            TP = new MyTimePickerDialog(this, this,0, 0,0, true, tScore);
            TP.show();
        }

    }


    @Override
    public void onTimeSet(shysao.com.timepickerwithcenti2.view.TimePicker view, int minute, int seconds, int centi, TextView tV) {
        String minutes = "";
        String secondes = "";
        String centis = "";
        if(minute < 10) {
            minutes = "0"+minute;
        }
        else {
            minutes = Integer.toString(minute);
        }
        if(seconds < 10) {
            secondes = "0"+seconds;
        }
        else {
            secondes = Integer.toString(seconds);
        }
        if(centi < 10) {
            centis = "0"+centi;
        }
        else {
            centis = Integer.toString(centi);
        }
        TestTimePicker = minutes+":"+secondes+":"+centis;
        tV.setText(TestTimePicker);
    }
    @Override
    public void onRefresh() {
        Log.w("CALL REFRESH","OK POTO CA REFRESH");
        doYourUpdate();
    }

    public void doYourUpdate() {
        Log.w("CALL REFRESH","OK POTO CA REFRESH");
        if(ValueItemMenu == ID_CONSULT_PASSAGE) {
            MyWebAsyncTaskWAIT myWAT = new MyWebAsyncTaskWAIT("http://"+url+"/player");
            myWAT.delegate = this;
            myWAT.execute();
            swipeRefreshLayout.setRefreshing(false);
        }
        else if(ValueItemMenu == ID_CONSULT_PASSE) {
            MyWebAsyncTaskDONE myWAT = new MyWebAsyncTaskDONE("http://"+url+"/playerdone");
            myWAT.delegate = this;
            myWAT.execute();
            swipeRefreshLayout.setRefreshing(false);
        }
        else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    private class MyWebAsyncTaskWAIT extends AsyncTask {
        String resultat;
        String url;
        Exception exception;
        String param;

        private MyWebAsyncTaskWAIT(String url) {
            this.url = url;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                resultat = HttpClientUtil.sendGetOkHttpRequest(url);
            } catch (Exception e) {
                exception = e;
                e.printStackTrace();
            }
            return null;
        }

        private AsyncResponse delegate = null;
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            delegate.processFinishWAIT(resultat);
        }
    }

    private class MyWebAsyncTaskDONE extends AsyncTask {
        String resultat;
        String url;
        Exception exception;
        String param;

        private  MyWebAsyncTaskDONE(String url) { this.url = url;}

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                resultat = HttpClientUtil.sendGetOkHttpRequest(url);
            } catch (Exception e) {
                exception = e;
                e.printStackTrace();
            }
            return null;
        }

        private AsyncResponse delegate = null;
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            delegate.processFinishDONE(resultat);
        }
    }
    private class MyWebAsyncTaskWAITPOST extends AsyncTask {
        String resultat;
        String url;
        Exception exception;
        String param;

        public MyWebAsyncTaskWAITPOST(String url,String param) {
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
        }
    }

    @Override
    protected void onDestroy() {
        //timer.cancel();
        super.onDestroy();
    }
}
