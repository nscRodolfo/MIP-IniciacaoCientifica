<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Cultura = $_GET['Cod_Cultura'];
    $Cod_Praga = $_GET['Cod_Praga'];

    // seleciona a propriedade
    $sql = "SELECT Atinge.NivelDanoEconomico, 
    Atinge.NivelDeControle, 
    Atinge.NivelDeEquilibrio, 
    Atinge.NumeroPlantasAmostradas,
    Atinge.PontosPorTalhao, 
    Atinge.PlantasPorPonto,
    Atinge.NumAmostras FROM Atinge, Cultura
    WHERE Cultura.Cod_Cultura = '$Cod_Cultura'
    and Cultura.fk_Planta_Cod_Planta = Atinge.fk_Planta_Cod_Planta
    and Atinge.fk_Praga_Cod_Praga = '$Cod_Praga'";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = 
        array("NivelDeControle" => $ed->NivelDeControle, 
        "NumeroPlantasAmostradas" => $ed->NumeroPlantasAmostradas,
        "PontosPorTalhao" => $ed->PontosPorTalhao,
        "PlantasPorPonto" => $ed->PlantasPorPonto,
        "NivelDanoEconomico" => $ed->NivelDanoEconomico,
        "NivelDeEquilibrio" => $ed->NivelDeEquilibrio,
        "NumAmostras" => $ed->NumAmostras);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>