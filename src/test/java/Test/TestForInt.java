package Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class TestForInt {
    public static void main(String[] args) {
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(bos));
            dos.write(-128);
//            dos.writeInt(22);
//            dos.writeDouble(33.33);
//            dos.writeLong(33l);
//            dos.writeBoolean(true);
//            dos.writeChar('a');
//            dos.writeByte(33);
//            dos.writeFloat(44.4f);
            dos.flush();
            System.out.println(showBytes(bos.toByteArray()));

            DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bos.toByteArray())));
            System.out.println(dis.read());
            System.out.println(dis.readInt());
            System.out.println(dis.readDouble());
            System.out.println(dis.readLong());
            System.out.println(dis.readBoolean());
            System.out.println(dis.readChar());
            System.out.println(dis.readByte());
            System.out.println(dis.readFloat());
            dis.close();
            bos.close();
            System.out.println("======");
            System.out.println(0b01111111);
        }catch(Exception e){
        }
    }


    public static String showBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String intBinStr = Integer.toBinaryString(Byte.toUnsignedInt(b));
            intBinStr = "00000000" + intBinStr;
            intBinStr = intBinStr.substring(intBinStr.length() - 8, intBinStr.length());
            sb.append(intBinStr).append("_");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
}}
