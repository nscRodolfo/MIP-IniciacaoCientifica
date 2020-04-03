package com.example.manejointeligentedepragas.VisualizaAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.AdicionarPropriedade;
import com.example.manejointeligentedepragas.AplicaMetodoDeControle;
import com.example.manejointeligentedepragas.Cultura;
import com.example.manejointeligentedepragas.InfoPraga;
import com.example.manejointeligentedepragas.PlanoDeAmostragem;
import com.example.manejointeligentedepragas.Pragas;
import com.example.manejointeligentedepragas.Propriedades;
import com.example.manejointeligentedepragas.R;
import com.example.manejointeligentedepragas.RealizarPlano;
import com.example.manejointeligentedepragas.model.PragaModel;
import com.example.manejointeligentedepragas.model.PropriedadeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisuPragasAdapter extends ArrayAdapter<PragaModel> {

    private ArrayList<PragaModel> cards = new ArrayList<>();
    private Context pragaContext;

    public VisuPragasAdapter( Context pragaContext,ArrayList<PragaModel> cards) {
        super(pragaContext, R.layout.construtor_visu_pragas,cards);
        this.cards = cards;
        this.pragaContext = pragaContext;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) pragaContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowVIew = inflater.inflate(R.layout.construtor_visu_pragas,parent, false);


        TextView nomePraga;
        TextView nomeCientificoPraga;

        nomePraga = rowVIew.findViewById(R.id.tvNomePraga);
        nomeCientificoPraga = rowVIew.findViewById(R.id.tvNomeCientPraga);

        nomePraga.setText(cards.get(position).getNome());
        nomeCientificoPraga.setText(cards.get(position).getNomeCientifico());

        return rowVIew;
    }


}
