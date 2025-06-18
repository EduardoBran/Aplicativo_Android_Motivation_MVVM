package com.luizeduardobrandao.motivationmvvm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luizeduardobrandao.motivationmvvm.R
import com.luizeduardobrandao.motivationmvvm.databinding.ActivityUserBinding
import com.luizeduardobrandao.motivationmvvm.viewmodel.UserViewModel


class UserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observers
        setObservers()

        // Clique em salvar
        binding.buttonSave.setOnClickListener(this)

    }

    // Lida com eventos de click
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_save -> {
                val name = binding.edittextName.text.toString()
                viewModel.saveName(name)
            }
        }
    }

    private fun setObservers() {
        viewModel.errorMessage.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
        viewModel.navigateToMain.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
