/*
 * Java GPX Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */
package jpx;

import static java.util.Objects.requireNonNull;
import static jpx.Parsers.parseString;
import static jpx.XMLReader.attr;

import java.io.Serializable;
import java.util.function.Function;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * An email address. Broken into two parts (id and domain) to help prevent email
 * harvesting.
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version 1.0
 * @since 1.0
 */
public final class Email implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String _id;
	private final String _domain;

	/**
	 * Create a new {@code Email} object with the given {@code id} and
	 * {@code domain}.
	 *
	 * @param id id half of email address (billgates2004)
	 * @param domain domain half of email address (hotmail.com)
	 * @throws NullPointerException if one of the argument is {@code null}
	 */
	private Email(final String id, final String domain) {
		_id = requireNonNull(id);
		_domain = requireNonNull(domain);
	}

	/**
	 * Return the id half of the email address.
	 *
	 * @return the id half of the email address
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Return the domain half of the email address.
	 *
	 * @return the domain half of the email address
	 */
	public String getDomain() {
		return _domain;
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash += 17*_id.hashCode() + 31;
		hash += 17*_domain.hashCode() + 31;
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Email &&
			((Email)obj)._id.equals(_id) &&
			((Email)obj)._domain.equals(_domain);
	}

	@Override
	public String toString() {
		return _id + "@" + _domain;
	}


	/* *************************************************************************
	 *  Static object creation methods
	 * ************************************************************************/

	/**
	 * Create a new {@code Email} object with the given {@code id} and
	 * {@code domain}.
	 *
	 * @param id id half of email address (billgates2004)
	 * @param domain domain half of email address (hotmail.com)
	 * @return a new {@code Email} object with the given values
	 * @throws NullPointerException if one of the argument is {@code null}
	 */
	public static Email of(final String id, final String domain) {
		return new Email(id, domain);
	}


	/* *************************************************************************
	 *  XML stream object serialization
	 * ************************************************************************/

	/**
	 * Writes this {@code Link} object to the given XML stream {@code writer}.
	 *
	 * @param writer the XML data sink
	 * @throws XMLStreamException if an error occurs
	 */
	void write(final XMLStreamWriter writer) throws XMLStreamException {
		final XMLWriter xml = new XMLWriter(writer);

		xml.write("email",
			xml.attr("id", _id),
			xml.attr("domain", _domain)
		);
	}

	static XMLReader<Email> reader() {
		final Function<Object[], Email> creator = a -> Email.of(
			parseString(a[0]), parseString(a[1])
		);

		return XMLReader.of(creator, "email",
			attr("id"),
			attr("domain")
		);
	}

}