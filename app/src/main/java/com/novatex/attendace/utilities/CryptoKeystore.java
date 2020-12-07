package com.novatex.attendace.utilities;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static com.novatex.attendace.utilities.Constant.CRYPTO_TRANSFORMATION;
import static com.novatex.attendace.utilities.Constant.KEY_ALIAS;
import static com.novatex.attendace.utilities.Constant.KEY_STORE;
import static com.novatex.attendace.utilities.Global.CIPHER_IV;

public class CryptoKeystore {

    public static String encText(String strToEncrypt) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            byte[] byteToEncrypt = null;

            try {
                byteToEncrypt = strToEncrypt.getBytes("UTF-8");
            } catch (Exception ex) {
                return null;
            }


            try {

                KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
                keyStore.load(null);

                if (!keyStore.containsAlias(KEY_ALIAS)) {

                    KeyGenerator generator = KeyGenerator.getInstance("AES", KEY_STORE);

                    KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setKeySize(256)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                            .setRandomizedEncryptionRequired(false)
                            .build();

                    generator.init(spec);

                    //generator.init(256);
                    generator.generateKey();
                }

                SecretKey secretKey = ((KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null)).getSecretKey();

                Cipher cipher = Cipher.getInstance(CRYPTO_TRANSFORMATION);

                IvParameterSpec ivParams = new IvParameterSpec(CIPHER_IV);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);


                byte cipherByte[] = cipher.doFinal(byteToEncrypt);


                String cipherString = new String(cipherByte, "ISO-8859-1");


                return cipherString;

            } catch (Exception e) {

                return null;
            }
        } else {
            return strToEncrypt;
        }

    }

    public static String decText(String strToDecrypt) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            byte decByte[] = null;
            try {

                byte  decByte2[] =strToDecrypt.getBytes("ISO-8859-1");
                decByte=decByte2;
            } catch (Exception ex) {
                return "ASCII issue" + ex.toString();
            }

            try {

                KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
                keyStore.load(null);

                if (!keyStore.containsAlias(KEY_ALIAS)) {

                    KeyGenerator generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_STORE);

                    KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setKeySize(256)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                            .build();

                    generator.init(spec);

                    generator.generateKey();
                }

                SecretKey secretKey = ((KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null)).getSecretKey();


                Cipher cipher = Cipher.getInstance(CRYPTO_TRANSFORMATION);


                IvParameterSpec ivParams = new IvParameterSpec(CIPHER_IV);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
                return new String(cipher.doFinal(decByte));
            } catch (Exception e) {
                Log.e("msg", e.toString());
            }

            return null;
        } else {
            return strToDecrypt;
        }
    }
}
