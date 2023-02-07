package com.currency.conversion.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class CurrencyMapAlgorithm {

	/**
	 * Creating the graph for all the currencies with CAD.
	 * 
	 * @param from
	 * @param currencyMappingGraph
	 * @return
	 */
	public static Map<String, String> buildCurrencyMappingGraph(String from,
			Map<String, Map<String, Double>> currencyMappingGraph) {
		Queue<CurrencyMapping> queue = new PriorityQueue<>();

		// Adding CAD as default currency in queue.
		queue.add(new CurrencyMapping(null, from, 1.0));

		// Visitor map is created to store the max rate.
		Map<String, Double> visitorMap = new HashMap<>();

		
		// Parent map is created to store the parent of each currency.
		Map<String, String> parent = new HashMap<>();
		parent.put(from, from);
		
		// Iterating the queue and find the connection with each currency with other and placing in queue.
		while (!queue.isEmpty()) {

			CurrencyMapping edge = queue.poll();
			String top = edge.currency;

			// Keeping the max rate in visitor map.
			if (visitorMap.containsKey(top) && visitorMap.get(top) < edge.cost) {
				continue;
			}
			
			visitorMap.put(top, edge.cost);
			parent.put(top, edge.parent);
			currencyMappingGraph.get(from).put(top, edge.cost);

			// Based on currency rates queue is updating with all the currencies.
			for (var dest : currencyMappingGraph.get(top).entrySet()) {
				double rate = currencyMappingGraph.get(from).get(top) * dest.getValue();

				if (visitorMap.containsKey(dest.getKey()) 
						&& (visitorMap.get(dest.getKey()) >= rate 
							|| dest.getKey().equals(from))) {
					continue;
				}
				queue.add(new CurrencyMapping(top, dest.getKey(), rate));
			}
		}
		return parent;
	}
	
	/**
	 * Finding the path from CAD to other currencies based on parent map.
	 * 
	 * @param parent
	 * @param from
	 * @param to
	 * @return
	 */
	public static String findCurrencyPath(Map<String, String> parent, String from, String to) {
		String current = to;
		Stack<String> res = new Stack<>();
		res.add(to);
		while (parent != null && current != null && parent.containsKey(current) && !from.equals(parent.get(current))) {
			current = parent.get(current);
			res.add(current);
		}

		StringBuilder stringBuilder = new StringBuilder();
		res.add(from);

		while (res.size() != 0) {

			stringBuilder.append(res.pop());
			if (res.size() > 0) {
				stringBuilder.append("|");
			}
		}
		return stringBuilder.toString();
	}
}
