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
import android.widget.ArrayAdapter;
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
import com.example.manejointeligentedepragas.AdicionarCultura;
import com.example.manejointeligentedepragas.AdicionarPropriedade;
import com.example.manejointeligentedepragas.AplicaMetodoDeControle;
import com.example.manejointeligentedepragas.Auxiliar.Utils;
import com.example.manejointeligentedepragas.Cultura;
import com.example.manejointeligentedepragas.Pragas;
import com.example.manejointeligentedepragas.Propriedades;
import com.example.manejointeligentedepragas.R;
import com.example.manejointeligentedepragas.model.CulturaModel;
import com.example.manejointeligentedepragas.model.PropriedadeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CulturaCardAdapter extends RecyclerView.Adapter<CulturaCardAdapter.ViewHolder>{

    private static final String TAG = "CulturaCardAdapter";

    private ArrayList<CulturaModel> cards = new ArrayList<>();
    private Context culturaContext;
    private int Cod_Propriedade;

    String ultimaAplicacao;

    public CulturaCardAdapter(Context culturaContext, ArrayList<CulturaModel> cards, int Cod_Propriedade) {
        this.cards = cards;
        this.culturaContext = culturaContext;
        this.Cod_Propriedade = Cod_Propriedade;
    }

    @NonNull
    @Override
    // resposável por inflar a view

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cultura, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        //int dias = CalculaDiasPraContagem(cards.get(position).getCod_Cultura());

        holder.plantaCultura.setText(cards.get(position).getnomePlanta());
        holder.numTalhao.setText("Número de talhões: " + cards.get(position).getNumeroTalhoes());
        //holder.contagem.setText("Dias para contagem: " + dias);
        //Toast.makeText(culturaContext, ""+cards.get(position).isAplicado(), Toast.LENGTH_LONG).show();

        holder.parent_layout_cultura.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               exibirCaixaConfirmacao(cards.get(position), position);
               return true;
            }
        });

        holder.parent_layout_cultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:  clicked on: ");
                Intent i = new Intent(culturaContext, AcoesCultura.class);

                i.putExtra("Cod_Propriedade", Cod_Propriedade);

                // chama a intent nesse adapter
                // pega o contexr do construtor
                int codigo = cards.get(position).getCod_Cultura();
                i.putExtra("Cod_Cultura", codigo);

                String nomeCultura= cards.get(position).getnomePlanta();
                i.putExtra("NomeCultura", nomeCultura);

                i.putExtra("Aplicado", cards.get(position).isAplicado());

                culturaContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView plantaCultura;
        TextView numTalhao;
        TextView contagem;

        RelativeLayout parent_layout_cultura;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            plantaCultura = itemView.findViewById(R.id.tvCultura);
            numTalhao = itemView.findViewById(R.id.tvNumTalhoes);
            contagem = itemView.findViewById(R.id.tvContagem);

            parent_layout_cultura = itemView.findViewById(R.id.parent_layout_cultura);

        }
    }



    public void exibirCaixaConfirmacao(final CulturaModel c, final Integer position)
    {
        AlertDialog.Builder dlgBox = new AlertDialog.Builder(culturaContext);
        dlgBox.setTitle("Excluindo...");
        dlgBox.setMessage("Tem certeza que deseja excluir essa cultura?");
        dlgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExcluirCultura(c.getCod_Cultura(), position);
            }
        });

        dlgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // não faz nenhuma ação
            }
        });

        dlgBox.show();

    }

    public void ExcluirCultura(int CodC, final Integer position){

        String url = "http://mip2.000webhostapp.com/excluirCultura.php?Cod_Cultura="+ CodC  ;

        RequestQueue queue = Volley.newRequestQueue(culturaContext);
        queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Parsing json
                //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                try {
                    JSONObject obj = new JSONObject(response);
                    boolean confirmacao = obj.getBoolean("confirmacao");
                    if(confirmacao){
                        Intent k = new Intent(culturaContext, Cultura.class);
                        Toast.makeText(culturaContext, "Cultura excluída com sucesso!",Toast.LENGTH_LONG).show();
                        k.putExtra("Cod_Propriedade", cards.get(position).getFk_Cod_Propriedade());
                        culturaContext.startActivity(k);
                    }else{
                        Toast.makeText(culturaContext, "Cultura não excluída! Tente novamente",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(culturaContext, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(culturaContext,error.toString(), Toast.LENGTH_LONG).show();
            }
        }));
    }


    public int CalculaDiasPraContagem(int Cod_Cultura){
        int dias =0;
        Utils u = new Utils();
        if(!u.isConected(culturaContext))
        {
            Toast.makeText(culturaContext,"Habilite a conexão com a internet!", Toast.LENGTH_LONG).show();
        }else { // se tem acesso à internet
            String url = "http://mip2.000webhostapp.com/diasParaContagem.php?Cod_Cultura=" + Cod_Cultura;


            RequestQueue queue = Volley.newRequestQueue(culturaContext);
            queue.add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //Parsing json
                    //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                    try {
                        //Toast.makeText(Entrar.this,"AQUI", Toast.LENGTH_LONG).show();
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i< array.length(); i++){
                            JSONObject obj = array.getJSONObject(i);
                            ultimaAplicacao = obj.getString("Data");
                          try{
                              SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
                              Date dataAtual = new Date();
                              Date dataFormatada = formataData.parse(ultimaAplicacao);
                          }catch (ParseException e){
                              Toast.makeText(culturaContext, e.toString(), Toast.LENGTH_LONG).show();
                          }
                        }


                    } catch (JSONException e) {
                        Toast.makeText(culturaContext, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(culturaContext,error.toString(), Toast.LENGTH_LONG).show();
                }
            }));
        }
        return dias;
    }
}
