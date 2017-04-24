/**
 * Simple node class for single linked list 
 * @author CS221 
 * @author Nathan King
 *
 * @param <T> generic type of elements stored in a node
 */
public class LinearNode<T>
{
	private LinearNode<T> next;		// reference to next node
	private T element;			// reference to object stored in node 
	private LinearNode<T> prev;		//reference to prev node
	
	/**
	 * Constructor - with given element 
	 * @param element - object of type T
	 */
	public LinearNode(T element)
	{
		setElement(element);
		setNext(null);
		setPrevious(null);
	}

	/**
	 * Returns reference to next node
	 * @return - ref to LinearNode<T> object 
	 */
	public LinearNode<T> getNext()
	{
		return next;
	}
	
	/**
	 * Returns refernce to prev node
	 * @return - ref to LinearNode<T> object
	 */
	public LinearNode<T> getPrevious()
	{
		return prev;
	}

	/**
	 * Assign reference to next node 
	 * @param next - ref to Node<T> object 
	 */
	public void setNext(LinearNode<T> next)
	{
		this.next = next;
	}
	
	/**
	 * Assign reference to prev node
	 * @param prev - ref to Node<T> object
	 */
	public void setPrevious(LinearNode<T> prev)
	{
		this.prev = prev;
	}

	/**
	 * Returns reference to node stored in node 
	 * @return - ref to object of type T 
	 */
	public T getElement()
	{
		return element;
	}

	/**
	 * Sets reference to element stored at node
	 * @param element - ref to object of type T
	 */
	public void setElement(T element)
	{
		this.element = element;
	}
	
	
}