package com.example.PicSend;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PictureData {

    public String Name;

    public Byte OrientationByte;

    public int[] PicData;

    public PictureData(String name, Byte orientationByte, byte[] data){
        Name = name;
        OrientationByte = orientationByte;
        PicData = new int[data.length];
        ConvertByteArrayToUnsignedInt(data);
    }

    private void ConvertByteArrayToUnsignedInt(byte[] bytes){

        PicData = IntStream.range(0, bytes.length)
                .parallel()
                .map(i -> bytes[i] & 0xFF)
                .toArray();

    }
}
