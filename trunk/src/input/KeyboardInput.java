package input;

/*
 * INPUT DEL MAGO
 */

import java.util.Set;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;

public class KeyboardInput extends InputHandler implements InputInterface {
	CharacterController controller;
	InputActionInterface listener;
	Set<String> actions;
	
	public KeyboardInput(CharacterController controller) {
		super();
		this.controller = controller;
		this.actions = controller.getActions();
		listener = new KeyboardListener(controller);
		
		//TODO per ogni azione settare un input (IMPORTANTE)
		
		//aggiunge azioni per il debug
		this.addAction(listener, "spell>Fireball", KeyInput.KEY_F, false);
		this.addAction(listener, "switch_pos>", KeyInput.KEY_LCONTROL, true);
		addMovementKeys();
	}
	
	// aggiunge il moviento risposto al tasto scelto( frecce direzionali )
	protected void addMovementKeys() {
		this.addAction(listener, "move>forward", KeyInput.KEY_UP, true);
		this.addAction(listener, "move>backward", KeyInput.KEY_DOWN, true);
		this.addAction(listener, "move>left", KeyInput.KEY_LEFT, true);
		this.addAction(listener, "move>right", KeyInput.KEY_RIGHT, true);
	}

	public short getPlayerID() {
		return controller.playerID;
	}
	
	public class KeyboardListener implements InputActionInterface {
		CharacterController controller;

		public KeyboardListener(CharacterController controller) {
			super();
			this.controller = controller;
		}
		
		@Override
		public void performAction(InputActionEvent evt) {
			controller.performAction(evt.getTriggerName());
		}
	}

	@Override
	public void update(float time) {
		super.update(time);
		
	}
	
}
