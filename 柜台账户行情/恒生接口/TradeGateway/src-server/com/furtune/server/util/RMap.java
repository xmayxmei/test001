package com.furtune.server.util;

import java.util.HashMap;
import java.util.Iterator;

/**
 * <code>Map</code>
 */
public class RMap {
	
	public static <K,V> IMap<K,V> create(){
		return new MapAdaptor<K,V>();
	}
	public static <V> IMap<String,V> build(){
		return new MapAdaptor<String,V>();
	}
	@SuppressWarnings("unchecked")
	public static class MapAdaptor<K, V> implements IMap<K,V> {
		private java.util.Map<K, V> m_hMap = new HashMap<K, V>(3);
		
		@Override
		public IMap<K, V> put(K oKey, V oVal) {
			this.m_hMap.put(oKey, oVal);
			return this;
		}				
		public V get(K oKey){
			return this.m_hMap.get(oKey);
		}
		public <T> T getAs(K oKey){
			return (T) this.m_hMap.get(oKey);
		}
		
		public <T> T getAs(K oKey, T defVal){
			Object obj = this.m_hMap.get(oKey);
			if (obj == null) {
				return defVal;
			}
			return (T)obj;
		}		
		public <T> T require(K oKey){
			Object obj = this.m_hMap.get(oKey);
			if (obj == null) {
				throw new MissingArgumentException(oKey.toString());
			} 
			return (T)obj;
		}		
		@Override
		public boolean isEmpty() {
			return this.m_hMap.isEmpty();
		}
		@Override
		public Iterator<K> keys() {
			return this.m_hMap.keySet().iterator();
		}
		public IMap<K, V> copy(){
			MapAdaptor<K, V> h = new MapAdaptor<K, V>();
			h.m_hMap.putAll(this.m_hMap);
			return h;
		}
		public String toString(){
			return this.m_hMap.toString();
		}
	}
	
	/**
	 * <code>MissingArgumentException</code>
	 */
	public static class MissingArgumentException extends RuntimeException{
		private static final long serialVersionUID = 1L;
		private String m_sMissedArgument;
		
		public MissingArgumentException(String sMissedArgument) {
			super("Missing argument '" + sMissedArgument + "' in the parameter map");
			m_sMissedArgument = sMissedArgument;
		}
		
		public String getMissedArgument() {
			return m_sMissedArgument;
		}
	}
}
