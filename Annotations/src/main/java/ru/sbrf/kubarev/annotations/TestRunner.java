package ru.sbrf.kubarev.annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static java.lang.Class.*;

public class TestRunner {

    public static void ImplementTest(String classname)
    {
        try {
            Class clt = forName(classname);

            Method met[] = clt.getDeclaredMethods();
            String output = "";
            for (Method m : met)
            {
                if (m.isAnnotationPresent(Test.class))
                {
                    Constructor constructor = clt.getConstructor();
                    Object obj = constructor.newInstance();
                    for (Method mbef : met)
                    {
                        if (mbef.isAnnotationPresent(Before.class))
                            try {
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

                    for (Method maft : met)
                    {
                        if (maft.isAnnotationPresent(After.class))
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
            }
            System.out.println("Test results: ");
            System.out.println(output);

        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
