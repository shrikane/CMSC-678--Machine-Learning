using System;
using System.Collections.Generic;

public abstract class Shape : IComparable, IComparable<Shape>
{
    public abstract double Area( );
    public abstract double Perimeter( );

    public int CompareTo( object rhs )
    {
        return CompareTo( (Shape) rhs );
    }

    public int CompareTo( Shape rhs )
    {
        double diff = Area( ) - rhs.Area( );
        if( diff == 0 )
            return 0;
        else if( diff < 0 )
            return -1;
        else
            return 1;
    }

    public bool Equals( Shape rhs )
    {
        return Area( ) == rhs.Area( );
    }

    public double Semiperimeter( )
    {
        return Perimeter( ) / 2;
    }
}

public class Circle : Shape, IComparable<Circle>
{
    public Circle( double rad )
    {
        radius = rad;
    }

    public int CompareTo( Circle rhs )
    {
        return CompareTo( (Shape) rhs );
    }

    public bool Equals( Circle rhs )
    {
        return Equals( (Shape) rhs );
    }
    public override double Area( )
    {
        return Math.PI * radius * radius;
    }

    public override double Perimeter( )
    {
        return 2 * Math.PI * radius;
    }

    public override string ToString( )
    {
        return "Circle: " + radius;
    }

    private double radius;
}

public class Rectangle : Shape, IComparable<Rectangle>
{
    public Rectangle( double len, double wid )
    {
        length = len; width = wid;
    }

    public int CompareTo( Rectangle rhs )
    {
        return CompareTo( (Shape) rhs );
    }

    public bool Equals( Rectangle rhs )
    {
        return Equals( (Shape) rhs );
    }

    public override double Area( )
    {
        return length * width;
    }

    public override double Perimeter( )
    {
        return 2 * ( length + width );
    }

    public override string ToString( )
    {
        return "Rectangle: " + length + " " + width;
    }

    public double GetLength( )
    {
        return length;
    }

    public double GetWidth( )
    {
        return width;
    }

    private double length;
    private double width;
}

public class Square : Rectangle, IComparable<Square>
{
    public Square( double side ) : base( side, side )
    {
    }

    public int CompareTo( Square rhs )
    {
        return CompareTo( (Shape) rhs );
    }

    public bool Equals( Square rhs )
    {
        return Equals( (Shape) rhs );
    }

    public override string ToString( )
    {
        return "Square: " + GetLength( );
    }

}

public class GenericFindMaxDemo
{
    // Return max item in a.
    // Precondition: a.Length > 0
    public static AnyType findMax<AnyType>( AnyType[ ] a ) where AnyType : IComparable<AnyType>
    {
        int maxIndex = 0;

        for( int i = 1; i < a.Length; i++ )
            if( a[ i ].CompareTo( a[ maxIndex ] ) > 0 )
                maxIndex = i;

        return a[ maxIndex ];
    }

    // Test findMax on Shape and String objects.
    // (Integer is discussed in Chapter 4).
    public static void Main( string[ ] args )
    {
        Shape[ ] sh1 = { new Circle(  2.0 ),
                        new Square(  3.0 ),
                        new Rectangle( 3.0, 4.0 ) };

        Circle[ ] c1 = { new Circle(  2.0 ),
                        new Circle(  3.0 ),
                        new Circle( 1.0 ) };

        string[ ] st1 = { "Joe", "Bob", "Bill", "Zeke" };
        int[ ] i1 = { 1, 4, 2, 3 };

        Shape maxShape = findMax( sh1 );
        string maxString = findMax( st1 );
        Circle maxCircle = findMax( c1 );
        int maxInt = findMax( i1 );

        Console.WriteLine( maxShape );
        Console.WriteLine( maxString );
        Console.WriteLine( maxCircle );
        Console.WriteLine( maxInt );
    }
}

