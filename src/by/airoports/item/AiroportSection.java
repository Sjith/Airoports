package by.airoports.item;

public class AiroportSection implements AiroportItem {
	private final char sectionLetter;

	public AiroportSection(final char sectionLetter) {
		this.sectionLetter = sectionLetter;
	}
	public String getSectionLetter() {
		if(sectionLetter == '('){
			return "Прочие";
		}
		return String.valueOf(sectionLetter);		
	}
	
	public boolean isSectionItem() {
		return true;
	}
}
