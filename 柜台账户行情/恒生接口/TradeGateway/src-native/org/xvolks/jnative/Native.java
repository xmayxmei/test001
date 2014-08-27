package org.xvolks.jnative;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;


/**
 * Denotes a method or member variable used by native side  <p>
 * so do not refactor it unless you also refactor native side</p>
 *
 * @author marc 17/05/2007
 *
 */
@java.lang.annotation.Target(
{ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR} )
@SuppressWarnings("unused")
@Inherited
public @interface Native
{
	
}
