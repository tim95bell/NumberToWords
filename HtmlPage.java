import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class HtmlPage {
	
	private String topHtml;
	private String bottomHtml;
	
	public HtmlPage(){
		topHtml = "";
		bottomHtml = "";
	}
	
	public void loadFile(String file){
		String content = "";
		try{
			Scanner in = new Scanner(new File(file));
			while(in.hasNext()){
				content += in.nextLine();
			}
			
			String[] split = content.split("\\{input\\}");
			topHtml = split[0];
			bottomHtml = split[1];
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public byte[] getHttpResponse(String numberVal){
		String answer = Server.HTTP_OK + topHtml + numberVal + bottomHtml;
		return answer.getBytes();
	}
	
}