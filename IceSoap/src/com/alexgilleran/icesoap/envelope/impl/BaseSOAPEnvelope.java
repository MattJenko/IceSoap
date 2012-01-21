package com.alexgilleran.icesoap.envelope.impl;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLNode;
import com.alexgilleran.icesoap.xml.XMLParentNode;
import com.alexgilleran.icesoap.xml.impl.XMLParentNodeImpl;

/**
 * Concrete implementation of {@link SOAPEnvelope}. Automatically sets up the
 * basic namespaces, <envelope> tags etc, as well as creating a head and body
 * tag to be manipulated by decorators.
 * 
 * Note that this is <i>not</i> an abstract class - when you're creating a new
 * envelope, you can either extend this class to keep your envelope's logic
 * contained within its own class, or instantiate a new instance of this class
 * and build it up using public methods.
 * 
 * @author Alex Gilleran
 * 
 */
public class BaseSOAPEnvelope extends XMLParentNodeImpl implements SOAPEnvelope {
	/** The SOAP header element */
	private XMLParentNode header;
	/** The SOAP body element */
	private XMLParentNode body;

	/**
	 * Initialises the class - sets up the basic "soapenv", "soapenc", "xsd" and
	 * "xsi" namespaces present in all SOAP messages
	 */
	public BaseSOAPEnvelope() {
		super(NODE_NAMESPACE, NODE_NAME);

		this.declarePrefix(NS_PREFIX_SOAPENV, NS_URI_SOAPENV);
		this.declarePrefix(NS_PREFIX_SOAPENC, NS_URI_SOAPENC);
		this.declarePrefix(XMLNode.NS_PREFIX_XSD, XMLNode.NS_URI_XSD);
		this.declarePrefix(XMLNode.NS_PREFIX_XSI, XMLNode.NS_URI_XSI);

		header = this.addParentNode(NS_URI_SOAPENV, "Header");
		body = this.addParentNode(NS_URI_SOAPENV, "Body");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLParentNode getHeader() {
		return header;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLParentNode getBody() {
		return body;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(XmlSerializer cereal)
			throws IllegalArgumentException, IllegalStateException, IOException {
		cereal.startDocument(ENCODING_UTF8, true);

		super.serialize(cereal);

		cereal.endDocument();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseSOAPEnvelope other = (BaseSOAPEnvelope) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		return true;
	}
}