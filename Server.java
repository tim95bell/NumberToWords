import java.net.ServerSocket;
import java.net.Socket;
import java.lang.StringBuilder;

public class Server {

	public static final String HTTP_OK = "HTTP/1.1 200 OK\r\n\r\n";
	
    private ServerSocket serverSocket;
	private HtmlPage htmlPage;
	private NumberToWords numberToWords;

    public Server() throws Exception {
        serverSocket = new ServerSocket(8080);
		htmlPage = new HtmlPage();
		numberToWords = new NumberToWords();
    }

    public void run() throws Exception {
		init();
        boolean running = true;
        while(running){
            Socket socket = serverSocket.accept();
			SocketThread socketThread = new SocketThread(socket, htmlPage, numberToWords);
			socketThread.start();
        }
    }
	
	private void init(){
		htmlPage.loadFile("index.html");
	}

}
