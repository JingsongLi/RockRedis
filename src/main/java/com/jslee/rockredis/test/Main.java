package com.jslee.rockredis.test;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.WriteOptions;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    private static Random rand_ = new Random(1000);

    public static void main(String[] args) throws RocksDBException, IOException, InterruptedException {
        String file = "/Users/jslee/Downloads/tmp/";
        deleteDir(new File(file));
        Options options = new Options();
        options.setCreateIfMissing(true);
//        options.setCompactionStyle(CompactionStyle.UNIVERSAL);
        RocksDB db_ = RocksDB.open(options, file);
        RandomGenerator gen_ = new RandomGenerator(0, 0.5d);
        WriteOptions writeOpt = new WriteOptions();
        writeOpt.setDisableWAL(true);
        int keySize_ = 100;
        int valueSize_ = 10000;
        byte[] key = new byte[keySize_];
        byte[] value = new byte[valueSize_];
        long startTime_ = System.nanoTime();
        long bytes = 0;
        long keyRange_ = 1000000;
        for (int i = 0; i < keyRange_; i++) {
            getRandomKey(key, keyRange_);
            gen_.generate(value);
            db_.put(writeOpt, key, value);
            bytes += keySize_;
            bytes += valueSize_;
        }
        long endTime = System.nanoTime();
        double elapsedSeconds =
                1.0d * (endTime - startTime_) / TimeUnit.SECONDS.toNanos(1);
        System.out.println(String.valueOf(bytes / 1048576.0 / elapsedSeconds) + "MB/s");
        System.out.println("use time: " + elapsedSeconds + " s");
        db_.close();

        Thread.sleep(1000 * 60);
    }

    private static void getRandomKey(byte[] key, long range) {
        generateKeyFromLong(key, Math.abs(rand_.nextLong() % range));
    }

    private static void generateKeyFromLong(byte[] slice, long n) {
        assert (n >= 0);
        int startPos = 0;

        for (int i = slice.length - 1; i >= startPos; --i) {
            slice[i] = (byte) ('0' + (n % 10));
            n /= 10;
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String aChildren : children) {
                    boolean success = deleteDir(new File(dir, aChildren));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

}
