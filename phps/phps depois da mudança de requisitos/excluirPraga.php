<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
 
    $codP= $_GET['Cod_Praga'];
    $codT= $_GET['Cod_Talhao'];

    
        $sql = "DELETE FROM PresencaPraga WHERE fk_Praga_Cod_Praga=:CODP AND fk_Talhao_Cod_Talhao=:CODT";

        //$sql = "SELECT Talhao.Cod_Talhao FROM Talhao WHERE Talhao.fk_Cultura_Cod_Cultura = '$codC'";

        
            $sqlDeletePlano = "DELETE FROM PlanoAmostragem 
                                WHERE PlanoAmostragem.fk_Praga_Cod_Praga = :CODP
                                AND PlanoAmostragem.fk_Talhao_Cod_Talhao = :CODT";

            $deletePlano = $PDO->prepare($sqlDeletePlano);
            $deletePlano->bindParam(':CODP',$codP);
            $deletePlano->bindParam(':CODT',$codT);
            $deletePlano->execute();
        

        // prepara o statment
        $stmt = $PDO->prepare($sql);
        //statment
        $stmt->bindParam(':CODP',$codP);
        $stmt->bindParam(':CODT',$codT);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $sqlDeleteAplicacao = "DELETE FROM Aplicacao
                            WHERE Aplicacao.fk_Talhao_Cod_Talhao = :CODT
                            AND Aplicacao.fk_Praga_Cod_Praga = :CODP";

        $stmt1 = $PDO->prepare($sqlDeleteAplicacao);
        //statment
        $stmt1->bindParam(':CODP',$codP);
        $stmt1->bindParam(':CODT',$codT);
        $stmt1->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>