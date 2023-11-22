package com.example.androidsecurity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.androidsecurity.Constants.USER_SETTINGS_JSON
import com.example.androidsecurity.databinding.ActivityPasswordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class PasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityPasswordBinding
    private val Context.dataStore by dataStore(
        USER_SETTINGS_JSON,
        UserSettingsSerializer(CryptoManager())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveBtn.setOnClickListener {
            lifecycleScope.launch {
                dataStore.updateData {
                    UserSettings(
                        binding.userNameEdTv.text.toString(),
                        binding.passwordEdTv.text.toString(),
                    )
                }
            }
        }
        binding.loadBtn.setOnClickListener {
            lifecycleScope.launch {
                binding.userSettingsTv.text = dataStore.data.first().toString()
            }
        }
    }
}