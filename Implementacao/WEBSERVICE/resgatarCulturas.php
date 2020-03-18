<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codPropriedade = $_GET['Cod_Propriedade'];

    // seleciona a propriedade
    $sql = "SELECT Cultura.Cod_Cultura,
                     Cultura.fk_Propriedade_Cod_Propriedade,
                      Planta.nome
                         from Cultura, Planta
                          WHERE Cultura.fk_Planta_Cod_Planta = Planta.Cod_Planta 
                           and Cultura.fk_Propriedade_Cod_Propriedade = '$codPropriedade'";
 
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Cultura" => $ed->Cod_Cultura, "fk_Propriedade_Cod_Propriedade" => $ed->fk_Propriedade_Cod_Propriedade, "NomePlanta" => $ed->Nome);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>