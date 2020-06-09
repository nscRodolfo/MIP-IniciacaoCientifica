package com.example.manejointeligentedepragas.RecyclerViewAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manejointeligentedepragas.AcoesCultura;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.Cultura;
import com.example.manejointeligentedepragas.R;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PlanoAmostragemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class PlanosRealizadosAdapter extends RecyclerView.Adapter<PlanosRealizadosAdapter.ViewHolder> {

    private ArrayList<PlanoAmostragemModel> planos = new ArrayList<>();
    private Context planosContext;
    private String NomeCultura;
    private String NomeTalhao;

    public PlanosRealizadosAdapter(Context planosContext,ArrayList<PlanoAmostragemModel> planos, String NomeCultura, String NomeTalhao){
        this.planos = planos;
        this.planosContext = planosContext;
        this.NomeCultura = NomeCultura;
        this.NomeTalhao = NomeTalhao;
    }

    @NonNull
    @Override
    // resposável por inflar a view

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.construtor_relatorio_planos_realizados, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvAutor.setText("Autor: "+planos.get(position).getAutor());
        holder.tvData.setText("Data do plano: "+planos.get(position).getDate());
        holder.tvPlantas.setText("Número de amostras: " + planos.get(position).getPlantasAmostradas());
        holder.tvPopPragas.setText("Plantas infestadas: " + planos.get(position).getPlantasInfestadas());
        holder.tvCultura.setText("Cultura: " + NomeCultura);
        holder.tvTalhao.setText("Talhão: "+NomeTalhao);
    }

    @Override
    public int getItemCount() {
        return planos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvData;
        TextView tvPlantas;
        TextView tvPopPragas;
        TextView tvAutor;
        TextView tvCultura;
        TextView tvTalhao;

        RelativeLayout parent_layout_planos_realizados;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvData = itemView.findViewById(R.id.tvDataPlanosRealizados);
            tvPopPragas = itemView.findViewById(R.id.tvPopPragasPlanosRealizados);
            tvPlantas = itemView.findViewById(R.id.tvNumPlantasPlanosRealizados);
            tvAutor = itemView.findViewById(R.id.tvAutorPlano);
            parent_layout_planos_realizados = itemView.findViewById(R.id.parent_layout_planos_realizados);
            tvCultura = itemView.findViewById(R.id.tvNomeCultura);
            tvTalhao = itemView.findViewById(R.id.tvTalhaoPlano);

        }
    }

}

