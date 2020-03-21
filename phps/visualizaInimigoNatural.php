<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codPraga = $_GET["Cod_Praga"];
    // seleciona a propriedade
    $sql = "SELECT InimigoNatural.Nome, InimigoNatural.Cod_Inimigo from InimigoNatural
            JOIN Combate WHERE Combate.fk_Praga_Cod_Praga = '$codPraga'
            and Combate.fk_InimigoNatural_Cod_Inimigo = InimigoNatural.Cod_Inimigo
            ORDER BY InimigoNatural.Nome ASC";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("NomeInimigo" => $ed->Nome, "Cod_Inimigo" => $ed->Cod_Inimigo);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>