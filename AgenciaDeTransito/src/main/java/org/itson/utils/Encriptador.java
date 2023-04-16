/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.utils;

import java.security.MessageDigest;
import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 * Clase encargada de la encriptación y desencriptación de cadenas de texto.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class Encriptador {

    String secretKey = "agenciatransito.mm2023";

    /**
     * Método que encripta cadenas de texto.
     *
     * @param texto Cadena de texto a encriptar.
     * @return Cadena de texto encriptada.
     */
    public String encriptar(String texto) {
        String textoEncriptado = "";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] llave = md5.digest(secretKey.getBytes("UTF-8"));
            byte[] bytesKey = Arrays.copyOf(llave, 24);

            SecretKey key = new SecretKeySpec(bytesKey, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, key);

            byte[] bytesTextoPlano = texto.getBytes("UTF-8");
            byte[] buf = cifrado.doFinal(bytesTextoPlano);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            textoEncriptado = new String(base64Bytes);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(
                    null,
                    "Error al encriptar",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
        return textoEncriptado;
    }

    /**
     * Método que desencripta cadenas de texto.
     *
     * @param texto Cadena de texto a desencriptar.
     * @return Cadena de texto desencriptada.
     */
    public String desencriptar(String texto) {
        String textoDesencriptado = "";

        try {
            byte[] mensaje = Base64.decodeBase64(texto.getBytes("UTF-8"));

            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] digestOfPassword = md5.digest(secretKey.getBytes("UTF-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher descifrado = Cipher.getInstance("DESede");
            descifrado.init(Cipher.DECRYPT_MODE, key);

            byte[] textoPlano = descifrado.doFinal(mensaje);
            textoDesencriptado = new String(textoPlano, "UTF-8");
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(
                    null,
                    "Error al desencriptar",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
        return textoDesencriptado;
    }
}
