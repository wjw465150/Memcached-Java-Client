/**
 * Copyright (c) 2008 Greg Whalin
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the BSD license
 *
 * This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 *
 * You should have received a copy of the BSD License along with this
 * library.
 *
 * @author greg whalin <greg@meetup.com> 
 */
package com.whalin.MemCached.test;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class TestMemcached {
	public static void main(String[] args) {
		// memcached should be running on port 11211 but NOT on 11212

		String[] servers = { "localhost:11111" };
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setFailover(true);
		pool.setInitConn(10);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		// pool.setMaintSleep( 30 );
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setAliveCheck(true);
		pool.initialize();

		MemCachedClient mcc = new MemCachedClient();

		// turn off most memcached client logging:
		// Logger.getLogger( MemCachedClient.class.getName() ).setLevel(
		// com.schooner.MemCached.Logger. );

		for (int i = 0; i < 10; i++) {
			boolean success = mcc.set("" + i, "Hello!");
			String result = (String) mcc.get("" + i);
			System.out.println(String.format("set( %d ): %s", i, success));
			System.out.println(String.format("get( %d ): %s", i, result));
		}

		System.out.println("\n\t -- sleeping --\n");
		try {
			Thread.sleep(10000);
		} catch (Exception ex) {
		}

		for (int i = 0; i < 10; i++) {
			boolean success = mcc.set("" + i, "Hello!");
			String result = (String) mcc.get("" + i);
			System.out.println(String.format("set( %d ): %s", i, success));
			System.out.println(String.format("get( %d ): %s", i, result));
		}
	}
}
