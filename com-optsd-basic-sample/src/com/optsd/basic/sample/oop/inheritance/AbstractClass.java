package com.optsd.basic.sample.oop.inheritance;

import java.awt.Component;
import java.awt.TextArea;

import org.junit.Test;

public class AbstractClass {
	
	public abstract class Component {
		private int x, y;

		public void setPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public abstract void render();
	}

	public class Button extends Component {
		@Override
		public void render() {
			// render a button
		}
	}

	public class TextBox extends Component {
		@Override
		public void render() {
			// render a textbox
		}
	}
	
	@Test
	public void usage()
	{
		// error: Component is abstract; cannot be instantiated
		Component myComponent = new Component();
		
		Component myButton = new Button();
		Component myTextBox = new TextBox();
		
		myButton.render(); //renders a button
		myTextBox.render(); //renders a text box
		
		// Anonymous subclasses of Abstract Classes
		Component myAnonymousComponent = new Component() {

			@Override
			public void render() {
				// render a quick 1-time use component
			}
		};
		myAnonymousComponent.render();
	}
}
