package com.example.manejointeligentedepragas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class SobreMIP extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.perfil:
                Intent i= new Intent(this, Perfil.class);
                startActivity(i);
                return true;
            case R.id.pragas:
                Intent k = new Intent(this, VisualizaPragas.class);
                startActivity(k);
                return true;
            case R.id.plantas:
                Intent j = new Intent(this, VisualizaPlantas.class);
                startActivity(j);
                return true;

            case R.id.metodo_de_controle:
                Intent l = new Intent(this, VisualizaMetodos.class);
                startActivity(l);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    TextView pergunta1;
    TextView resposta1;
    TextView pergunta2;
    TextView resposta2;
    TextView pergunta3;
    TextView resposta3;
    TextView pergunta4;
    TextView resposta4;
    TextView pergunta5;
    TextView resposta5;
    TextView pergunta6;
    TextView resposta6;
    TextView pergunta7;
    TextView resposta7;
    TextView pergunta8;
    TextView resposta8;
    TextView pergunta9;
    TextView resposta9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_mip);

       // setTitle("MIP² | Sobre o aplicativo");


        pergunta1 = findViewById(R.id.tvPergunta1);
        pergunta1.setText("O que é o MIP? E quais seus componentes?");
        resposta1 = findViewById(R.id.tvResp1);
        resposta1.setText("O MIP, como é conhecido o Manejo Integrado de Pragas, trata-se de uma filosofia de controle de pragas que procura preservar e incrementar os fatores de mortalidade natural, através do uso integrado de todas as técnicas de combate possíveis, selecionadas com base nos parâmetros econômicos, ecológicos e sociológicos. O MIP possui 4 componentes principais sendo eles: avaliação do agroecossistema/Diagnose que trata da identificação correta das pragas da cultura (pragas-chave) e seus inimigos naturais; amostragem das populações de pragas para determinar se esta se encontra no nível de dano econômico (onde necessita fazer o uso de um método de controle); tomada de decisão de acordo com a amostragem para verificar se há ou não a necessidade de utilizar métodos de controle e a seleção dos métodos de controle de pragas de acordo com parâmetros técnicos (eficácia), econômicos (maior lucro), ecotoxicológicos (preservação do ambiente e da saúde humana) e sociológicos (adaptáveis ao usuário).");


        pergunta2 = findViewById(R.id.tvPergunta2);
        pergunta2.setText("O que é o MIP²? ");
        resposta2 = findViewById(R.id.tvResp2);
        resposta2.setText("O MIP² (Manejo Inteligente de Pragas) trata-se de uma ferramenta computacional agroecológica baseada no MIP, que tem a função de facilitar a adoção do Manejo Integrado de Pragas nas propriedades com cultivo orgânico/agroecológico.");


        pergunta3 = findViewById(R.id.tvPergunta3);
        pergunta3.setText("Como utilizar o aplicativo?");
        resposta3 = findViewById(R.id.tvResp3);
        resposta3.setText("Para utilizar o aplicativo é necessário fazer um cadastro preenchendo as informações requisitadas, selecionando qual o tipo de usuário. O usuário tipo PROPRIETÁRIO tem a acesso total as funções oferecidas pelo MIP² referente a propriedade como: adicionar/editar/excluir propriedade, adicionar/excluir culturas e adicionar/excluir FUNCIONARIO.\n" +
                "O FUNCIONARIO tem função limitada no aplicativo, não podendo gerir a propriedade ou adicionar culturas, no entanto ainda tem acesso as informações para que seja possível executar o plano de amostragem, como ver informações das culturas, pragas e métodos de controle, adicionar praga, selecionar o método de controle adotado e gerar relatórios. ");


        pergunta4 = findViewById(R.id.tvPergunta4);
        pergunta4.setText("Como gerir minhas propriedades?");
        resposta4 = findViewById(R.id.tvResp4);
        resposta4.setText("Após entrar no sistema, você (proprietário) será direcionado para a tela de propriedades, onde é possível adicionar propriedades e ver quais propriedades estão cadastradas no sistema. Ao selecionar uma propriedade você será levado para uma tela onde é possível ver quantos funcionários estão vinculados à sua propriedade e quais culturas estão presentes nessa propriedade. Para vincular funcionários, clique no cartão FUNCIONÁRIOS > ADICIONAR FUNCIONÁRIOS e peça à pessoa que insira suas credenciais de entrada. Ao vincular um funcionário à sua propriedade, ele poderá realizar ações nas culturas existentes na propriedade.");


        pergunta5 = findViewById(R.id.tvPergunta5);
        pergunta5.setText("Como faço para adicionar uma cultura a propriedade?");
        resposta5 = findViewById(R.id.tvResp5);
        resposta5.setText("Após adicionar a propriedade você (proprietário) poderá escolher dentre as culturas cadastradas no aplicativo, devendo preencher o tamanho da cultura em hectares para que o aplicativo defina quantos talhões serão necessários para realizar o plano de amostragem.");


        pergunta6 = findViewById(R.id.tvPergunta6);
        pergunta6.setText("Como identificar e adicionar uma praga?");
        resposta6 = findViewById(R.id.tvResp6);
        resposta6.setText("Para identificar uma praga em uma cultura o usuário deve selecionar -dentro da propriedade- qual cultura se trata e selecionar a opção PRAGAS ATUANTES> ADICIONAR PRAGAS> na opção selecionar praga o usuário terá uma lista de pragas que acometem a cultura, selecione uma delas e clique em INFORMAÇÕES DA PRAGA (onde o usuário poderá ver suas informações, fotos da praga e dos danos causados a cultura), após selecionar a praga que pretende fazer a amostragem clique em SALVAR. As pragas terão diferentes cores, indicando diferentes situações que podem ser descritas ao clicar no botão de informação (i). ");


        pergunta7 = findViewById(R.id.tvPergunta7);
        pergunta7.setText("Como realizar o plano de amostragem?");
        resposta7 = findViewById(R.id.tvResp7);
        resposta7.setText("Ao selecionar a cultura onde se localiza a praga a ser amostrada clique em REALIZAR PLANO DE AMOSTRAGEM e selecione a praga. Siga as informações descritas na tela inspecionando o número de plantas exigido, caso tenha dúvida sobre a localização da praga na planta (AMOSTRA), clique no botão (i). Você deverá inspecionar o número de plantas descrito para cada talhão, caso tenha menos plantas que o necessário, realize o plano de amostragem apenas com o número de plantas presentes em sua cultura. Para realizar a contagem clique no botão ENCONTRADO ou NÃO ENCONTRADO de acordo com a AMOSTRA para cada planta até completar o plano, se cometer um erro, é possível clicar em CORRIGIR, corrigindo a última planta amostrada, podendo também segurar o botão para recomeçar a contagem, caso tenha cometido erros consecutivos. Ao FINALIZAR a contagem você será levado para uma tela que vai informar se há necessidade de aplicar algum método de controle. Havendo necessidade, você poderá selecionar o método de controle, devendo aplicá-lo assim que possível. \n" +
                "ATENÇÃO: Ao selecionar APLICAR o sistema entende que você realizou a aplicação do método, sendo necessário aguardar o tempo exigido, que ficará descrito no cartão da sua cultura. Caso selecione APLICAR DEPOIS, a sua praga ficará vermelha na tela de pragas atuantes, o que indica ser necessária uma aplicação para o controle desta.");


        pergunta8 = findViewById(R.id.tvPergunta8);
        pergunta8.setText("Como realizar a aplicação do método de controle?");
        resposta8 = findViewById(R.id.tvResp8);
        resposta8.setText("É indicado que, ao detectar a necessidade de uma aplicação, o usuário realize-a o quanto antes, aplicando sobre TODA a cultura e NÃO apenas sobre as plantas infestadas. Para mais informações leia a descrição do método a ser utilizado.");


        pergunta9 = findViewById(R.id.tvPergunta9);
        pergunta9.setText("Como ver o resultado da aplicação do MIP²?");
        resposta9 = findViewById(R.id.tvResp9);
        resposta9.setText("Em cada cultura é possível clicar no botão GERAR RELATÓRIOS, onde são descritos os relatórios de maior relevância para a gestão de sua propriedade.");
    }
}
