package org.erehwon.shadowlands.utils;

public class SimpleStringScanner {

	char[] text;
	int idx;
	int len;

	// constructors
	public SimpleStringScanner() {
		text = null;
		idx = -1;
		len = 0;
	}

	public SimpleStringScanner(String inp) {
		text = inp.toCharArray();
		idx = 0;
		len = text.length;
	}

	// methods
	public boolean hasNextChar() {
		return idx < len;
	}

	public char nextChar() {
		return text[idx++];
	}

	public boolean hasNext() {
		while (hasNextChar() && (String.valueOf(text[idx]).matches("\\s"))) {
			idx++;
		}
		// positioned at non white space or end
		if (idx >= len)
			return false;
		return (!String.valueOf(text[idx]).matches("\\s"));

	}

	public String next() {
		StringBuilder bldr = new StringBuilder();
		char ch;
		if (hasNextChar()) {
			ch = nextChar();
			while (String.valueOf(ch).matches("\\s") && hasNextChar()) {
				ch = nextChar();
			}
			while (!String.valueOf(ch).matches("\\s") && hasNextChar()) {
				bldr.append(ch);
				ch = nextChar();
			}
		}
		return bldr.toString();
	}

	public String lookAhead1() {
		StringBuilder bldr = new StringBuilder();
		if (hasNext()) {
			bldr.append(String.valueOf(text[idx]));
		}
		return bldr.toString();
	}

	public String nextPattern(String pat) {
		StringBuilder bldr = new StringBuilder();
		char ch;
		if (hasNextChar()) {
			ch = nextChar();
			while (String.valueOf(ch).matches("\\s") && hasNextChar()) {
				ch = nextChar();
			}
			while (String.valueOf(ch).matches(pat) && hasNextChar()) {
				bldr.append(ch);
				ch = lookAhead1().charAt(0);
				if (String.valueOf(ch).matches(pat)) {
					nextChar();
				}
			}
			if ((String.valueOf(ch).matches(pat) && !hasNextChar())) {
				// capture the end character
				bldr.append(ch);
			}
		}
		String s = bldr.toString();
		return s;
	}

	public int nextInt() {
		String s = nextNumeric();
		if (s.isEmpty())
			return -1;
		return Integer.parseInt(s);
	}

	public String nextNumeric() {
		String sPat = "[0-9]";
		return nextPattern(sPat);
	}

	public String nextAlpha() {
		String sPat = "[a-zA-Z]";
		return nextPattern(sPat);
	}

	public String nextAlphaNum() {
		String sPat = "[\\da-zA-Z]";
		return nextPattern(sPat);
	}

	public String nextAlphaExt(String ext) {
		String sPat = "[a-zA-Z" + ext + "]";
		return nextPattern(sPat);
	}

	public int getIdx() {
		return idx;
	}

}
