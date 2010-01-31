package net;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import core.Fight;

import input.CharacterController;


/*
 * controller che inoltra in rete i trigger
 */
public class NetForwarderController extends CharacterController {
	DataOutputStream forwarder;
	
	public NetForwarderController(short id, Fight fight, OutputStream os) {
		super(id,fight);
		forwarder = new DataOutputStream(os);
	}
	
	@Override
	public void performAction(String trigger) {
		try {
			forwarder.writeBytes(trigger + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.performAction(trigger);
	}

}
