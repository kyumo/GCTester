package ru.sbrf.kubarev.annotations;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TestRunner testRunner = new TestRunner("ru.sbrf.kubarev.annotations.SomeClass");
        System.out.println(testRunner.ImplementTest());
    }
}
