<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Planta = $_GET["Cod_Planta"];

    // seleciona a propriedade
    $sql = "SELECT Praga.Nome, Praga.Cod_Praga 
    from Praga, Planta, Atinge
    WHERE Planta.Cod_Planta = '$Cod_Planta'
    AND Atinge.fk_Planta_Cod_Planta = Planta.Cod_Planta
    AND Atinge.fk_Praga_Cod_Praga = Praga.Cod_Praga
    ORDER BY Praga.Nome ASC";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome, "Cod_Praga" => $ed->Cod_Praga);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>