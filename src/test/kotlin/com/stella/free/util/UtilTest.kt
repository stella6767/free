package com.stella.free.util

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.junit.jupiter.api.Test


class UtilTest {

    @Test
    fun jasyptTest() {

        val key = "asdasd"

        val gitHubSecret = "asdasd"
        val googleSecret = "asdasd"
        val facebookSecret = "asdasd"


        val encryptGithub = jasyptEncrypt(key,gitHubSecret)
        val encryptGoogle = jasyptEncrypt(key,googleSecret)
        val encryptFacebook = jasyptEncrypt(key,facebookSecret)


        System.out.println("encryptGithub : " + encryptGithub)
        System.out.println("encryptGoogle" + encryptGoogle)
        System.out.println("encryptFacebook" + encryptFacebook)


    }

    private fun jasyptEncrypt(key:String, input: String): String? {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(key)
        return encryptor.encrypt(input)
    }

    private fun jasyptDecryt(key:String, input: String): String? {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(key)
        return encryptor.decrypt(input)
    }


}