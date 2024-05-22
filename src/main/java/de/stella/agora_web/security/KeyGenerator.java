package de.stella.agora_web.security;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyGenerator {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // Ruta completa para la carpeta de claves
        Path keysFolderPath = Paths.get("C:\\Users\\Stella\\Documents\\GitHub\\BOOTCAMPF5\\EJERCICIOS-INDIVIDUALES\\Proyecto Personal\\agora_back\\access-refresh-token-keys");

        // Generar claves para el token de acceso
        String accessTokenPrivateKeyPath = keysFolderPath.resolve("access-token-private.key").toString();
        String accessTokenPublicKeyPath = keysFolderPath.resolve("access-token-public.key").toString();
        generateAndSaveKeys("EC", accessTokenPrivateKeyPath, accessTokenPublicKeyPath);

        // Espera un momento antes de generar nuevas claves para el token de actualización
        try {
            Thread.sleep(5000); // Espera 5 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Generar claves para el token de actualización
        String refreshTokenPrivateKeyPath = keysFolderPath.resolve("refresh-token-private.key").toString();
        String refreshTokenPublicKeyPath = keysFolderPath.resolve("refresh-token-public.key").toString();
        generateAndSaveKeys("EC", refreshTokenPrivateKeyPath, refreshTokenPublicKeyPath);
    }

    private static void generateAndSaveKeys(String algorithm, String privateKeyFilePath, String publicKeyFilePath) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
        KeyPair keyPair = kpg.generateKeyPair();

        System.out.println("Claves generadas para el tipo de token: " + algorithm);
        System.out.println("Private Key: " + keyPair.getPrivate());
        System.out.println("Public Key: " + keyPair.getPublic());

        // Guardar la clave privada codificada en Base64 en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(privateKeyFilePath))) {
            String encodedPrivateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            writer.write(encodedPrivateKey);
            System.out.println("Clave privada guardada en: " + privateKeyFilePath);
        }

        // Guardar la clave pública codificada en Base64 en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(publicKeyFilePath))) {
            String encodedPublicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            writer.write(encodedPublicKey);
            System.out.println("Clave pública guardada en: " + publicKeyFilePath);
        }
    }
}