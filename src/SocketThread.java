import java.lang.Thread;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SocketThread extends Thread {
	
	private Socket socket;
	private HtmlPage htmlPage;
	private NumberToWords numberToWords;
	
	public SocketThread(Socket socket, HtmlPage htmlPage, NumberToWords numberToWords){
		this.socket = socket;
		this.htmlPage = htmlPage;
		this.numberToWords = numberToWords;
	}
	
	@Override
	public void run() {
		String request = readRequest();
        handleRequest(request);
		try{
			socket.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public String readRequest(){
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
	
	public void handleRequest(String request){
		if(request == null)
			return;
		
		String[] splitRequest = request.split(" ");
		if(splitRequest[0].equals("GET")){
			handleGetRequest(splitRequest);
		}
    }

    public void handleGetRequest(String[] splitRequest){
		// obtain location
		String location = "";
		if(splitRequest[1].contains("?")){
			location = ( splitRequest[1].split("\\?") )[0];
		} else{
			location = splitRequest[1];
		}
		
		// handle based on location
		if(location.equals("/")){
			handleHomeGetRequest();
		} else if(location.equals("/numberInput")){
			handleNumberInputRequest(splitRequest[1]);
		}
    }

    public void handleHomeGetRequest(){
      respond("");
    }

    public void handleNumberInputRequest(String locVarAndVal){
      String varAndVal = (locVarAndVal.split("\\?"))[1];
      String[] varAndValSplit = varAndVal.split("=");
	  String val = "0";
	  if(varAndValSplit.length > 1){
		val = varAndValSplit[1];
	  }
      respond( numberToWords.process(val) );
    }

    public void respond(String value){
      try{
        byte[] httpResponse = htmlPage.getHttpResponse(value);
        socket.getOutputStream().write(httpResponse);
      } catch(Exception e){
        e.printStackTrace();
      }
    }
	
}