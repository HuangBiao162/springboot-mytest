package normal.util;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


/**
 * <p>
 * 封装各种生成唯一性ID算法的工具类.
 * <p>
 * 在分布式系统中，需要生成全局UID的场合还是比较多的，twitter的snowflake解决了这种需求，
 * 实现也还是很简单的，除去配置信息，核心代码就是毫秒级时间41位+机器ID 10位+毫秒内序列12位。
 * 该项目地址为：https://github.com/twitter/snowflake是用Scala实现的。
 * python版详见开源项目https://github.com/erans/pysnowflake。
 * </p>
 * @author
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdUtils {

    /**
     * 根据具体机器环境提供
     */
    private final static long WORKER_ID;

    /**
     * 根据具体机器环境提供
     */
    private final static long DATA_CENTER_ID;

    /**
     * 滤波器,使时间变小,生成的总位数变小,一旦确定不能变动
     */
    private final static long TWEPOCH = 1361753741828L;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 机器id所占的位数 */
    private final static long WORKER_ID_BITS = 5L;

    /** 数据标识id所占的位数 */
    private final static long DATA_CENTER_ID_BITS = 5L;

    /** 支持的最大机器id，结果是31 */
    private final static long MAX_WORKER_ID = -1L ^ -1L << WORKER_ID_BITS;

    /** 序列在id中占的位数 */
    private final static long SEQUENCE_BITS = 12L;

    /** 机器ID向左移12位 */
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final static long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;

    /** 数据标识id向左移17位(12+5) */
    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /** 时间截向左移22位(5+5+12) */
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    /**
     * 主机和进程的机器码
     */
    private static IdUtils idUtils = new IdUtils();

    /**
     * 主机和进程的机器码
     */
    private static final int GENMACHINE;

    private static String sPre = "";

    private static SecureRandom random = new SecureRandom();

    private static final Logger logger = LoggerFactory.getLogger(IdUtils.class);

    static {
        try {
            // build a 2-byte machine piece based on NICs info
            int machinePiece;
            {
                try {
                    StringBuilder sb = new StringBuilder();
                    InetAddress addr = InetAddress.getLocalHost();
                    sb.append(Integer.toHexString(addr.getHostName().hashCode()));
                    sb.append(Integer.toHexString(addr.getHostAddress().hashCode()));
                    machinePiece = sb.toString().hashCode() & 0xFFFF;
                } catch (Throwable e) {
                    logger.error(" IdGen error. ", e);
                    machinePiece = new Random().nextInt() << 16;
                }
            }

            // add a 2 byte process piece. It must represent not only the JVM
            // but the class loader.
            // Since static var belong to class loader there could be collisions
            // otherwise
            final int processPiece;
            {
                int processId = new Random().nextInt();
                try {
                    processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
                } catch (Throwable t) {
                }

                ClassLoader loader = IdUtils.class.getClassLoader();
                int loaderId = loader != null ? System.identityHashCode(loader) : 0;

                StringBuilder sb = new StringBuilder();
                sb.append(Integer.toHexString(processId));
                sb.append(Integer.toHexString(loaderId));
                processPiece = sb.toString().hashCode() & 0xFFFF;
            }
            GENMACHINE = machinePiece | processPiece;

            WORKER_ID = GENMACHINE % (IdUtils.MAX_WORKER_ID + 1);

            DATA_CENTER_ID = getDataCenterId();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static String getId() {
        return getId(sPre);
    }

    public static String getId(String sPre) {
        return String.valueOf(sPre + dateGen() + idUtils.nextId());
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (lastTimestamp == timestamp) {
            sequence = sequence + 1 & IdUtils.SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            } catch (Exception e) {
                logger.error(" IdGen error. ", e);
            }
        }
        lastTimestamp = timestamp;
        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) | (DATA_CENTER_ID << DATA_CENTER_ID_SHIFT) | (WORKER_ID << WORKER_ID_SHIFT) | sequence;
    }

    private long tilNextMillis(final long lastTimestamp1) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp1) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    private static String dateGen() {
        return DateUtils.getDate("yyMMdd");
    }

    private static Long getDataCenterId(){
        int[] ints = org.apache.commons.lang3.StringUtils.toCodePoints(SystemUtils.getHostName());
        int sums = 0;
        for (int i: ints) {
            sums += i;
        }
        return (long)(sums % 32);
    }

    public static void main(String[] args) {

        Set<String> ids = new HashSet<>();

        String id;
        for (int i = 0; i < 100; i++) {
            id = getId();
            System.out.println(id);
            ids.add(id);
            System.out.println((i +1) + " " + ids.size());
        }
    }



}