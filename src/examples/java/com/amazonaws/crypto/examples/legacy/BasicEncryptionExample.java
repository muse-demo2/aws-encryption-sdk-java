<<<<<<< HEAD:src/examples/java/com/amazonaws/crypto/examples/legacy/BasicEncryptionExample.java
// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
=======
// Copyright Amazon.com Inc. or its affiliates. All Rights Reserved.
>>>>>>> master:src/examples/java/com/amazonaws/crypto/examples/BasicEncryptionExample.java
// SPDX-License-Identifier: Apache-2.0

package com.amazonaws.crypto.examples.legacy;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
import com.amazonaws.encryptionsdk.CommitmentPolicy;

/**
 * <p>
 * Encrypts and then decrypts data using an AWS KMS customer master key.
 * NOTE: Master key providers are deprecated and replaced by keyrings.
 *       We keep these older examples as reference material,
 *       but we recommend that you use the new examples in examples/keyring
 *       The new examples reflect our current guidance for using the library.
 * <p>
 * Arguments:
 * <ol>
 * <li>Key ARN: For help finding the Amazon Resource Name (ARN) of your AWS KMS customer master
 *    key (CMK), see 'Viewing Keys' at http://docs.aws.amazon.com/kms/latest/developerguide/viewing-keys.html
 * </ol>
 */
public class BasicEncryptionExample {

    private static final byte[] EXAMPLE_DATA = "Hello World".getBytes(StandardCharsets.UTF_8);

    public static void main(final String[] args) {
        final String keyArn = args[0];

        encryptAndDecrypt(keyArn);
    }

    static void encryptAndDecrypt(final String keyArn) {
<<<<<<< HEAD:src/examples/java/com/amazonaws/crypto/examples/legacy/BasicEncryptionExample.java
        // 1. Instantiate the AWS Encryption SDK.
        final AwsCrypto crypto = new AwsCrypto();

        // 2. Instantiate a KMS master key provider.
        final KmsMasterKeyProvider masterKeyProvider = KmsMasterKeyProvider.builder().withKeysForEncryption(keyArn).build();

        // 3. Create an encryption context.
        //
=======
        // 1. Instantiate the SDK
        // This builds the AwsCrypto client with the RequireEncryptRequireDecrypt commitment policy,
        // which enforces that this client only encrypts using committing algorithm suites and enforces
        // that this client will only decrypt encrypted messages that were created with a committing algorithm suite.
        // This is the default commitment policy if you build the client with `AwsCrypto.builder().build()`
        // or `AwsCrypto.standard()`.
        final AwsCrypto crypto = AwsCrypto.builder()
                .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
                .build();

        // 2. Instantiate an AWS KMS master key provider in strict mode using buildStrict().
        // In strict mode, the AWS KMS master key provider encrypts and decrypts only by using the key
        // indicated by keyArn.
        // To encrypt and decrypt with this master key provider, use an AWS KMS key ARN to identify the CMKs.
        // In strict mode, the decrypt operation requires a key ARN.
        final KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder().buildStrict(keyArn);

        // 3. Create an encryption context
>>>>>>> master:src/examples/java/com/amazonaws/crypto/examples/BasicEncryptionExample.java
        // Most encrypted data should have an associated encryption context
        // to protect integrity. This sample uses placeholder values.
        // For more information see:
        // blogs.aws.amazon.com/security/post/Tx2LZ6WBJJANTNW/How-to-Protect-the-Integrity-of-Your-Encrypted-Data-by-Using-AWS-Key-Management
        final Map<String, String> encryptionContext = Collections.singletonMap("ExampleContextKey", "ExampleContextValue");

<<<<<<< HEAD:src/examples/java/com/amazonaws/crypto/examples/legacy/BasicEncryptionExample.java
        // 4. Encrypt the data.
        final CryptoResult<byte[], KmsMasterKey> encryptResult = crypto.encryptData(masterKeyProvider, EXAMPLE_DATA, encryptionContext);
        final byte[] ciphertext = encryptResult.getResult();

        // 5. Decrypt the data.
        final CryptoResult<byte[], KmsMasterKey> decryptResult = crypto.decryptData(masterKeyProvider, ciphertext);

        // 6. Before verifying the plaintext, verify that the customer master key that
        // was used in the encryption operation was the one supplied to the master key provider.
        if (!decryptResult.getMasterKeyIds().get(0).equals(keyArn)) {
            throw new IllegalStateException("Wrong key ID!");
        }
=======
        // 4. Encrypt the data
        final CryptoResult<byte[], KmsMasterKey> encryptResult = crypto.encryptData(keyProvider, EXAMPLE_DATA, encryptionContext);
        final byte[] ciphertext = encryptResult.getResult();

        // 5. Decrypt the data
        final CryptoResult<byte[], KmsMasterKey> decryptResult = crypto.decryptData(keyProvider, ciphertext);
>>>>>>> master:src/examples/java/com/amazonaws/crypto/examples/BasicEncryptionExample.java

        // 6. Verify that the encryption context in the result contains the
        // encryption context supplied to the encryptData method. Because the
        // SDK can add values to the encryption context, don't require that
        // the entire context matches.
        if (!encryptionContext.entrySet().stream()
                .allMatch(e -> e.getValue().equals(decryptResult.getEncryptionContext().get(e.getKey())))) {
            throw new IllegalStateException("Wrong Encryption Context!");
        }

<<<<<<< HEAD:src/examples/java/com/amazonaws/crypto/examples/legacy/BasicEncryptionExample.java
        // 8. Verify that the decrypted plaintext matches the original plaintext.
=======
        // 7. Verify that the decrypted plaintext matches the original plaintext
>>>>>>> master:src/examples/java/com/amazonaws/crypto/examples/BasicEncryptionExample.java
        assert Arrays.equals(decryptResult.getResult(), EXAMPLE_DATA);
    }
}
