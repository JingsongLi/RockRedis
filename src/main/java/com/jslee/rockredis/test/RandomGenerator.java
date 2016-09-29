package com.jslee.rockredis.test;

import java.util.Random;

public class RandomGenerator {
    private final byte[] data_;
    private int dataLength_;
    private int position_;
    private double compressionRatio_;
    Random rand_;

    public RandomGenerator(long seed, double compressionRatio) {
      // We use a limited amount of data over and over again and ensure
      // that it is larger than the compression window (32KB), and also
      byte[] value = new byte[100];
      // large enough to serve all typical value sizes we want to write.
      rand_ = new Random(seed);
      dataLength_ = value.length * 10000;
      data_ = new byte[dataLength_];
      compressionRatio_ = compressionRatio;
      int pos = 0;
      while (pos < dataLength_) {
        compressibleBytes(value);
        System.arraycopy(value, 0, data_, pos,
                         Math.min(value.length, dataLength_ - pos));
        pos += value.length;
      }
    }

    private void compressibleBytes(byte[] value) {
      int baseLength = value.length;
      if (compressionRatio_ < 1.0d) {
        baseLength = (int) (compressionRatio_ * value.length + 0.5);
      }
      if (baseLength <= 0) {
        baseLength = 1;
      }
      int pos;
      for (pos = 0; pos < baseLength; ++pos) {
        value[pos] = (byte) (' ' + rand_.nextInt(95));  // ' ' .. '~'
      }
      while (pos < value.length) {
        System.arraycopy(value, 0, value, pos,
                         Math.min(baseLength, value.length - pos));
        pos += baseLength;
      }
    }

    public void generate(byte[] value) {
      if (position_ + value.length > data_.length) {
        position_ = 0;
        assert(value.length <= data_.length);
      }
      position_ += value.length;
      System.arraycopy(data_, position_ - value.length,
                       value, 0, value.length);
    }
  }
