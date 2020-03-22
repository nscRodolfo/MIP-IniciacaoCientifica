<?php
    include "conexao.php";

    $cod_Cultura = $_GET["cod_Cultura"];

    $sql = "SELECT Praga.Nome, Praga.Cod_Praga from Cultura, Praga, Atinge
    WHERE Cultura.Cod_Cultura = '$cod_Cultura'
    and Cultura.fk_Planta_Cod_Planta = Atinge.fk_Planta_Cod_Planta 
    and Atinge.fk_Praga_Cod_Praga = Praga.Cod_Praga
    ORDER BY Praga.Nome ASC";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Nome" => $ed->Nome, "Cod_Praga" => $ed->Cod_Praga);
       
    }
    echo json_encode($resultado);
   
?>