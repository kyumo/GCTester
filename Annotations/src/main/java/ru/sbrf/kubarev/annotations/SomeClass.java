package ru.sbrf.kubarev.annotations;

public class SomeClass {


    @Test
    public void MethodForTest1()
    {
        System.out.println("MethodForTest1 in SomeClass");
    }

    @Test
    public void MethodForTest2()
    {
        System.out.println("MethodForTest2 in SomeClass");
    }

    @Before
    public void Method2()
    {
        System.out.println("Method2 in SomeClass");
        throw (new RuntimeException());
    }

    @After
    public void Method3()
    {
        System.out.println("Method3 in SomeClass");
    }
}
