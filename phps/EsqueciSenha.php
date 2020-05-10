<?php
    include "conexao.php";


    // pega a variavel da url passada no aplicativo android
    $email = $_GET['Email'];

    $newPass = generatePassword();

    $sql = "SELECT Cod_Usuario FROM Usuario WHERE email = '$email'";
    $dados = $PDO->query($sql);

    while ($ed = $dados->fetch(PDO::FETCH_OBJ)) //passa os dados como objetos pro $ed
    {
        $codUsu = $ed->Cod_Usuario;
    }

    if(!empty($codUsu))
    {
        $verifica = array("confirmacao" => true);

        $sql2 = " UPDATE Usuario SET Senha = :SENHA WHERE Usuario.Cod_Usuario = $codUsu";
        
        // prepara o statment
        $stmt = $PDO->prepare($sql2);
        //statment
        $senha = md5($newPass);
        $stmt->bindParam(':SENHA',$senha);
        // só executa a query depois de receber os valores
        $stmt->execute();

        
        echo sendEmailRecoveryPass($email, $newPass);

    }


function sendEmailRecoveryPass($email, $senha)
{
    
    $headers = 'From: MIP2@recuperacao.com' . "\r\n" .
    'Reply-To: nsc.rodolfo@gmail.com' . "\r\n" .
    'X-Mailer: PHP/' . phpversion();

    //titulo da mensagem
    $title = "Recuperação de Senha do aplicativo MIP²";

    //mensagem que vai ser enviada, gerando o password random que será guardado no banco de dados
    $message = "Foi solicitada uma nova senha para este e-mail no sistema MIP².\nSua senha foi alterada.\nA nova senha é: " . $senha . "\nFaça o login utilizando ela e altere!
                \n\n Não responda este e-mail";

    //aqui que tá a mágica, funcao mail (propria do php) que envia email do servidor, tem que ver se o 000webhost permite usar ela no modo free
    if (mail($email, $title, $message, $headers)) {
        //aqui vc salva no banco de dados a nova senha random no BD, não sei como faz com o volley, ou se quiser pode retornar ela no JSON também
        //só tá retornando em json falando que deu certo, verifica isso no android pra aparecer uma mensagem falando que email foi enviado
        $verifica = array("confirmacao" => true);
        echo json_encode($verifica);

    } else {

        //aqui é quando deu merda, ou veio email vazio ou o servidor não suporta essa função, talvez tenha que pagar não sei mas reza pra dar kkkk
        $verifica = array("confirmacao" => false);
        echo json_encode($verifica);
    }
}

//funcao que peguei na net só pra gerar uma senha segura
function generatePassword($qtyCaraceters = 8)
{
    //Letras minúsculas embaralhadas
    $smallLetters = str_shuffle('abcdefghijklmnopqrstuvwxyz');

    //Letras maiúsculas embaralhadas
    //$capitalLetters = str_shuffle('ABCDEFGHIJKLMNOPQRSTUVWXYZ');

    //Números aleatórios
    $numbers = (((date('Ymd') / 12) * 24) + mt_rand(800, 9999));
    $numbers .= 1234567890;
    //Junta tudo
    $characters =  $smallLetters . $numbers;
    //Embaralha e pega apenas a quantidade de caracteres informada no parâmetro
    $password = substr(str_shuffle($characters), 0, $qtyCaraceters);
    //Retorna a senha
    return $password;
}
   
?>