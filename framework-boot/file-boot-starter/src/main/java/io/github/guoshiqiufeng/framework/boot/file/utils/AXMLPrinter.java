package io.github.guoshiqiufeng.framework.boot.file.utils;

import android.content.res.AXmlResourceParser;

import java.io.InputStream;

@SuppressWarnings("all")
public class AXMLPrinter {

	private static final float[] RADIX_MULTS = { 0.0039063F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };

	private static final String[] DIMENSION_UNITS = { "px", "dip", "sp", "pt", "in", "mm", "", "" };

	private static final String[] FRACTION_UNITS = { "%", "%p", "", "", "", "", "", "" };

	private StringBuffer buf;

	public AXMLPrinter() {
		buf = new StringBuffer();
	}

	public StringBuffer getBuf() {
		return buf;
	}

	public void setBuf(StringBuffer buf) {
		this.buf = buf;
	}

	public void startPrinf(InputStream stream) {
		try {
			AXmlResourceParser parser = new AXmlResourceParser();
			parser.open(stream);
			StringBuilder indent = new StringBuilder(10);
			do {
				int type = parser.next();
				if (type == 1) {
					break;
				}
				switch (type) {
				// '\0'
				case 0:
					log("<?xml version=\"1.0\" encoding=\"utf-8\"?>", new Object[0]);
					break;
				// '\002'
				case 2:
					log("%s<%s%s", new Object[] { indent, getNamespacePrefix(parser.getPrefix()), parser.getName() });
					indent.append("\t");
					int namespaceCountBefore = parser.getNamespaceCount(parser.getDepth() - 1);
					int namespaceCount = parser.getNamespaceCount(parser.getDepth());
					for (int i = namespaceCountBefore; i != namespaceCount; i++) {
						log("%sxmlns:%s=\"%s\"",
								new Object[] { indent, parser.getNamespacePrefix(i), parser.getNamespaceUri(i) });
					}

					for (int i = 0; i != parser.getAttributeCount(); i++) {
						log("%s%s%s=\"%s\"", new Object[] { indent, getNamespacePrefix(parser.getAttributePrefix(i)),
								parser.getAttributeName(i), getAttributeValue(parser, i) });
					}

					log("%s>", new Object[] { indent });
					break;
				// '\003'
				case 3:
					indent.setLength(indent.length() - "\t".length());
					log("%s</%s%s>", new Object[] { indent, getNamespacePrefix(parser.getPrefix()), parser.getName() });
					break;
				// '\004'
				case 4:
					log("%s%s", new Object[] { indent, parser.getText() });
					break;
				default:
				}
			}
			while (true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getNamespacePrefix(String prefix) {
		if (prefix == null || prefix.length() == 0) {
			return "";
		}
		else {
			return (new StringBuilder(String.valueOf(prefix))).append(":").toString();
		}
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == 3) {
			return parser.getAttributeValue(index);
		}
		if (type == 2) {
			return String.format("?%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
		}
		if (type == 1) {
			return String.format("@%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
		}
		if (type == 4) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == 17) {
			return String.format("0x%08X", new Object[] { Integer.valueOf(data) });
		}
		if (type == 18) {
			return data == 0 ? "false" : "true";
		}
		if (type == 5) {
			return (new StringBuilder(String.valueOf(Float.toString(complexToFloat(data)))))
					.append(DIMENSION_UNITS[data & 0xf]).toString();
		}
		if (type == 6) {
			return (new StringBuilder(String.valueOf(Float.toString(complexToFloat(data)))))
					.append(FRACTION_UNITS[data & 0xf]).toString();
		}
		if (type >= 28 && type <= 31) {
			return String.format("#%08X", new Object[] { Integer.valueOf(data) });
		}
		if (type >= 16 && type <= 31) {
			return String.valueOf(data);
		}
		else {
			return String.format("<0x%X, type 0x%02X>", new Object[] { Integer.valueOf(data), Integer.valueOf(type) });
		}
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		else {
			return "";
		}
	}

	private void log(String format, Object[] arguments) {
		buf.append(String.format(format, arguments));
		buf.append("\n");
	}

	public static float complexToFloat(int complex) {
		return (float) (complex & 0xffffff00) * RADIX_MULTS[complex >> 4 & 3];
	}

}
