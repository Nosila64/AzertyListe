package shysao.com.azertyliste;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import shysao.com.azertyliste.WebService.HttpClientUtil;

public class WatchActivity extends AppCompatActivity {
    TextView Status;
    static final int ID_CONSULT_PASSAGE = 1;
    static final int ID_CONSULT_PASSE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.WatchTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_ac);
        Status = findViewById(R.id.status);
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
                MyWebAsyncTask myWAT = new MyWebAsyncTask("http://192.168.43.122:3000/Player");
                myWAT.execute();
                break;
            }
            case ID_CONSULT_PASSE: {
               /* tableLayoutP.removeAllViews();
                tableLayoutW.removeAllViews();
                tableLayoutW.setVisibility(View.INVISIBLE);
                tableLayoutP.setVisibility(View.VISIBLE);
                Status.setText("Joueurs passés");
                //TODO: Faire le service web pour récupérer les joueurs passés

                for(int j=0;j < 5; j++) {
                    TableRow tableRow1 = new TableRow(WatchActivity.this);
                    TextView tv3 = new TextView(WatchActivity.this); // création cellule
                    tv3.setText("POULET"); // ajout du texte
                    tv3.setGravity(Gravity.CENTER); // centrage dans la cellule
                    // adaptation de la largeur de colonne à l'écran :
                    tv3.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

                    // idem 2ème cellule
                    TextView tv4 = new TextView(WatchActivity.this);
                    tv4.setText("Frite");
                    tv4.setGravity(Gravity.CENTER);
                    tv4.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

                    // ajout des cellules à la ligne
                    tableRow1.addView(tv3);
                    tableRow1.addView(tv4);

                    // ajout de la ligne au tableau
                    tableLayoutP.addView(tableRow1);
                }*/
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //TODO: Gérer le listener pour Update le status de passage
    public class MyWebAsyncTask extends AsyncTask {
        String resultat;
        String url;
        Exception exception;

        public MyWebAsyncTask(String url) {
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

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            TableLayout tableLayoutW = findViewById(R.id.tblLYW);
            TableLayout tableLayoutP = findViewById(R.id.tblLYP);

            tableLayoutW.setVisibility(View.VISIBLE);
            tableLayoutP.setVisibility(View.INVISIBLE);
            tableLayoutW.removeAllViews();
            tableLayoutP.removeAllViews();
            Status.setText("Joueurs en attente");
            //TODO: faire le service web pour récupérer les joueurs en attente + Ajouter un bouton et un listener

            Gson jsonResult = new Gson();
            ResultBean myResult = jsonResult.fromJson(resultat,ResultBean.class);
            ArrayList<PlayerBean> listePlayerBean = myResult.getResults();
            for(PlayerBean player: listePlayerBean) {
                    TableRow tableRow1 = new TableRow(WatchActivity.this);
                    TextView tv3 = new TextView(WatchActivity.this); // création cellule
                    tv3.setText(player.getFnameP()); // ajout du texte
                    tv3.setGravity(Gravity.CENTER); // centrage dans la cellule
                    // adaptation de la largeur de colonne à l'écran :
                    tv3.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

                    // idem 2ème cellule
                    TextView tv4 = new TextView(WatchActivity.this);
                    tv4.setText(player.getLnameP());
                    tv4.setGravity(Gravity.CENTER);
                    tv4.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

                    //TODO:: AJOUTER UN BOUTON + ADD INTERACTION -> Valider passage -> ASYNC TASK POST
                    TextView tv5 = new TextView(WatchActivity.this);
                    tv5.setText("TODO:: ADD BOUTON");
                    tv5.setGravity(Gravity.CENTER);
                    tv5.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
                    // ajout des cellules à la ligne
                    tableRow1.addView(tv3);
                    tableRow1.addView(tv4);

                    // ajout de la ligne au tableau
                    tableLayoutW.addView(tableRow1);
            }
            if(exception != null)
            {
                Toast.makeText(WatchActivity.this,exception.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(WatchActivity.this,"Affichage en cours",Toast.LENGTH_LONG).show();
            }
        }
    }
}
