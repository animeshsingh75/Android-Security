package com.example.androidsecurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidsecurity.Constants.SECRET_TXT
import com.example.androidsecurity.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var encryptedString = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cryptoManager = CryptoManager()
        binding.encryptBtn.setOnClickListener {
            val bytes = binding.originalStringEdTv.text.toString().encodeToByteArray()
            val file = File(filesDir, SECRET_TXT)
            if (!file.exists()) {
                file.createNewFile()
            }
            val fos = FileOutputStream(file)
            binding.encryptedCipherTv.text = cryptoManager.encrypt(
                bytes,
                fos
            ).decodeToString()
        }
        binding.decryptBtn.setOnClickListener {
            val file = File(filesDir, SECRET_TXT)
            binding.encryptedCipherTv.text =
                cryptoManager.decrypt(FileInputStream(file)).decodeToString()
        }
        binding.passwordBtn.setOnClickListener {
            startActivity(Intent(this, PasswordActivity::class.java))
        }
    }
}