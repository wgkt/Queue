package edu.iup.cosc310.util;

import java.util.NoSuchElementException;
/**
 * Test class to ensure that the ArrayQueue works properly
 * @author wgkt
 *
 * @param <E>
 */
public class TestQueue<E> {

	public static void main(String[] args) {
		ArrayQueue<String> queue = new ArrayQueue<String>();

		assert queue.isEmpty() == true : "Expected true, returned" + queue.isEmpty();

		try {
			queue.peekItem();
			assert true : "NoSuchElementException not thrown on peekItem()";
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			assert false : "Wrong execption thrown on peekItem(), got " + e;
		}

		try {
			queue.dequeueItem();
			assert true : "NoSuchElementException not thrown on dequeueItem()";
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			assert false : "Wrong execption thrown on dequeueItem(), got " + e;
		}

		queue.enqueueItem("a");
		queue.enqueueItem("b");
		queue.enqueueItem("c");

		assert queue.noItems() == 3 : "Expected 3, returned" + queue.noItems();
		assert queue.isEmpty() == false : "Expected false, returned" + queue.isEmpty();

		queue.enqueueItem("d");
		queue.enqueueItem("e");
		queue.enqueueItem("f");

		assert queue.noItems() == 6 : "Expected 6, returned" + queue.noItems();
		assert queue.peekItem() == "a" : "Expected item 'a', returned" + queue.peekItem();

		//Tests reallocation when tail < head
		queue.dequeueItem();      
		queue.enqueueItem("x");   
		queue.enqueueItem("y");   

		assert queue.peekItem() == "b" : "Expected item 'b', returned" + queue.peekItem();
		assert queue.noItems() == 7 : "Expected 7, returned" + queue.noItems();

		queue.dequeueItem();

		assert queue.peekItem() == "c" : "Expected item 'c', returned" + queue.peekItem();
		assert queue.noItems() == 6 : "Expected 6, returned" + queue.noItems();

		queue.dequeueItem();

		assert queue.peekItem() == "d" : "Expected item 'd', returned" + queue.peekItem();
		assert queue.noItems() == 5 : "Expected 5, returned" + queue.noItems();

		queue.dequeueItem();

		assert queue.peekItem() == "e" : "Expected item 'e', returned" + queue.peekItem();
		assert queue.noItems() == 4 : "Expected 4, returned" + queue.noItems();

		queue.dequeueItem();

		assert queue.peekItem() == "f" : "Expected item 'f', returned" + queue.peekItem();
		assert queue.noItems() == 3 : "Expected 3, returned" + queue.noItems();
		
		queue.dequeueItem();
		
		assert queue.peekItem() == "x" : "Expected item 'x', returned" + queue.peekItem();
		assert queue.noItems() == 2 : "Expected 2, returned" + queue.noItems();
		
		queue.dequeueItem();
		
		assert queue.peekItem() == "y" : "Expected item 'y', returned" + queue.peekItem();
		assert queue.noItems() == 1 : "Expected 1, returned" + queue.noItems();
		
		queue.dequeueItem();

		
		assert queue.peekItem() == null : "Expected null, returned" + queue.peekItem();
		assert queue.isEmpty() == true : "Expected true, returned" + queue.isEmpty();
		
		try {
			queue.peekItem();
			assert true : "NoSuchElementException not thrown on peekItem()";
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			assert false : "Wrong execption thrown on peekItem(), got " + e;
		}

		try {
			queue.dequeueItem();
			assert true : "NoSuchElementException not thrown on dequeueItem()";
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			assert false : "Wrong execption thrown on dequeueItem(), got " + e;
		}

		
		assert false : "Test Complete";

	}

}
