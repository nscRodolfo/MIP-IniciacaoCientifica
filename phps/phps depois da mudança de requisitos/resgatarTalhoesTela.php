<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codCultura = $_GET['Cod_Cultura'];

    // seleciona a propriedade

    $sql = "SELECT Talhao.Cod_Talhao, Talhao.Aplicado,
             Talhao.fk_Cultura_Cod_Cultura, 
              Talhao.Nome, Talhao.fk_Planta_Cod_Planta
               from Talhao
                WHERE Talhao.fk_Cultura_Cod_Cultura = '$codCultura'
                   ";
                   //GROUP BY Talhao.Nome
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Talhao" => $ed->Cod_Talhao, "fk_Cultura_Cod_Cultura" => $ed->fk_Cultura_Cod_Cultura, "fk_Planta_Cod_Planta" => $ed->fk_Planta_Cod_Planta, "Nome" => $ed->Nome, "Aplicado" => $ed ->Aplicado);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>