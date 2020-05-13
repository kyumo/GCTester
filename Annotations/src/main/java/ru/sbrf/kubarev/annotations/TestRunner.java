package ru.sbrf.kubarev.annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static java.lang.Class.*;

public class TestRunner {

    private ArrayList<Method> beforeMethods;
    private ArrayList<Method> afterMethods;
    private ArrayList<Method> testMethods;
    Class clt;

    public TestRunner(String classname)
    {
        try {
            beforeMethods = new ArrayList<>();
            afterMethods = new ArrayList<>();
            testMethods = new ArrayList<>();
            clt = forName(classname);
            for (Method m : clt.getDeclaredMethods())
            {
                if (m.isAnnotationPresent(Before.class))
                    beforeMethods.add(m);
                if (m.isAnnotationPresent(After.class))
                    afterMethods.add(m);
                if (m.isAnnotationPresent(Test.class))
                    testMethods.add(m);
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public String ImplementTest()
    {

        try {
            String output = "";
            for (Method m : testMethods)
            {
                    Constructor constructor = clt.getConstructor();
                    Object obj = constructor.newInstance();
                    for (Method mbef : beforeMethods)
                    {
                        try{
                                mbef.invoke(obj);
                                output += mbef.getName() + " 'Before' invoked succefully \r\n";
                            }
                        catch (Exception e)
                        {
                            output += mbef.getName() + " 'Before' error: " + e.toString() +"\r\n";
                        }
                    }
                    try {
                        m.invoke(obj);
                        output += m.getName() + " 'Test' invoked succefully \r\n";
                    }
                    catch (Exception e)
                    {
                        output += m.getName() + " 'Test' error " + e.toString() +"\r\n";
                    }
                    for (Method maft : afterMethods)
                    {
                            try {
                                maft.invoke(obj);
                                output += maft.getName() + " 'After' invoked succefully \r\n";
                            }
                            catch (Exception e)
                            {
                                output += maft.getName() + " 'After' error " + e.toString() +"\r\n";
                            }
                    }
                }
            return output;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
