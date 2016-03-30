package com.kingsun.core.Test;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.regex.*;

public class TCPNioClient {
	// The standard daytime port
	private static int DAYTIME_PORT = 9920;

	// The port we'll actually use
	private static int port = DAYTIME_PORT;

	// Charset and decoder for US-ASCII
	private static Charset charset = Charset.forName("utf-8");
	private static CharsetDecoder decoder = charset.newDecoder();

	// Direct byte buffer for reading
	private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

	// Ask the given host what time it is
	//
	private static void query(String host) throws IOException {
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(host), port);
		SocketChannel sc = null;
		try {
			// Connect
			sc = SocketChannel.open();
			sc.connect(isa);
			ByteBuffer writeBuffer=ByteBuffer.wrap("something".getBytes("UTF-8"));
		    sc.write(writeBuffer);

			// Read the time from the remote host. For simplicity we assume
			// that the time comes back to us in a single packet, so that we
			// only need to read once.
			dbuf.clear();
			sc.read(dbuf);

			// Print the remote address and the received time
			dbuf.flip();
			CharBuffer cb = decoder.decode(dbuf);
			System.out.print(isa + " : " + cb);

		} finally {
			// Make sure we close the channel (and hence the socket)
			if (sc != null)
				sc.close();
		}
	}

	public static void main(String[] args) {

			String host = "10.10.38.92";
			try {
				query(host);
			} catch (IOException x) {
				x.printStackTrace();
				System.err.println(host + ": " + x);
			}
		}
	}
