package org.example.model;

import java.util.List;

public class HedgedRequestData {
	public record ServerNode(String name, long latencyMs, boolean shouldFail) {
	}

	public record QuoteResult(String serverName, double price, long responseTimeMs) {
	}

	public static List<ServerNode> getSimulatedNodes() {
		return List.of(
				new ServerNode("Node-Alpha (EE.UU)", 150, false),
				new ServerNode("Node-Beta (Europa)", 40, false),
				new ServerNode("Node-Gamma (Asia)", 300, true)
		);
	}
}
