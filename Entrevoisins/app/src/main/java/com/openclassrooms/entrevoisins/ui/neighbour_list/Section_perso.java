package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

public class Section_perso extends AppCompatActivity {

    private TextView Name_Big;
    private TextView Name;
    private TextView Location;
    private FloatingActionButton retour;
    private FloatingActionButton favoris;
    private FloatingActionButton favoris_active;
    private NeighbourApiService mApiService;
    private List<Neighbour> liste_favoris;
    private static final String TAG = "Section_perso";
    private Neighbour fav;
    private ImageView avatar;
    Neighbour neighbour;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.section_perso_neighbour);
        mApiService = DI.getNeighbourApiService();
        Name_Big=(TextView) findViewById(R.id.editText_Name2);
        Name=(TextView) findViewById(R.id.editText_Name);
        Location=(TextView) findViewById(R.id.editText_Location);
        retour=(FloatingActionButton) findViewById(R.id.Retour_arriere_btn);
        avatar=(ImageView) findViewById(R.id.imageView);
        liste_favoris=mApiService.getFav();
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        favoris=(FloatingActionButton) findViewById(R.id.favoris_btn);
        favoris_active=(FloatingActionButton) findViewById(R.id.favoris_active);
        Bundle extras=getIntent().getExtras();
        if (extras!=null) {
            String MessageNameB= extras.getString("Big_Name");
            String MessageName= extras.getString("Name");
            String MessageLocation= extras.getString("Location");
            int position= extras.getInt("posi");
            String UrlA= extras.getString("url");
            Boolean alterbool= extras.getBoolean("alter");
            Glide.with(this)
                    .load(UrlA)
                    .into(avatar);
            Name_Big.setText(MessageNameB);
            Location.setText(MessageLocation);
            Name.setText(MessageNameB);
            if(alterbool==true){
                favoris_active.setVisibility(View.VISIBLE);
            }
            Log.d(TAG, " onclick position: " + position);
            Log.d(TAG, " onclick liste favoris de base: "+liste_favoris);

            favoris.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View v) {
                    Toast.makeText(Section_perso.this,"Favori: ajouté !", Toast.LENGTH_LONG).show();
                    favoris.setVisibility(View.GONE);
                    favoris_active.setVisibility(View.VISIBLE);
                    mApiService = DI.getNeighbourApiService();
                    fav= mApiService.getNeighbours().get(position);
                    liste_favoris=mApiService.getFav();
                    mApiService.addFav(fav);
                    Log.d(TAG, "onclick Fav: "+fav);
                    Log.i(TAG, " onclick liste de Favoris ajout: " + liste_favoris);
                }
            });
            favoris_active.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View v) {
                    if (ListNeighbourActivity.position==true) {
                        fav = mApiService.getNeighbours().get(position);
                    } else {
                        fav = mApiService.getFav().get(position);
                    }
                        Log.d(TAG, " onclick position: " + position);
                        favoris_active.setVisibility(View.GONE);
                        favoris.setVisibility(View.VISIBLE);
                        mApiService.removeFav(fav);

                        Toast.makeText(Section_perso.this, "Favori: supprimé !", Toast.LENGTH_LONG).show();
                        Log.d(TAG, " onclick Fav: " + fav);
                        Log.i(TAG, " onClick: liste favoris suppression" + liste_favoris);
                    }
            });
        }

    }
}
