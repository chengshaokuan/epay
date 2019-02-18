package com.csk.epay.utils.util;


/**
 * @program: cheng
 * @description: 雪花算法, 雪花算法只能使用69年, 再继续，前面时间戳部分就会一致，往左移动22位,超过41位的都变成了0.
 * @author: Mr.Cheng
 * @create: 2019-01-16 16:05
 **/
public class SnowFlake {

    /**
     * &与运算符，两个操作数中位都为1，结果才为1，否则结果为0
     * |或运算符，两个位只要有一个为1，那么结果就是1，否则就为0
     * ^异或运算符,两个操作数的位中，相同则结果为0，不同则结果为1
     */

    //起始的时间戳,这个是随意的1288834974657L
    private final static long START_STMP = 1288834974657L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数12
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    /**
     * @Description:
     * @param: datacenterId 数据中心的ID
     * @param: machineId 机器号的ID
     * @return:
     * @Author: Mr.Cheng
     * @Date:
     */
    public SnowFlake (long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * @Description: 产生下一个ID
     * @param:
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 16:06 2019/1/16
     */
    public synchronized long nextId () {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.Refusing to generate id");
        }
        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastStmp = currStmp;
        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                          //序列号部分
    }

    /**
     * @Description: 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     * @param:
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 17:28 2019/1/17
     */
    private long getNextMill () {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    /**
     * @Description: 获取时间戳
     * @param:
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 11:24 2019/1/18
     */
    private long getNewstmp () {
        return System.currentTimeMillis();
    }

    public static void main (String[] args) {

        Long sequence = 0L;
        SnowFlake snowFlake = new SnowFlake(0, 0);
        System.out.println(sequence);
    }
}
