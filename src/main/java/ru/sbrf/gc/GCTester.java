package ru.sbrf.gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.*;

public class GCTester {
    public static void main( String[] args ) throws InterruptedException
    {
/*
-Xms256m
-Xmx256m
 */
        System.out.println("Start " + System.currentTimeMillis());
        switchOnMonitoring();
        ArrayList<String> list = new ArrayList<>(1);
        for (int i =0,j=0;i<1000*1000*16;i++) {
            list.add(new String("0"));
            if (i%2==0) {
                list.remove(j++);
            }
            if (i%1000 ==0)
                Thread.sleep(10);
            if (i%100000 ==0)
                System.out.println(i+";"+ System.currentTimeMillis());
        }
        System.out.println( "Done " );
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for ( GarbageCollectorMXBean gcbean : gcbeans ) {
            System.out.println( "GC name:" + gcbean.getName() );
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback ) -> {
                if ( notification.getType().equals( GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION ) ) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from( (CompositeData) notification.getUserData() );
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();
                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();
                    //System.out.println( "start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)" );
                    System.out.println( startTime + ";" + gcName + ";" + gcAction + ";" + gcCause + ";" + duration + ";" +  System.currentTimeMillis());
                }
            };
            emitter.addNotificationListener( listener, null, null );
        }
    }
}