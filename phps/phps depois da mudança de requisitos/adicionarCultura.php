<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
    $TamanhoDaCultura = $_GET['TamanhoDaCultura'];
    $fk_Propriedade_Cod_Propriedade = $_GET['fk_Propriedade_Cod_Propriedade'];
    $fk_Planta_Cod_Planta = $_GET['fk_Planta_Cod_Planta'];
    $qtdTalhao = $_GET['qtdTalhao'];

    
        $sql2 = "CALL addCulturaTalhaoPosRequisito(:TAMANHO, :PROPRIEDADE, :PLANTA, :QTD);";

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':TAMANHO',$TamanhoDaCultura);
        $stmt->bindParam(':PROPRIEDADE',$fk_Propriedade_Cod_Propriedade);
        $stmt->bindParam(':PLANTA',$fk_Planta_Cod_Planta);
        $stmt->bindParam(':QTD',$qtdTalhao);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>