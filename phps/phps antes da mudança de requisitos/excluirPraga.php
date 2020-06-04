<?php
    include "conexao.php";

    // pega a variavel da url passada no aplicativo android
 
    $codP= $_GET['Cod_Praga'];
    $codC= $_GET['Cod_Cultura'];

    
        $sql2 = "DELETE FROM PresencaPraga WHERE fk_Praga_Cod_Praga=:CODP AND fk_Cultura_Cod_Cultura=:CODC";

        $sql = "SELECT Talhao.Cod_Talhao FROM Talhao WHERE Talhao.fk_Cultura_Cod_Cultura = '$codC'";

        $dados = $PDO->query($sql);
        $resultado = array();

        while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
        {
            $resultado [] = $ed->Cod_Talhao;
        }
        foreach($resultado as $r){
            $sqlDeletePlano = "DELETE FROM PlanoAmostragem 
                                WHERE PlanoAmostragem.fk_Praga_Cod_Praga = :CODP
                                AND PlanoAmostragem.fk_Talhao_Cod_Talhao = :CODT";
            $deletePlano = $PDO->prepare($sqlDeletePlano);
            $deletePlano->bindParam(':CODP',$codP);
            $deletePlano->bindParam(':CODT',$r);
            $deletePlano->execute();
        }

        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $stmt->bindParam(':CODP',$codP);
        $stmt->bindParam(':CODC',$codC);

        // só executa a query depois de receber os valores
        $stmt->execute();

        $sqlDeleteAplicacao = "DELETE FROM Aplicacao
                            WHERE Aplicacao.fk_Cultura_Cod_Cultura = :CODC 
                            AND Aplicacao.fk_Praga_Cod_Praga = :CODP";

        $stmt1 = $PDO->prepare($sqlDeleteAplicacao);
        //statment
        $stmt1->bindParam(':CODP',$codP);
        $stmt1->bindParam(':CODC',$codC);
        $stmt1->execute();

        $verifica = array("confirmacao" => true);

    echo json_encode($verifica);


?>