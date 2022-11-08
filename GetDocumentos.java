import java.io.File;

class GetDocumentos{
    // a variavel pasta vai buscar todos os arquivos que estão no diretorio "./Docs/"
    private File pasta = new File("./Docs/");

    // Vai gerar um array que listara todos os arquivos dentro da variavel pasta
    private File[] arquivos = pasta.listFiles();

    // Esse método transforma o Array de arquivos em uma String chamada listaDeArquivos, 
    //tornando mais facil de  levar para o usuario as informações, ela também exclui as palavras 
    //./Docs/ para deixar menos complicado e o retorno é a String listaDeArquivos
    public String DocumentsList(){
        String listaDeArquivos = "";
        
        for(int i = 0; i < arquivos.length; i++){
            String arquivo = arquivos[i] + ",";
            listaDeArquivos += arquivo.substring(7);
        }
        return listaDeArquivos;
    }
    //Nesse metodo tem como parametro a requisicao do Usuario. a funcao desse metodo é passar por 
    //todos os arquivos comparando com a requisicao, se encontrar, retorna o caminho do arquivo, 
    // se não encontrar retorna a mensagem
    public String getDocument(String requisicao){
        for(int i = 0; i < arquivos.length; i++){
                String arquivo = arquivos[i] + "";
                arquivo = arquivo.substring(7);
                if(requisicao.equals(arquivo)){
                    return arquivos[i] + "";
                }
        }
        return "Não foi encontrado nenhum arquivo";
    }

}