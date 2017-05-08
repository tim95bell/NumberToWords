


public class NumberTripleSection extends NumberDoubleSection {
	
	public enum Place { HUNDREDS, THOUSANDS, MILLIONS, BILLIONS, TRILLIONS };
	public static final int MAX_PLACE = 5;
	public static final String HUNDRED = " HUNDRED";
	public static final String THOUSAND = " THOUSAND ";
	public static final String MILLION = " MILLION ";
	public static final String BILLION = " BILLION ";
	public static final String TRILLION = " TRILLION ";
	
	private String tripleInDigits;
	private String tripleInWords;
	private boolean containsAnd;
	private boolean isEmpty;
	private boolean prependAnd;
	private Place place;
	
	public NumberTripleSection(String triple, int sectionNumber){
		super( triple.substring(1,3) );
		this.load(triple, sectionNumber);
	}
	
	public void load(String triple, int sectionNumber){
		this.tripleInDigits = triple;
		this.tripleInWords = "";
		this.containsAnd = false;
		this.isEmpty = true;
		this.prependAnd = false;
		switch(sectionNumber){
			case 1:
				this.place = Place.HUNDREDS;
				break;
			case 2:
				this.place = Place.THOUSANDS;
				break;
			case 3:
				this.place = Place.MILLIONS;
				break;
			case 4:
				this.place = Place.BILLIONS;
				break;
			case 5:
				this.place = Place.TRILLIONS;
				break;
		}
		this.tripleInWords = processtriple(tripleInDigits.charAt(0));
	}
	
	private String processtriple(char hundreds){
		String answer = basictriple(hundreds);
		switch(place){
			case THOUSANDS:
				answer += THOUSAND;
				break;
			case MILLIONS:
				answer += MILLION;
				break;
			case BILLIONS:
				answer += BILLION;
				break;
			case TRILLIONS:
				answer += TRILLION;
				break;
		}
		return answer;
	}
	
	private String basictriple(char hundreds){
		String answer = "";
		//hundreds
		String hundredsString = "";
		boolean hasHundreds = hundreds != '0';
		if(hasHundreds){
			hundredsString += unit(hundreds) + HUNDRED;
		}
		// combine
		if(hasHundreds && (hasTens || hasUnits)){
			answer = hundredsString + " AND " + doubleInWords;
			this.containsAnd = true;
			this.isEmpty = false;
		} else if(hasHundreds){
			answer = hundredsString;
			this.isEmpty = false;
		} else if(hasTens || hasUnits){
			answer = doubleInWords;
			this.isEmpty = false;
		} else {
			answer = "ZERO";
			this.isEmpty = true;
		}
		return answer;
	}
	
	public void setPrependAnd(boolean val){
		prependAnd = val;
	}
	
	public String getWords(){
		String answer = "";
		if(prependAnd){
			answer += "AND ";
		}
		answer += tripleInWords;
		return answer;
	}
	
	public boolean containsAnd(){
		return containsAnd;
	}
	
	public boolean isEmpty(){
		return isEmpty;
	}

}