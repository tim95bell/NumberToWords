import java.util.ArrayList;

public class NumberToWords {
	
	public static final String DOLLARS = " DOLLARS ";
	
	public NumberToWords(){
		
	}
	
	public String process(String input){
		String[] validParts = validateFormat(input);
		String wholePart = "";
		String fractionalPart = "";
		if(validParts == null)
			return "invalid";
		else{
			wholePart = processWholePart(validParts[0]);
			fractionalPart = processFractionalPart(validParts[1]);
		}
		
		if(wholePart.equals("NEGATIVE ZERO DOLLARS ") && fractionalPart.length() == 0)
			return "invalid";
			
		return "$" + input + "<br>" + wholePart + fractionalPart;
	}
	
	private String processWholePart(String wholePart){
		boolean negative = false;
		if(wholePart.charAt(0) == '-'){
			negative = true;
			wholePart = wholePart.substring(1,wholePart.length());
		}
		
		String answer = "";
		ArrayList<NumberTripleSection> parts = new ArrayList<NumberTripleSection>();
		int numSections = wholePart.length()/3;
		// split into 3s and process each part
		for(int i = 0; i < numSections; ++i){
			String section = wholePart.substring(i*3, i*3+3);
			int sectionNumber = numSections-i;
			NumberTripleSection nts = new NumberTripleSection(section, sectionNumber);
			if(!nts.isEmpty()){
				parts.add(nts);
			}
		}
		
		// add an extra and if needed on the last part
		if(parts.size() > 1 && !parts.get(parts.size()-1).containsAnd()){
			parts.get(parts.size()-1).setPrependAnd(true);
		}
		
		for(NumberTripleSection nts : parts){
			answer += nts.getWords();
		}
		
		if(answer.length() == 0){
			answer = "ZERO";
		}
		if(negative ){
			answer = "NEGATIVE " + answer;
		}
		
		answer += DOLLARS;
		return answer;
	}
	
	private String processFractionalPart(String fractional){
		String answer = "";
		NumberDoubleSection nds = new NumberDoubleSection(fractional);
		if(!nds.isEmpty()){
			answer = "AND " + nds.getWords() + " CENTS";
		}
		return answer;
	}

	private String[] validateFormat(String input){
		boolean negative = false;
		if(input.charAt(0) == '-'){
			input = input.substring(1, input.length());
			negative = true;
			if(input.length() == 0){
				input = "0";
			}
		}
		if(input.charAt(0) == '.')
			input = "0"+input;
		if(input.charAt(input.length()-1) == '.'){
			input += "00";
		}
		
		String wholePart = "";
		String fractionalPart = "";
		if(input.contains(".")){
			String[] split = input.split("\\.");
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
		
		// check not to long
		if(wholePart.length() > NumberTripleSection.MAX_PLACE*3){
			return null;
		}
		
		if(negative){
			wholePart = "-"+wholePart;
		}
		
		return new String[]{wholePart, fractionalPart};
	}
	
	private boolean isNumber(char c){
		int cInt = (int)c;
		int zeroInt = (int)'0';
		int nineInt = (int)'9';
		return cInt >= zeroInt && cInt <= nineInt;
	}
	
}