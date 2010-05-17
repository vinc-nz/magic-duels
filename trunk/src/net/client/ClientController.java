package net.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.NetForwarderController;

import core.fight.Fight;

public class ClientController extends NetForwarderController {
	DataOutputStream forwarder;

	public ClientController(int id, Fight fight, OutputStream os) {
		super(id, fight);
		forwarder = new DataOutputStream(os);
	}
	
	@Override
	public void forward(String trigger) {
		try {
			forwarder.writeBytes(trigger + "\n");
		} catch (IOException e) {
			System.out.println("errore ClientController");
			e.printStackTrace();
			System.exit(4);
		}
	}

}
