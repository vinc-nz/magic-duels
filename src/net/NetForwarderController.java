package net;

import java.io.OutputStream;
import java.io.PrintWriter;

import core.Fight;

import input.CharacterController;


/*
 * controller che inoltra in rete i trigger
 */
public class NetForwarderController extends CharacterController {
	PrintWriter forwarder;
	
	public NetForwarderController(short id, Fight fight, OutputStream os) {
		super(id,fight);
		forwarder = new PrintWriter(os);
	}
	
	@Override
	public void performAction(String trigger) {
		forwarder.println(trigger);
		super.performAction(trigger);
	}

}
