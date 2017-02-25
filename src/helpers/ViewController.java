package helpers;

public abstract class ViewController<T> {
	protected T mother;
	
	public abstract void beforeRender ();
	public abstract void beforeRemove ();
	
	public void setMother (T mother) {
		this.mother = mother;
	}
}
