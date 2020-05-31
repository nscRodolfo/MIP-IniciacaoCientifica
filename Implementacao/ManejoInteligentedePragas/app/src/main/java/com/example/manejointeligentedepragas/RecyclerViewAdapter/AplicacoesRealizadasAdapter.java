package com.example.manejointeligentedepragas.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.manejointeligentedepragas.R;
import com.example.manejointeligentedepragas.model.AplicacaoModel;
import com.example.manejointeligentedepragas.model.PlanoAmostragemModel;

import java.util.ArrayList;
public class AplicacoesRealizadasAdapter extends RecyclerView.Adapter<AplicacoesRealizadasAdapter.ViewHolder>{

    private ArrayList<AplicacaoModel> aplicacoes = new ArrayList<>();
    private Context aplicacoesContext;
    private int codCultura;
    private int codPraga;

    public AplicacoesRealizadasAdapter(Context aplicacoesContext,ArrayList<AplicacaoModel> aplicacoes, int codCultura, int codPraga){
        this.aplicacoes = aplicacoes;
        this.aplicacoesContext = aplicacoesContext;
        this.codCultura = codCultura;
        this.codPraga = codPraga;
    }

    @NonNull
    @Override
    // resposável por inflar a view

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.construtor_relatorio_aplicacoes_realizadas, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvAutorAplicacao.setText("Autor: "+aplicacoes.get(position).getAutor());
        holder.tvMetodoUtiizado.setText("Método aplicado: "+aplicacoes.get(position).getMetodoAplicado());
        holder.tvDataAplicacao.setText("Data da aplicação: " + aplicacoes.get(position).getData());
        holder.tvNumPlantasAplicacoesRealizadas.setText("Plantas infestadas: " + aplicacoes.get(position).getPopPragas());
        holder.tvPopPragasAplicacoesRealizadas.setText("Número de amostras: " + aplicacoes.get(position).getNumPlantas());
    }

    @Override
    public int getItemCount() {
        return aplicacoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAutorAplicacao;
        TextView tvMetodoUtiizado;
        TextView tvDataAplicacao;
        TextView tvNumPlantasAplicacoesRealizadas;
        TextView tvPopPragasAplicacoesRealizadas;

        RelativeLayout parent_layout_aplicacoes_realizadas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAutorAplicacao = itemView.findViewById(R.id.tvAutorAplicacao);
            tvMetodoUtiizado = itemView.findViewById(R.id.tvMetodoUtiizado);
            tvDataAplicacao = itemView.findViewById(R.id.tvDataAplicacao);
            tvNumPlantasAplicacoesRealizadas = itemView.findViewById(R.id.tvNumPlantasAplicacoesRealizadas);
            tvPopPragasAplicacoesRealizadas = itemView.findViewById(R.id.tvPopPragasAplicacoesRealizadas);
            parent_layout_aplicacoes_realizadas = itemView.findViewById(R.id.parent_layout_aplicacoes_realizadas);

        }
    }

}
