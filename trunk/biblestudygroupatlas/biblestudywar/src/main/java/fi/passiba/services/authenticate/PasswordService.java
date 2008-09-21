

package fi.passiba.services.authenticate;

import java.io.IOException;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author haverinen
 */
public class PasswordService {
    
    
    private static int ITERATIONS = 1000;
    private static final String keyValue="PBEWithMD5AndDES";
    public static String encrypt(char[] password, String username)
		throws Exception
{
	// Begin by creating a random salt of 64 bits (8 bytes)

	byte[] salt = new byte[8];
	Random random = new Random();
	random.nextBytes(salt);

	// Create the PBEKeySpec with the given password

	PBEKeySpec keySpec = new PBEKeySpec(password);

	// Get a SecretKeyFactory for PBEWithSHAAndTwofish

	SecretKeyFactory keyFactory =
		SecretKeyFactory.getInstance(keyValue);
	
	// Create our key
	
	SecretKey key = keyFactory.generateSecret(keySpec);
	
	// Now create a parameter spec for our salt and iterations
	
	PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATIONS);
	
	// Create a cipher and initialize it for encrypting
	
	Cipher cipher = Cipher.getInstance(keyValue);
	cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	
	byte[] ciphertext = cipher.doFinal(username.getBytes());
	
	BASE64Encoder encoder = new BASE64Encoder();
	
	String saltString = encoder.encode(salt);
	String ciphertextString = encoder.encode(ciphertext);
	
	return saltString+ciphertextString;
}
    
    public static String decrypt(char[] password, String username) throws Exception
		
	{
	// Below we split the text into salt and text strings.
	
	String salt = username.substring(0,12);
	String ciphertext = username.substring(12,username.length());
	
	// BASE64Decode the bytes for the salt and the ciphertext

	BASE64Decoder decoder = new BASE64Decoder();
	byte[] saltArray = decoder.decodeBuffer(salt);
	byte[] ciphertextArray = decoder.decodeBuffer(ciphertext);

	// Create the PBEKeySpec with the given password

	PBEKeySpec keySpec = new PBEKeySpec(password);

	// Get a SecretKeyFactory for PBEWithSHAAndTwofish

	SecretKeyFactory keyFactory =
		SecretKeyFactory.getInstance(keyValue);
	
	// Create our key
	
	SecretKey key = keyFactory.generateSecret(keySpec);
	
	// Now create a parameter spec for our salt and iterations
	PBEParameterSpec paramSpec =
		new PBEParameterSpec(saltArray, ITERATIONS);
	
	// Create a cipher and initialize it for encrypting
	
	Cipher cipher = Cipher.getInstance(keyValue);
	cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	
	// Perform the actual decryption
	
	byte[] plaintextArray = cipher.doFinal(ciphertextArray);
	
	return new String(plaintextArray);
	}




}
