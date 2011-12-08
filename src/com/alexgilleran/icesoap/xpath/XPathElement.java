package com.alexgilleran.icesoap.xpath;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XPathElement {
	private String name;
	private Map<String, String> predicates = new HashMap<String, String>();

	public XPathElement(String element) {
		this.name = element;
	}

	public void addPredicate(String name, String value) {
		predicates.put(name, value);
	}

	public boolean matches(XPathElement otherElement) {
		if (!this.name.equals(otherElement.name)) {
			return false;
		}

		for (String predicateKey : predicates.keySet()) {
			if (!otherElement.predicates.containsKey(predicateKey)) {
				return false;
			}

			if (!this.predicates.get(predicateKey).equals(
					otherElement.predicates.get(predicateKey))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder().append(name);

		if (!predicates.isEmpty()) {
			builder.append("[");

			Iterator<String> it = predicates.keySet().iterator();

			while (it.hasNext()) {
				String key = it.next();
				builder.append("@").append(key).append("=")
						.append(predicates.get(key));

				if (it.hasNext()) {
					builder.append(" and ");
				}
			}

			builder.append("]");
		}

		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((predicates == null) ? 0 : predicates.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XPathElement other = (XPathElement) obj;
		if (predicates == null) {
			if (other.predicates != null)
				return false;
		} else if (!predicates.equals(other.predicates))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
