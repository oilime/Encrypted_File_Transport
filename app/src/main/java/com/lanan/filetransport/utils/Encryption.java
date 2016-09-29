package com.lanan.filetransport.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	
	private static final String algorithmStr = "AES/CBC/PKCS5Padding";
    static private Cipher cipher;
    private static byte[] paramIv = new byte[16];
    private static final String keyBytes = "123456abcdefghij";

    static private Cipher deCipher = cipherSet(Cipher.DECRYPT_MODE);
      
    private static byte[] getKey(String password) {
        byte[] rByte;
        if (password != null) {
            rByte = password.getBytes();
        }else{
            rByte = new byte[24];
        }
        return rByte;
    }
    
    private static Cipher cipherSet(int cipherMode){
    	try{
    	   	SecretKeySpec key = new SecretKeySpec(getKey(keyBytes), "AES");
        	cipher = Cipher.getInstance(algorithmStr);
        	cipher.init(cipherMode, key, new IvParameterSpec(paramIv));
    	}catch (Exception e) {
            e.printStackTrace();
        }
    	return cipher;
    }

    public static void fileDecrypt(final File srcFile, final File tarFile) {

        try {
            byte[] decryptBuffer = new byte[1024];
            int decryptLen;

            InputStream inputStream = new FileInputStream(srcFile);
            OutputStream outputStream = new FileOutputStream(tarFile);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, deCipher);

            while ((decryptLen = inputStream.read(decryptBuffer)) != -1) {
                cipherOutputStream.write(decryptBuffer, 0, decryptLen);
            }
            cipherOutputStream.close();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}