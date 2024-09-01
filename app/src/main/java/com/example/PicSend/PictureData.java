package com.example.PicSend;

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
        for(int i = 0; i < bytes.length;i++){

            PicData[i] = bytes[i] & 0XFF;
        }
    }
}
