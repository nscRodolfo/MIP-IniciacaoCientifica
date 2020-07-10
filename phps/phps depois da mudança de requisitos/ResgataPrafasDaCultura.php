<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $Cod_Cultura = $_GET["Cod_Cultura"];

    // seleciona a propriedade
    $sql = "SELECT Praga.Cod_Praga,Praga.Nome,Praga.Familia,Praga.Ordem,Praga.Descricao,Praga.NomeCientifico,Praga.Localizacao,Praga.AmbientePropicio,Praga.CicloVida,Praga.Injurias,
                   Praga.Observacoes,Praga.HorarioDeAtuacao,Praga.EstagioDeAtuacao,Praga.ControleCultural,
                   Atinge.Cod_Atinge,
                   Atinge.NivelDanoEconomico, 
                    Atinge.NivelDeControle, 
                    Atinge.NivelDeEquilibrio, 
                    Atinge.NumeroPlantasAmostradas,
                    Atinge.PontosPorTalhao, 
                    Atinge.PlantasPorPonto,
                    Atinge.NumAmostras,
                    Atinge.fk_Planta_Cod_Planta,
                    Atinge.fk_Praga_Cod_Praga
                    from Praga, Cultura, Atinge, Planta
                    WHERE Cultura.Cod_Cultura = '$Cod_Cultura'
                    AND Cultura.fk_Planta_Cod_Planta = Planta.Cod_Planta
                    AND Planta.Cod_Planta = Atinge.fk_Planta_Cod_Planta
                    AND Atinge.fk_Praga_Cod_Praga = Praga.Cod_Praga";
    
    $dados = $PDO->query($sql);
    $resultado = array();

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $resultado [] = array("Cod_Praga" => $ed->Cod_Praga,"Nome" => $ed->Nome, "Familia" => $ed->Familia, "Ordem" => $ed->Ordem,
        "Descricao" => $ed->Descricao, "NomeCientifico" => $ed->NomeCientifico, "Localizacao" => $ed->Localizacao,
       "AmbientePropicio" => $ed->AmbientePropicio, "CicloVida" => $ed->CicloVida,
        "Injurias" => $ed->Injurias, "Observacoes" => $ed->Observacoes, "HorarioDeAtuacao" => $ed->HorarioDeAtuacao,
         "EstagioDeAtuacao" => $ed->EstagioDeAtuacao, "ControleCultural" =>$ed->ControleCultural,
         "Cod_Atinge" => $ed->Cod_Atinge,
         "NivelDeControle" => $ed->NivelDeControle, 
         "NumeroPlantasAmostradas" => $ed->NumeroPlantasAmostradas,
         "PontosPorTalhao" => $ed->PontosPorTalhao,
         "PlantasPorPonto" => $ed->PlantasPorPonto,
         "NivelDanoEconomico" => $ed->NivelDanoEconomico,
         "NivelDeEquilibrio" => $ed->NivelDeEquilibrio,
         "NumAmostras" => $ed->NumAmostras,
         "fk_Planta_Cod_Planta" => $ed->fk_Planta_Cod_Planta,
         "fk_Praga_Cod_Praga" => $ed->fk_Praga_Cod_Praga
        );
        //$ed->Cod_Usuario entr no obj ed e pega atributo Cod_Usuario
    }
    echo json_encode($resultado);
   
?>