package minesweeper;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;


/**
 * Used in place of private or public, as a reminder.
 * 
 * @author SeiJ
 */
@Target({ FIELD, METHOD })
public @interface Package {
	// Nothing
}
