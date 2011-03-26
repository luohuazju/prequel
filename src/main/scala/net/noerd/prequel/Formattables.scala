package net.noerd.prequel

import java.util.Date

import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormatter

/**
 * Wrap your optional value in NullComparable to compare with null if None. 
 *
 * Note: The '=' operator is added during formatting so don't include it in your SQL
 */    
class NullComparable( val value: Option[ Formattable ] ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = {
        value.map( "=" + _.escaped( formatter) ).getOrElse( "is null" )
    }
}
object NullComparable {
    def apply( value: Option[ Formattable ] ) = new NullComparable( value )
}

/**
 * Wrap your optional value in Nullable to have it converted to null if None
 */
class Nullable( val value: Option[ Formattable ] ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = {
        value.map( _.escaped( formatter ) ).getOrElse( "null" )
    }
}
object Nullable {
    def apply( value: Option[ Formattable ] ) = new Nullable( value )
}

/**
 * Wrap a parameter string in an Identifier to avoid escaping
 */ 
class Identifier( val value: String ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = {
        value
    }
}
object Identifier {
    def apply( value: String ) = new Identifier( value )
}

//
// String
//
class StringFormattable( val value: String ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = {
        formatter.escapeString( value ) 
    }
}
object StringFormattable{
    def apply( value: String ) = new StringFormattable( value )
}

//
// Boolean
// 
class BooleanFormattable( val value: Boolean ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = value.toString
}
object BooleanFormattable {
    def apply( value: Boolean ) = new BooleanFormattable( value )
}

//
// Long
//
class LongFormattable( val value: Long ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = value.toString
}
object LongFormattable{
    def apply( value: Long ) = new LongFormattable( value )
}

//
// Int
//
class IntFormattable( val value: Int ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = value.toString
}
object IntFormattable{
    def apply( value: Int ) = new IntFormattable( value )
}

//
// Float
//
class FloatFormattable( val value: Float ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = "%f".format( value )
}
object FloatFormattable{
    def apply( value: Float ) = new FloatFormattable( value )
}

//
// Double
//
class DoubleFormattable( val value: Double ) extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = "%f".format( value )
}
object DoubleFormattable{
    def apply( value: Double ) = new DoubleFormattable( value )
}

//
// DateTime
//
class DateTimeFormattable( val value: DateTime ) 
extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = {
        formatter.escapeString( formatter.timeStampFormatter.print( value ) )
    }
}
object DateTimeFormattable{
    def apply( value: DateTime ) = {
        new DateTimeFormattable( value )
    }
    def apply( value: Date) = {
        new DateTimeFormattable( new DateTime( value ) )
    }
}

//
// Duration
//
/**
 * Formats an Duration object by converting it to milliseconds.
 */
class DurationFormattable( val value: Duration ) 
extends Formattable {
    override def escaped( formatter: SQLFormatter ): String = value.getMillis.toString
}
object DurationFormattable{
    def apply( value: Duration ) = new DurationFormattable( value )
}