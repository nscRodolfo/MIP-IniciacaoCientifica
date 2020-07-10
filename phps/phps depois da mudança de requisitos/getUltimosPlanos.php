<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Talhao = $_GET['Cod_Talhao'];


    $sql = "SELECT MAX(PlanoAmostragem.Data) as Data, PlanoAmostragem.fk_Praga_Cod_Praga 
    from PlanoAmostragem 
    WHERE PlanoAmostragem.fk_Talhao_Cod_Talhao ='$Cod_Talhao' 
    GROUP BY PlanoAmostragem.fk_Praga_Cod_Praga;";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Data" => $ed->Data,"fk_Praga_Cod_Praga" => $ed->fk_Praga_Cod_Praga );
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>