import java.io.*;
import java.net.*;

class Servidor{

    public static void main(String argv[]) throws Exception{
        String respostaUsuario;
        String respostaServidor;
        GetDocumentos documents = new GetDocumentos();
        ServerSocket socketRecepcao = null;
        try{
            socketRecepcao = new ServerSocket(6789);
            //Um loop que para que sempre fique aberto para novos Usuarios
            while(true){
                //Abrir conexao com o Cliente
                Socket socketConexao = socketRecepcao.accept();
                BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));
                DataOutputStream paraCliente = new DataOutputStream(socketConexao.getOutputStream());
                System.out.println("Cliente Conectado");

                //Esse loop é para quando se abre uma conexão com o cliente 
                while(true){
                // para resetar a variavel
                respostaUsuario = "";
                //Ele manda para o cliente a lista de documentos
                paraCliente.writeBytes(documents.DocumentsList() + "\n");

                //Aqui ele recebe a requesição do arquivo
                respostaUsuario = doCliente.readLine();
                System.out.println("Resposta recebida");

                //Aqui ele usa o metodo getDocument para achar o arquivo
                respostaServidor = documents.getDocument(respostaUsuario);

                //Aqui ele manda para o Usuario a resposta 
                paraCliente.writeBytes(respostaServidor + "\n");

                //Aqui ele recebe a resposta se deve continuar a mandar arquivos para o usuario
                respostaUsuario = doCliente.readLine();
                if(respostaUsuario.equals("N")) break;
                }

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}