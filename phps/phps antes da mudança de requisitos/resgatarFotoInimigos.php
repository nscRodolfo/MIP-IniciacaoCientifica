<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codI = $_GET["Cod_Inimigo"];

    // seleciona a propriedade
    $sql = "SELECT FotoInimigo.FotoInimigo 
                   from FotoInimigo
                    WHERE FotoInimigo.fk_InimigoNatural_Cod_Inimigo = '$codI'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("FotoInimigo" => $ed->FotoInimigo);
    }
    echo json_encode($resultado);
   
?>