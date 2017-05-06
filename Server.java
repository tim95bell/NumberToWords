import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.StringBuilder;

public class Server {

    private ServerSocket serverSocket;
    private String httpOk = "HTTP/1.1 200 OK\r\n\r\n";
    private String topHtml;
    private String bottomHtml;

    public Server() throws Exception {
        serverSocket = new ServerSocket(8080);

        String file = loadFile("index.html");
        String[] split = file.split("</body>");
        topHtml = split[0];
        bottomHtml = "</head>"+split[1];
    }

    public void run() throws Exception {
        boolean running = true;
        while(running){
            Socket socket = serverSocket.accept();
            String request = readRequest(socket);
            handleRequest(socket, request);
            socket.close();
        }
    }

    public String readRequest(Socket socket){
      String request = "";
      try{
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        request = reader.readLine();
      } catch(IOException e){
        return null;
      }
      return request;
    }

    public void handleRequest(Socket socket, String request){
      if(request == null)
        return;
      //System.out.println("request: " + request);
      String[] splitRequest = request.split(" ");
      if(splitRequest[0].equals("GET")){
        handleGetRequest(socket, splitRequest);
      }
    }

    public void handleGetRequest(Socket socket, String[] splitRequest){
      String location = "";
      if(splitRequest[1].contains("?")){
        location = ( fs[1].split("\\?") )[0];
      } else{
        location = fs[1];
      }

      if(location.equals("/")){
        handleHomeGetRequest(socket);
      } else if(location.equals("/numberInput")){
        handleNumberInputRequest(socket, fs[1]);
      }
    }

    public void handleHomeGetRequest(Socket socket){
      respond("");
    }

    public void handleNumberInputRequest(Socket socket, String locAndVal){
      String varAndVal = (locAndVal.split("\\?"))[1];
      String val = (varAndVal.split("="))[1];
      respond( numberToWords(val) );
    }

    public void respond(Socket socket, String value){
      try{
        String httpResponse = httpOk + topHtml + value + bottomHtml;
        socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
      } catch(Exception e){
        e.printStackTrace();
      }
    }

    public String loadFile(String file) {
      String answer = "";
      try{
        Scanner in = new Scanner(new File(file));
        while(in.hasNext()){
          answer += in.nextLine();
        }
      } catch(FileNotFoundException e){
        e.printStackTrace();
      }
      return answer;
    }

    public String numberToWords(String number){
      number = StringBuilder(number).reverse().toString();
      int length = number.length();
      if(length == 0){

      } else if(length == 1){
        unitsToWords(number.charAt(0));
      } else{
        tensAndUnitsToWords(number.substring(0,2));
      }
    }

}
