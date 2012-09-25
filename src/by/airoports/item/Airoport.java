package by.airoports.item;

public class Airoport implements AiroportItem, Comparable<Airoport> {

	private final String name;

	public Airoport() {
		this.name = "";
	}
	public Airoport(final String name) {
		this.name = name;
	}

	@Override
	public boolean isSectionItem() {
		return false;
	}

	@Override
	public int compareTo(Airoport another) {
		if(this.getName().startsWith("(")){
			return 1;
		}
		return this.getName().compareTo(another.getName());
	}

	public String getName() {
		return name;
	}
}
