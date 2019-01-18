package com.csk.epay.algorithm;

/**
 * Hash算法大全<br>
 * 推荐使用FNV1算法
 *
 * @author Goodzzp 2006-11-20
 * @algorithm None
 * @lastEdit Goodzzp 2006-11-20
 * @editDetail Create
 */
public class HashAlgorithms {

    /**
     * MASK值，随便找一个值，最好是质数
     */
    static int M_MASK = 0x8765fed1;
    static int M_SHIFT = 0;
    /** 
     * @Description: 32位的FNV算法 
     * @param: data
     * @return: int 
     * @Author: Mr.Cheng
     * @Date: 14:16 2019/1/18 
     */ 
    public static int FNVHash (byte[] data) {
        int hash = (int) 2166136261L;
        for (byte b : data) {
            hash = (hash * 16777619) ^ b;
        }
        if (M_SHIFT == 0) {
            return hash;
        }
        return (hash ^ (hash >> M_SHIFT)) & M_MASK;
    }

    /**
     * @Description: 改进的32位FNV算法1
     * @param: data
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 11:59 2019/1/18
     */
    public static int FNVHash1 (String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        //如果参数是数组，需要改变下面的方法,其他都不需要改变
        //for (byte b : data) {hash = (hash ^ b) * p;}
        for (int i = 0; i < data.length(); i++) {
            hash = (hash ^ data.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    /**
     * @Description: 改进的32位FNV算法1
     * @param: str
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 11:39 2019/1/18
     */
    public static long FNVHash (String str) {
        long fnv_prime = 0x811C9DC5;
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash *= fnv_prime;
            hash ^= str.charAt(i);
        }
        return hash;
    }
    /* End Of FNV Hash Function */

    /**
     * @Description: RS算法hash
     * @param: str
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 11:43 2019/1/18
     */
    public static long RSHash (String str) {
        int b = 378551;
        int a = 63689;
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = hash * a + str.charAt(i);
            a = a * b;
        }
        //返回int类型(hash & 0x7FFFFFFF);
        return hash;
    }
    /* End Of RS Hash Function */

    /**
     * @Description: JS算法
     * @param: str
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 11:45 2019/1/18
     */
    public static int JSHash (String str) {
        int hash = 1315423911;
        for (int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }

        return (hash & 0x7FFFFFFF);
    }
    /* End Of JS Hash Function */

    /**
     * @Description: PJW算法
     * @param: str
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 11:46 2019/1/18
     */
    public static int PJWHash (String str) {
        int BitsInUnsignedInt = 32;
        int ThreeQuarters = (BitsInUnsignedInt * 3) / 4;
        int OneEighth = BitsInUnsignedInt / 8;
        int HighBits = 0xFFFFFFFF << (BitsInUnsignedInt - OneEighth);
        int hash = 0;
        int test = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << OneEighth) + str.charAt(i);

            if ((test = hash & HighBits) != 0) {
                hash = ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
            }
        }
        return (hash & 0x7FFFFFFF);
    }
    /* End Of  P. J. Weinberger Hash Function */

    /**
     * @Description: ELF算法
     * @param: str
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 11:47 2019/1/18
     */
    public static int ELFHash (String str) {
        int hash = 0;
        int x = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = (int) (hash & 0xF0000000L)) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }
        return (hash & 0x7FFFFFFF);
    }
    /* End Of ELF Hash Function */

    /**
     * BKDR算法
     */
    public static int BKDRHash (String str) {
        int seed = 131; // 31 131 1313 13131 131313 etc..
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * seed) + str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }
    /* End Of BKDR Hash Function */

    /**
     * @Description: SDBM算法
     * @param: str
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 11:48 2019/1/18
     */
    public static int SDBMHash (String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return (hash & 0x7FFFFFFF);
    }
    /* End Of SDBM Hash Function */

    /**
     * DJB算法
     */
    public static int DJBHash (String str) {
        int hash = 5381;
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) + hash) + str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }
    /* End Of DJB Hash Function */

    /**
     * @Description: DEK算法
     * @param: str
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 11:50 2019/1/18
     */
    public static int DEKHash (String str) {
        int hash = str.length();
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }
    /* End Of DEK Hash Function */

    /**
     * @Description: AP算法
     * @param: str
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 11:37 2019/1/18
     */
    public static long APHash (String str) {
        long hash = 0xAAAAAAAA;
        for (int i = 0; i < str.length(); i++) {
            //下面if-else,可以改为三目运算
            if ((i & 1) == 0) {
                hash ^= ((hash << 7) ^ str.charAt(i) * (hash >> 3));
            } else {
                hash ^= (~((hash << 11) + str.charAt(i) ^ (hash >> 5)));
            }
        }
        return hash;
    }
    /* End Of AP Hash Function */

    public long BPHash (String str) {
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = hash << 7 ^ str.charAt(i);
        }
        return hash;
    }
    /* End Of BP Hash Function */

    /** 
     * @Description:  加法hash
     * @param: str  字符串
     * @param: prime  一个质数
     * @return: int  hash结果
     * @Author: Mr.Cheng
     * @Date: 14:18 2019/1/18 
     */ 
    public static int additiveHash (String str, int prime) {
        int hash, i;
        for (hash = str.length(), i = 0; i < str.length(); i++) {
            hash += str.charAt(i);
        }
        return (hash % prime);
    }

    /** 
     * @Description:  旋转hash
     * @param: str 输入字符串
     * @param: prime 质数
     * @return: int  hash值
     * @Author: Mr.Cheng
     * @Date: 14:18 2019/1/18 
     */ 
    public static int rotatingHash (String str, int prime) {
        int hash, i;
        for (hash = str.length(), i = 0; i < str.length(); ++i) {
            hash = (hash << 4) ^ (hash >> 28) ^ str.charAt(i);
        }
        return (hash % prime);//hash = (hash ^ (hash>>10) ^ (hash>>20)) & mask;
        // return (hash ^ (hash>>10) ^ (hash>>20));
    }

    /**
     * @Description:  一次一个hash
     * @param: str 输入字符串
     * @return: int 输出hash值
     * @Author: Mr.Cheng
     * @Date: 14:22 2019/1/18
     */
    public static int oneByOneHash (String str) {
        int hash, i;
        for (hash = 0, i = 0; i < str.length(); ++i) {
            hash += str.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        // return (hash & M_MASK);
        return hash;
    }

    /**
     * @Description:
     * @param: str
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 14:22 2019/1/18
     */
    public static int bernstein (String str) {
        int hash = 0;
        int i;
        for (i = 0; i < str.length(); ++i) {
            hash = 33 * hash + str.charAt(i);
        }
        return hash;
    }

    /**
     * @Description: Universal Hashing
     * @param: str
     * @param: mask
     * @param: tab
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 14:24 2019/1/18
     */
    public static int universal (char[] str, int mask, int[] tab) {
        int hash = str.length, i, len = str.length;
        for (i = 0; i < (len << 3); i += 8) {
            char k = str[i >> 3];
            if ((k & 0x01) == 0) {
                hash ^= tab[i + 0];
            }
            if ((k & 0x02) == 0) {
                hash ^= tab[i + 1];
            }
            if ((k & 0x04) == 0) {
                hash ^= tab[i + 2];
            }
            if ((k & 0x08) == 0) {
                hash ^= tab[i + 3];
            }
            if ((k & 0x10) == 0) {
                hash ^= tab[i + 4];
            }
            if ((k & 0x20) == 0) {
                hash ^= tab[i + 5];
            }
            if ((k & 0x40) == 0) {
                hash ^= tab[i + 6];
            }
            if ((k & 0x80) == 0) {
                hash ^= tab[i + 7];
            }
        }
        return (hash & mask);
    }

    /**
     * @Description:  Zobrist Hashing
     * @param: str
     * @param: mask
     * @param: tab
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 14:24 2019/1/18
     */
    public static int zobrist (char[] str, int mask, int[][] tab) {
        int hash, i;
        for (hash = str.length, i = 0; i < str.length; ++i) {
            hash ^= tab[i][str[i]];
        }
        return (hash & mask);
    }

    /**
     * @Description:  Thomas Wang的算法，整数hash
     * @param: str
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 14:23 2019/1/18
     */
    public static int intHash (int str) {
        str += ~(str << 15);
        str ^= (str >>> 10);
        str += (str << 3);
        str ^= (str >>> 6);
        str += ~(str << 11);
        str ^= (str >>> 16);
        return str;
    }

    /**
     * @Description:  JAVA自己带的算法
     * @param: str
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 14:23 2019/1/18
     */
    public static int java (String str) {
        int h = 0;
        int off = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            h = 31 * h + str.charAt(off++);
        }
        return h;
    }

    /**
     * @Description:  混合hash算法，输出64位的值
     * @param: str
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 14:23 2019/1/18
     */
    public static long mixHash (String str) {
        long hash = str.hashCode();
        hash <<= 32;
        hash |= FNVHash1(str);
        return hash;
    }

}
