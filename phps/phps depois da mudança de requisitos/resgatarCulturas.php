<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $codPropriedade = $_GET['Cod_Propriedade'];

    // seleciona a propriedade
    $sql = "SELECT Cultura.Cod_Cultura, Cultura.TamanhoDaCultura,
                 Cultura.fk_Propriedade_Cod_Propriedade,
                  Cultura.fk_Planta_Cod_Planta, 
                  Planta.Nome, count(*) as count_talhao
                   from Cultura, Planta, Talhao
                    WHERE Cultura.fk_Planta_Cod_Planta = Planta.Cod_Planta and
                     Cultura.Cod_Cultura = Talhao.fk_Cultura_Cod_Cultura and
                      Cultura.fk_Propriedade_Cod_Propriedade = '$codPropriedade' 
                       GROUP BY Planta.Nome";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Cultura" => $ed->Cod_Cultura, "fk_Propriedade_Cod_Propriedade" => $ed->fk_Propriedade_Cod_Propriedade, "fk_Planta_Cod_Planta" => $ed->fk_Planta_Cod_Planta, "NomePlanta" => $ed->Nome, "count_talhao" => $ed->count_talhao, "TamanhoDaCultura" => $ed->TamanhoDaCultura);
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>