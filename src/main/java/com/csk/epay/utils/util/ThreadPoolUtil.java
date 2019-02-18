package com.csk.epay.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadPoolUtil {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtil.class);

    private static Map<String, BlockingQueue> queueMap = new ConcurrentHashMap<>();
    private static Map<String, ExecutorService> executorMap = new ConcurrentHashMap<>();

    static {
        for (int i = 0; i < 200; i++) {
            queueMap.put("queue_" + i, new ArrayBlockingQueue(500));
            executorMap.put("queue_" + i, new ThreadPoolExecutor(1,
                    1, 1L, TimeUnit.HOURS, new ArrayBlockingQueue<Runnable>(500)));
        }
    }

    public static ExecutorService getExecutor (String queueName) {
        return executorMap.get(queueName);
    }

    public static Map<String, ExecutorService> getExecutorsMap () {
        return executorMap;
    }

    public static BlockingQueue getueue (String queueName) {
        return queueMap.get(queueName);
    }

    public static int getServerIndex () {
        try {
            String addStr = InetAddress.getLocalHost().getHostAddress();
            int index = Math.abs(addStr.hashCode() % 6);
            logger.info("获取本机IP:{},index:{}", addStr, index);
            return index;
        } catch (UnknownHostException e) {
            logger.error("获取本机IP异常", e);
            return -1;
        }
    }

}

