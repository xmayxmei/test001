package com.furtune.server.util;

import java.util.Iterator;

/**
 * The <code>IMap</code> is simply the interface for putting and getting the hashmap value in chaining way.
 * 
 * @param <K> The key type
 * @param <V> The value type.
 * 
 */
public interface IMap<K, V> {	
	/**
	 * Put the key-value pair into the map
	 * 
	 * @param oKey The key
	 * @param oVal The value 
	 * @return the map itself
	 * 
	 */
	IMap<K,V> put(K oKey, V oVal);
	/**
	 * Get the value based on the <code>oKey</code>
	 * 
	 * @param oKey The key
	 * @return The value if found, null otherwise.
	 * 
	 */
	V get(K oKey);
	/**
	 * Get the value based on the <code>oKey</code>
	 * 
	 * @param oKey The key
	 * @return The value if found, null otherwise.
	 * 
	 */
	<T> T getAs(K oKey);
	/**
	 * Get the value based on the <code>oKey</code>.<br>
	 * Return the default value specified by <code>defVal</code>, if the value is null.
	 * 
	 * @param oKey The key
	 * @param defVal The default value
	 * @return The value if found, default value otherwise.
	 * 
	 */
	<T> T getAs(K oKey, T defVal);
	/**
	 * Similar to {@link #getAs(Object)}, except it throws {@link IllegalArgumentException} when the value 
	 * of the specified key <code>oKey</code> is missing.
	 * 
	 * @param oKey The key
	 * @return The value if found, default value otherwise.
	 * 
	 * @see #get(Object)
	 */
	<T> T require(K oKey);
	
	Iterator<K> keys();	
	/**
	 * Return whether the map is empty or not.
	 * 
	 * @return Return whether the map is empty or not.
	 */
	boolean isEmpty();
	/**
	 * Return the copy of the map. Note that the key and value are shadow copy only
	 */
	IMap<K, V> copy();
}
