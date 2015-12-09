package InteractiveLearner.Model;

public class Tuple<X, Y> { 
	private X x; 
	private Y y; 

	public Tuple() {
		this.x = null;
		this.y = null;
	}

	public Tuple(X x, Y y) { 
		this.x = x; 
		this.y = y; 
	}

	/**
	 * Returns the first element of the tuple.
	 * @return X in Tuple<X, Y>
	 */
	public X getFirstElement() {
		return this.x;
	}

	/**
	 * Returns the second element of the tuple.
	 * @return Y in Tuple<X, Y>
	 */
	public Y getSecondElement() {
		return this.y;
	}

	/**
	 * Sets a new value for the first element of the tuple.
	 * @param newX is the value which will replace X
	 */
	public void setFirstElement(X newX) {
		this.x = newX;
	}

	/**
	 * Sets a new value for the second element of the tuple.
	 * @param newY is the value which will replace Y
	 */
	public void setSecondElement(Y newY) {
		this.y = newY;
	}

	/**
	 * Compares the first element of the tuple to another object of the same class.
	 * @param otherX is the object which will be compared to X
	 * @return true if the objects are equal
	 */
	public boolean compareX(X otherX) {
		return this.x.equals(otherX);
	}

	/**
	 * Compares the second element of the tuple to another object of the same class.
	 * @param otherY is the object which will be compared to Y
	 * @return true if the objects are equal
	 */
	public boolean compareY(Y otherY) {
		return this.y.equals(otherY);
	}

	/**
	 * Compares the first and second element of the tuple with the two parameter values.
	 * The first parameter will be compared to the first element.
	 * The second parameter will be compared to the second element.
	 * @param firstElement will be compared with X
	 * @param secondElement will be compared with Y
	 * @return true if both element are equal
	 */
	public boolean compare(X firstElement, Y secondElement) {
		return this.x.equals(firstElement) && this.y.equals(secondElement);
	}

	/**
	 * Returns a string representation of the tuple.
	 */
	@Override
	public String toString() {
		return "<" + this.x + ", " + this.y + ">";
	}

	/**
	 * Calculates the hash code of the Tuple object.
	 */
	@Override
	public int hashCode() {
		return x.hashCode() + y.hashCode();  
	}

	/**
	 * Compares the Tuple object to another object
	 * @param obj is the object that the Tuple will be compared with
	 * @return true if the objects are equal and have the same contents
	 */
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;

		if (obj instanceof Tuple<?, ?>) {
			@SuppressWarnings("unchecked")
			Tuple<X, Y> temp = (Tuple<X, Y>) obj;
			equals = this.compare(temp.getFirstElement(), temp.getSecondElement());
		}

		return equals;
	}
} 
