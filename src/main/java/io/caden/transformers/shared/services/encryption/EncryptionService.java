package io.caden.transformers.shared.services.encryption;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
  private static final Charset ENCRYPTION_CHARSETS = StandardCharsets.UTF_8;

  public String decrypt(final SecretKey key, final byte[] data) throws Exception {
    Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    decryptCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));

    byte[] decryptedMessageBytes = decryptCipher.doFinal(data);
    return new String(decryptedMessageBytes, ENCRYPTION_CHARSETS);
  }

  public String decrypt(final String encodedKey, final byte[] data) throws Exception {
    SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(encodedKey), "AES");

    return this.decrypt(key, data);
  }
}
