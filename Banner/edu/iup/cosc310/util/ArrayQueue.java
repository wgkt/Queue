package edu.iup.cosc310.util;

/**
 * Class that creates an array based queue
 * 
 * @author wgkt
 * 
 * @param <E>
 */
public class ArrayQueue<E> implements ItemQueue<E> {
	private int front;
	private int rear;
	private int size;
	private int capacity;
	private static final int DEFAULT_CAPACITY = 4;
	private E[] data;

	/**
	 * Creates the initial size of the queue
	 */
	public ArrayQueue() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor for the ArrayQueue
	 * 
	 * @param initCapacity
	 */
	public ArrayQueue(int initCapacity) {
		capacity = initCapacity;
		data = (E[]) new Object[capacity];
		front = 0;
		rear = capacity - 1;
		size = 0;
	}

	/**
	 * Method to add an item to the rear of the queue
	 */
	public synchronized void enqueueItem(E item) {
		if (size == capacity) {
			reallocate();
		}
		size++;
		rear = (rear + 1) % capacity;
		data[rear] = item;

	}

	/**
	 * Method to remove an item from the front of the queue
	 */
	public synchronized E dequeueItem() {
		if (size == 0) {
			return null;
		}
		E result = data[front];
		front = (front + 1) % capacity;
		size--;
		return result;
	}

	/**
	 * Method to test if the queue is empty
	 */
	public synchronized boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method to increase the size of the array if needed
	 */
	private synchronized void reallocate() {
		int newCapacity = 2 * capacity;
		E[] newData = (E[]) new Object[newCapacity];
		int j = front;
		for (int i = 0; i < size; i++) {
			newData[i] = data[j];
			j = (j + 1) % capacity;
		}
		front = 0;
		rear = size - 1;
		capacity = newCapacity;
		data = newData;
	}

	/**
	 * Method to get the data from the front of the queue
	 */
	public synchronized E peekItem() {
		if (size == 0) {
			return null;
		} else {
			return data[front];
		}
	}

	/**
	 * Method to get the number of items in the queue
	 */
	public synchronized int noItems() {
		return size;
	}

}
