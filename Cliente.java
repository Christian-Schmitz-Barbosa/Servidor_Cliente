import java.io.*;
import java.net.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;


public class Cliente{
    public static void main(String argv[])throws Exception{
        Scanner input = new Scanner(System.in);
        String respostaServidor;
        String respostaUsuario;
        BufferedReader doUsuario = new BufferedReader(new InputStreamReader(System.in));
        try{
            // Abre conexão com o servidor
            Socket socketCliente = new Socket("localhost", 6789);
            DataOutputStream paraServidor =new DataOutputStream(socketCliente.getOutputStream());
            BufferedReader doServidor =new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            //Esse laço de repetição vai rodar ate que o Usuario 
            //decida que não quer mais baixar documentos 
            while(true){
             // Recebe a lista de arquivos disponiveis.
                respostaServidor = doServidor.readLine();
                String[] docsList = respostaServidor.split(",");
                for(int i = 0; i < docsList.length; i++){
                    System.out.println(docsList[i]);
                }

                //Envia o nome do arquivo selecionado
                System.out.println("Digite o nome do arquivo: ");
                respostaUsuario = doUsuario.readLine();
                paraServidor.writeBytes(respostaUsuario + "\n");

                //Recebe o arquivo
                respostaServidor = doServidor.readLine();

                //Executa o metodo salvar(linha 51)
                salvar(respostaServidor,respostaUsuario);

                //Aqui vem a decisao se o Usuario quer ou não continuar
                System.out.println("Voce quer continuar a receber arquivos? (S/N)");
                String choise = input.nextLine().toUpperCase();
                paraServidor.writeBytes(choise + "\n");
                if(choise.equals("N")) break;
            }

        }catch(IOException e ){
            e.printStackTrace();
        }
    }

    // A função dessa metodo é de salvar o arquivo em um diretorio
    //os parametros: arquivo = conteudo dele; nome = nome do arquivo
    private static void salvar(String arquivo, String nome){
        try{
            // com a variavel time sera possivel baixar o mesmo arquivo multiplas vezes
			long time = System.currentTimeMillis();

            // o fileInputStream lê informações (em bytes) do aquivo
            FileInputStream fileInputStream = new FileInputStream(arquivo);

            //O fileOutputStream diz qual diretorio sera armazenado o arquivo e também nomeia o arquivo
            FileOutputStream fileOutputStream = new FileOutputStream("./DocsEnviados/" + time + "_" + nome);

            // aqui eles Recebem os valores de fileInputStream e de fileOutputStream para poder usar o metodo transferTo mais tarde
            FileChannel fin = fileInputStream.getChannel();
			FileChannel fout = fileOutputStream.getChannel();
				
			//o size recebe a informação do tamanho da variavel fin
			long size = fin.size();
			
            // Aqui ele transfere o arquivo
			fin.transferTo(0, size, fout);

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    


}