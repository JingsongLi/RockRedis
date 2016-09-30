
package com.jslee.rockredis.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringHelper {

	private static final int HIGH_BIT = 0x1 << 7;

	public static String readString(DataInput in) throws IOException {
		// the length we read is offset by one, because a length of zero indicates a null value
		int len = in.readUnsignedByte();
		
		if (len == 0) {
			return null;
		}

		if (len >= HIGH_BIT) {
			int shift = 7;
			int curr;
			len = len & 0x7f;
			while ((curr = in.readUnsignedByte()) >= HIGH_BIT) {
				len |= (curr & 0x7f) << shift;
				shift += 7;
			}
			len |= curr << shift;
		}
		
		// subtract one for the null length
		len -= 1;
		
		final char[] data = new char[len];

		for (int i = 0; i < len; i++) {
			int c = in.readUnsignedByte();
			if (c < HIGH_BIT) {
				data[i] = (char) c;
			} else {
				int shift = 7;
				int curr;
				c = c & 0x7f;
				while ((curr = in.readUnsignedByte()) >= HIGH_BIT) {
					c |= (curr & 0x7f) << shift;
					shift += 7;
				}
				c |= curr << shift;
				data[i] = (char) c;
			}
		}
		
		return new String(data, 0, len);
	}

	public static final void writeString(CharSequence cs, DataOutput out) throws IOException {
		if (cs != null) {
			// the length we write is offset by one, because a length of zero indicates a null value
			int lenToWrite = cs.length()+1;
			if (lenToWrite < 0) {
				throw new IllegalArgumentException("CharSequence is too long.");
			}
	
			// write the length, variable-length encoded
			while (lenToWrite >= HIGH_BIT) {
				out.write(lenToWrite | HIGH_BIT);
				lenToWrite >>>= 7;
			}
			out.write(lenToWrite);
	
			// write the char data, variable length encoded
			for (int i = 0; i < cs.length(); i++) {
				int c = cs.charAt(i);
	
				while (c >= HIGH_BIT) {
					out.write(c | HIGH_BIT);
					c >>>= 7;
				}
				out.write(c);
			}
		} else {
			out.write(0);
		}
	}
	
	public static final void copyString(DataInput in, DataOutput out) throws IOException {
		int len = in.readUnsignedByte();
		out.writeByte(len);

		if (len >= HIGH_BIT) {
			int shift = 7;
			int curr;
			len = len & 0x7f;
			while ((curr = in.readUnsignedByte()) >= HIGH_BIT) {
				out.writeByte(curr);
				len |= (curr & 0x7f) << shift;
				shift += 7;
			}
			out.writeByte(curr);
			len |= curr << shift;
		}

		// note that the length is one larger than the actual length (length 0 is a null string, not a zero length string)
		len--;

		for (int i = 0; i < len; i++) {
			int c = in.readUnsignedByte();
			out.writeByte(c);
			while (c >= HIGH_BIT) {
				c = in.readUnsignedByte();
				out.writeByte(c);
			}
		}
	}
}
