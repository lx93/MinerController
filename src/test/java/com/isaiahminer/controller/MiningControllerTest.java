package com.isaiahminer.controller;

import org.junit.Test;

import com.isaiahminer.models.MiningController;
import com.isaiahminer.controllers.MiningTabController;

import static org.junit.Assert.*;

import java.io.IOException;

public class MiningControllerTest {

	@Test public void testReturnBalance() {
    		final MiningController miningController = new MiningController(null);
        assertEquals("Value for the Debug mode", "50000.00", miningController.returnBalance());
    }

	@Test public void testReturnHashrate() {
		final MiningController miningController = new MiningController(null);
		assertEquals("Value for the Debug mode", "8000", miningController.returnHashrate());
	}

	@Test public void testJonParse() {
		final MiningController miningController = new MiningController(null);
		final String sampleJson = "{\"status\":true,\"data\":{\"hashrate\":8000,\"balance\":50}}";
		assertEquals("balance", "50", miningController.jsonParse(sampleJson, "balance"));
		assertEquals("hashrate", "8000", miningController.jsonParse(sampleJson, "hashrate"));
	}

	@Test public void testClaymoreStarter() throws IOException {
		final MiningController miningController = new MiningController(new MiningTabController());
		miningController.claymoreStarter();
		try { Thread.sleep(300000); } catch (InterruptedException e) {}
	}
}
