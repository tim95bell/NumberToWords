


public class NumberDoubleSection {
	
	protected String doubleInDigits;
	protected String doubleInWords;
	protected boolean hasTens;
	protected boolean hasUnits;
	
	public NumberDoubleSection(String dbl){
		this.doubleInDigits = dbl;
		char tens = dbl.charAt(0);
		char units = dbl.charAt(1);
		this.hasTens = tens != '0';
		this.hasUnits = units != '0';
		if(tens == '1'){
			this.doubleInWords = tensAndUnitsForTenToTwenty(units);
		} else {
			this.doubleInWords = tensAndUnitsForAboveTwenty(tens, units);
		}
	}
	
	protected String unit(char c){
		String answer = "";
		switch(c){
			case '0':
			answer = "ZERO";
			break;
			case '1':
			answer = "ONE";
			break;
			case '2':
			answer = "TWO";
			break;
			case '3':
			answer = "THREE";
			break;
			case '4':
			answer = "FOUR";
			break;
			case '5':
			answer = "FIVE";
			break;
			case '6':
			answer = "SIX";
			break;
			case '7':
			answer = "SEVEM";
			break;
			case '8':
			answer = "EIGHT";
			break;
			case '9':
			answer = "NINE";
			break;
		}
		return answer;
	}
	
	protected String tens(char c){
		String answer = "";
		switch(c){
			case '1':
			answer = "TEN";
			break;
			case '2':
			answer = "TWENTY";
			break;
			case '3':
			answer = "THIRTY";
			break;
			case '4':
			answer = "FOURTY";
			break;
			case '5':
			answer = "FIFTY";
			break;
			case '6':
			answer = "SIXTY";
			break;
			case '7':
			answer = "SEVENTY";
			break;
			case '8':
			answer = "EIGHTY";
			break;
			case '9':
			answer = "NINETY";
			break;
		}
		return answer;
	}
	
	protected String tensAndUnitsForTenToTwenty(char units){
		String answer = "";
		switch(units){
			case '0':
			answer = "TEN";
			break;
			case '1':
			answer = "ELEVEN";
			break;
			case '2':
			answer = "TWELVE";
			break;
			case '3':
			answer = "THIRTEEN";
			break;
			case '4':
			answer = "FOURTEEN";
			break;
			case '5':
			answer = "FIFTEEN";
			break;
			case '6':
			answer = "SIXTEEN";
			break;
			case '7':
			answer = "SEVENTEEN";
			break;
			case '8':
			answer = "EIGHTEEN";
			break;
			case '9':
			answer = "NINETEEN";
			break;
		}
		return answer;
	}
	
	protected String tensAndUnitsForAboveTwenty(char tens, char units){
		boolean hasUnits = false;
		boolean hasTens = false;
		String tensWords = "";
		if(tens != '0'){
			hasTens = true;
			tensWords = tens(tens);
		}
		String unitsWords = "";
		if(units != '0'){
			hasUnits = true;
			unitsWords = unit(units);
		}
		
		String answer = "";
		if(hasTens && hasUnits){
			answer = tensWords + "-" + unitsWords;
		} else if(hasTens){
			answer = tensWords;
		} else if(hasUnits){
			answer = unitsWords;
		}
		return answer;
	}
	
	public String getWords(){
		return doubleInWords;
	}
	
	public boolean isEmpty(){
		return !hasTens && !hasUnits;
	}
}