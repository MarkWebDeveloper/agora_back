package de.stella.agora_web.auth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

  

public class KeyUtils { 
    // Inyección de dependencias
    @Autowired
    Environment environment; 
  
    // Inyección de properties
    @Value("${access-token.private}") 
    private String accessTokenPrivateKeyPath; 
  
    @Value("${access-token.public}") 
    private String accessTokenPublicKeyPath; 
  
    @Value("${refresh-token.private}") 
    private String refreshTokenPrivateKeyPath; 
  
    @Value("${refresh-token.public}") 
    private String refreshTokenPublicKeyPath; 
  
    // Almacenamiento de pares de claves para acceso y refresco de tokens
    private KeyPair _accessTokenKeyPair; 
    private KeyPair _refreshTokenKeyPair; 
  
    // Obtiene el par de claves de acceso a tokens
    private KeyPair getAccessTokenKeyPair() { 
        if (Objects.isNull(_accessTokenKeyPair)) { 
            // Si no se ha generado el par de claves, lo genera y lo almacena
            _accessTokenKeyPair = getKeyPair(accessTokenPublicKeyPath, accessTokenPrivateKeyPath); 
        } 
        return _accessTokenKeyPair; 
    } 
  
    // Obtiene el par de claves de refresco de tokens
    private KeyPair getRefreshTokenKeyPair() { 
        if (Objects.isNull(_refreshTokenKeyPair)) { 
            // Si no se ha generado el par de claves, lo genera y lo almacena
            _refreshTokenKeyPair = getKeyPair(refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath); 
        } 
        return _refreshTokenKeyPair; 
    } 
  
    // Genera un par de claves y lo almacena en ficheros
    private KeyPair getKeyPair(String publicKeyPath, String privateKeyPath) { 
        KeyPair keyPair; 
  
        // Creación de objetos File para los ficheros de claves
        File publicKeyFile = new File(publicKeyPath); 
        File privateKeyFile = new File(privateKeyPath); 
  
        // Si los ficheros de claves existen, los lee y devuelve el par de claves
        if (publicKeyFile.exists() && privateKeyFile.exists()) { 
            try { 
                KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
  
                byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath()); 
                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes); 
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec); 
  
                byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath()); 
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes); 
                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec); 
  
                keyPair = new KeyPair(publicKey, privateKey); 
                return keyPair; 
            } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) { 
                throw new RuntimeException(e); 
            } 
        } else { 
            // Si los ficheros de claves no existen, si el entorno es de producción, lanza una excepción
            if (Arrays.stream(environment.getActiveProfiles()).anyMatch(s -> s.equals("prod"))) { 
                throw new RuntimeException("public and private keys don't exist"); 
            } 
        } 
  
        // Si el entorno no es de producción, crea los directorios y ficheros de claves
        File directory = new File("access-refresh-token-keys"); 
        if (!directory.exists()) { 
            directory.mkdirs(); 
        } 
        try { 
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); 
            keyPairGenerator.initialize(2048); 
            keyPair = keyPairGenerator.generateKeyPair(); 
            try (FileOutputStream fos = new FileOutputStream(publicKeyPath)) { 
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded()); 
                fos.write(keySpec.getEncoded()); 
            } 
  
            try (FileOutputStream fos = new FileOutputStream(privateKeyPath)) { 
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded()); 
                fos.write(keySpec.getEncoded()); 
            } 
        } catch (NoSuchAlgorithmException | IOException e) { 
            throw new RuntimeException(e); 
        } 
  
        return keyPair; 
    } 
  
  
    // Devuelve la clave pública de acceso a tokens
    public RSAPublicKey getAccessTokenPublicKey() { 
        return (RSAPublicKey) getAccessTokenKeyPair().getPublic(); 
    }; 
    // Devuelve la clave privada de acceso a tokens
    public RSAPrivateKey getAccessTokenPrivateKey() { 
        return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate(); 
    }; 
    // Devuelve la clave pública de refresco de tokens
    public RSAPublicKey getRefreshTokenPublicKey() { 
        return (RSAPublicKey) getRefreshTokenKeyPair().getPublic(); 
    }; 
    // Devuelve la clave privada de refresco de tokens
    public RSAPrivateKey getRefreshTokenPrivateKey() { 
        return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate(); 
    }

    public static RSAPublicKey loadPublicKey(String publicKeyPath) throws IOException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyPath));
        EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }; 
}
