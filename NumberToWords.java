public class NumberToWords {
	
	public static final String HUNDRED = "Hundred";
	public static final String THOUSAND = "Thousand";
	public static final String MILLION = "Million";
	
	public NumberToWords(){
		
	}
	
	public String process(String input){
		String answer = "";
		String[] validParts = validateFormat(input);
		String wholePart = "";
		String fractionalPart = "";
		if(validParts == null)
			return "invalid";
		else{
			wholePart = processWholePart(validParts[0]);
			fractionalPart = processFractionalPart(validParts[1]);
		}
		return answer = wholePart + fractionalPart;
	}
	
	private String processWholePart(String wholePart){
		String answer = "";
		int numSections = wholePart.length()/3;
		for(int i = 0; i < wholePart.length(); i += 3){
			String section = wholePart.substring(i, i+3);
			int sectionNumber = numSections - i/3;
			answer += processWholePartSection(section, sectionNumber);
		}
		if(answer.length() == 0){
			answer = "Zero";
		}
		answer += " Dollars ";
		return answer;
	}
	
	private String processFractionalPart(String fractional){
		String answer = tensAndUnits(fractional.charAt(0), fractional.charAt(1));
		if(answer.length() != 0){
			answer = "and " + answer + " Cents";
		}
		return answer;
	}
	
	private String processWholePartSection(String tripple, int sectionNumber){
		String answer = "";
		switch(sectionNumber){
			case 1:
			answer = basicTripple(tripple);
			break;
			case 2:
			answer = thousandsTripple(tripple);
			break;
			case 3:
			answer = millionsTripple(tripple);
			break;
		}
		return answer;
	}
	
	private String[] validateFormat(String input){
		//TODO: boolean negative
		if(input.charAt(0) == '-')
			input = input.substring(1, input.length());
		if(input.charAt(0) == '.')
			input = "0"+input;
		if(input.charAt(input.length()-1) == '.'){
			input += "00";
		}
		
		String wholePart = "";
		String fractionalPart = "";
		if(input.contains(".")){
			System.out.println(input);
			String[] split = input.split("\\.");
			System.out.println(split.length);
			wholePart = split[0];
			if(split.length > 2){
				// more than one decimal point
				return null;
			} else if(split.length == 2){
				fractionalPart = split[1];
				if(fractionalPart.length() > 2){
					// fractional part bigger than 2 places
					return null;
				} else if(fractionalPart.length() < 2){
					while(fractionalPart.length() < 2){
						fractionalPart += "0";
					}
				}
			}
		} else {
			// no decimal point
			wholePart = input;
			fractionalPart = "00";
		}
		
		// now in formal XXXXX.XX
		// check for other symbols
		int wholePartLength = wholePart.length();
		for(int i = 0; i < wholePartLength; ++i){
			if(!isNumber(wholePart.charAt(i))){
				return null;
			}
		}
		int fractionalPartLength = fractionalPart.length();
		for(int i = 0; i < fractionalPartLength; ++i){
			if(!isNumber(fractionalPart.charAt(i))){
				return null;
			}
		}
		
		// make wholePart divisible by 3
		int extraNeeded = 3 - (wholePart.length()%3);
		if(extraNeeded == 3)
			extraNeeded = 0;
		for(int i = 0; i < extraNeeded; ++i){
			wholePart = "0"+wholePart;
		}
		
		return new String[]{wholePart, fractionalPart};
	}
	
	private boolean isNumber(char c){
		int cInt = (int)c;
		int zeroInt = (int)'0';
		int nineInt = (int)'9';
		return cInt >= zeroInt && cInt <= nineInt;
	}
	
	private String unit(char c){
		String answer = "";
		switch(c){
			case '0':
			answer = "Zero";
			break;
			case '1':
			answer = "One";
			break;
			case '2':
			answer = "Two";
			break;
			case '3':
			answer = "Three";
			break;
			case '4':
			answer = "Four";
			break;
			case '5':
			answer = "Five";
			break;
			case '6':
			answer = "Six";
			break;
			case '7':
			answer = "Seven";
			break;
			case '8':
			answer = "Eight";
			break;
			case '9':
			answer = "Nine";
			break;
		}
		return answer;
	}
	
	private String tens(char c){
		String answer = "";
		switch(c){
			case '1':
			answer = "Ten";
			break;
			case '2':
			answer = "Twenty";
			break;
			case '3':
			answer = "Thirty";
			break;
			case '4':
			answer = "Fourty";
			break;
			case '5':
			answer = "Fifty";
			break;
			case '6':
			answer = "Sixty";
			break;
			case '7':
			answer = "Seventy";
			break;
			case '8':
			answer = "Eighty";
			break;
			case '9':
			answer = "Ninety";
			break;
		}
		return answer;
	}
	
	private String tensAndUnitsForTenToTwenty(char units){
		String answer = "";
		switch(units){
			case '0':
			answer = "Ten";
			break;
			case '1':
			answer = "Eleven";
			break;
			case '2':
			answer = "Twelve";
			break;
			case '3':
			answer = "Thirteen";
			break;
			case '4':
			answer = "Fourteen";
			break;
			case '5':
			answer = "Fifteen";
			break;
			case '6':
			answer = "Sixteen";
			break;
			case '7':
			answer = "Seventeen";
			break;
			case '8':
			answer = "Eighteen";
			break;
			case '9':
			answer = "Nineteen";
			break;
		}
		return answer;
	}
	
	private String tensAndUnitsForAboveTwenty(char tens, char units){
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
	
	private String tensAndUnits(char tens, char units){
		String answer = "";
		if(tens == '1'){
			answer = tensAndUnitsForTenToTwenty(units);
		} else {
			answer = tensAndUnitsForAboveTwenty(tens, units);
		}
		return answer;
	}
	
	private String basicTripple(String tripple){
		String answer = "";
		//hundreds
		String hundredsString = "";
		boolean hasHundreds = tripple.charAt(0) != '0';
		if(hasHundreds){
			hundredsString += unit(tripple.charAt(0)) + " " + HUNDRED;
		}
		//tens and units
		String tensAndUnitsString = "";
		boolean hasTensAndOrUnits = tripple.charAt(1) != '0' || tripple.charAt(2) != '0';
		tensAndUnitsString = tensAndUnits(tripple.charAt(1), tripple.charAt(2));
		// combine
		if(hasHundreds && hasTensAndOrUnits){
			answer = hundredsString + " and " + tensAndUnitsString;
		} else if(hasHundreds){
			answer = hundredsString;
		} else if(hasTensAndOrUnits){
			answer = tensAndUnitsString;
		}
		return answer;
	}
	
	private String thousandsTripple(String tripple){
		String answer = basicTripple(tripple);
		if(answer.length() > 0){
			answer += " " + THOUSAND + " ";
		}
		return answer;
	}
	
	private String millionsTripple(String tripple){
		String answer = basicTripple(tripple);
		if(answer.length() > 0){
			answer += " " + MILLION + " ";
		}
		return answer;
	}
	
	
	
	
	
	
}