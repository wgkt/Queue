package edu.iup.cosc310.banner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JWindow;

import edu.iup.cosc310.util.ArrayQueue;
import edu.iup.cosc310.util.ItemQueue;

/**
 * Banner provides a smooth scroll banner window in which to display messages.
 * The characters to be displayed in the banner are accessed from a queue. A
 * queue of Charaters must be provided on construction of a Banner object
 * 
 * @author dtsmith
 * 
 */
@SuppressWarnings("serial")
public class Banner<E> extends JWindow {
	/**
	 * CharInfo is an internal class used to keep track of the display of a
	 * character while it is being displayed by Banner
	 * 
	 * @author dtsmith
	 * 
	 */
	private class CharInfo {
		String character;

		int position;

		int width;

		public CharInfo(String character, int position, int width) {
			this.character = character;
			this.position = position;
			this.width = width;
		}
	}

	private ItemQueue<Character> queue;

	private LinkedList<CharInfo> charsInFlight = new LinkedList<CharInfo>();

	private Font font = new Font("Default", 0, 50);

	private Image buffer;

	private Graphics bufferGraphics;

	private boolean started = false;

	/**
	 * Constructor - create a Banner
	 * 
	 * Note: the created Banner will not be displayed until setVisible(true) is
	 * called.
	 * 
	 * Note: Banner will not begin displaying characters until start() is
	 * called.
	 * 
	 * Warning: the start method will begin a separate thread of execution. THUS
	 * THE QUEUE SUPPLIED MUST BE THREAD SAFE.
	 * 
	 * @param queue
	 *            a queue of Characters from which Banner will get its
	 *            characters
	 */
	public Banner(ItemQueue<Character> queue) {
		this.queue = queue;
		getFontMetrics(font).getHeight();
		setSize(500, getFontMetrics(font).getHeight());
		setLocation(100, 100);
	}

	/**
	 * Paint the rendering of the banner
	 * 
	 * @param g
	 *            the Graphics to use to provide the rendering
	 */
	public void paint(Graphics g) {
		if (bufferGraphics == null) {
			buffer = createImage(getSize().width, getSize().height);
			bufferGraphics = buffer.getGraphics();
		}
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		bufferGraphics.setColor(Color.black);
		bufferGraphics.setFont(font);
		for (CharInfo charInfo : charsInFlight) {
			bufferGraphics.drawString(charInfo.character, charInfo.position,
					bufferGraphics.getFontMetrics().getAscent());
		}
		bufferGraphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.drawImage(buffer, 0, 0, this);
	}

	/**
	 * Start the banner scrolling
	 */
	public void start() {
		started = true;
		new Thread(new Runnable() {
			public void run() {
				while (started) {
					CharInfo lastCharInfo = null;
					for (Iterator<CharInfo> iter = charsInFlight.iterator(); iter
							.hasNext();) {
						lastCharInfo = iter.next();
						lastCharInfo.position--;
						if (lastCharInfo.position + lastCharInfo.width < 0) {
							iter.remove();
						}
					}
					if (lastCharInfo == null
							|| lastCharInfo.position + lastCharInfo.width < Banner.this
									.getWidth()) {
						String character = "" + getNextCharacter();
						charsInFlight.add(new CharInfo(character, Banner.this
								.getWidth(), getFontMetrics(font).stringWidth(
								character)));
					}

					Banner.this.repaint();
					try {
						Thread.sleep(12);
					} catch (InterruptedException e) {
					}
				}
			}

		}).start();
	}

	/**
	 * Stop the banner from scrolling
	 */
	public void stop() {
		started = false;
	}

	/**
	 * Get the next character to be displayed in the banner
	 * 
	 * @return next character to be displayed
	 */
	private char getNextCharacter() {
		if (!queue.isEmpty()) {
			return ((Character) queue.dequeueItem()).charValue();
		} else {
			return ' ';
		}
	}
/**
 * Main class to display banner and read user input
 * @param args
 */
	public static void main(String[] args) {
		// TODO replace new with your array based circular queue
		ItemQueue<Character> queue = new ArrayQueue<Character>();
		// Leave alone();
		Banner banner = new Banner(queue);
		banner.setVisible(true);
		banner.start();

		// TODO replace all following code with code to read from keyboard and
		// add characters into the queue

		System.out.println(">");
		while (true) {
			Scanner in = new Scanner(System.in);
			String line = in.next();
			for (int i = 0; i < line.length(); i++) {
				char n = line.charAt(i);
				queue.enqueueItem(new Character(n));
			}
		}

	}
}
