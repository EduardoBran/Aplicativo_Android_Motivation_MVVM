package com.luizeduardobrandao.motivationmvvm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luizeduardobrandao.motivationmvvm.helper.MotivationConstants
import com.luizeduardobrandao.motivationmvvm.repository.NamePreferences
import com.luizeduardobrandao.motivationmvvm.R
import com.luizeduardobrandao.motivationmvvm.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserBinding
    private lateinit var namePreferences: NamePreferences // instancia para poder usar em vários métodos diferentes

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

        // Inicializa variáveis da classe
        namePreferences = NamePreferences(this)

        // Inicializa mét0do para verificar se nome já foi salvo
        verifyUserName()

        // Acesso aos elementos de interface
        setListeners()
    }

    // Lida com eventos de click
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_save -> {
                handleButtonSave()
            }
        }
    }

    // Verifica se namePreferences tem algum valor
    private fun verifyUserName() {
        val name = namePreferences.getStoredString(MotivationConstants.KEY.PERSON_NAME)
        if (name.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
            // Impede que seja possível voltar a Activity
            finish()
        }
    }

    // Salva o nome do usuário para utilizações futuras
    private fun handleButtonSave() {

        // Obtém o nome
        val name = binding.edittextName.text.toString()

        // Verifica se usuário preencheu o nome
        if (name.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_mandatory_name), Toast.LENGTH_LONG).show()
        }
        else {
            // Salva os dados do usuário e redireciona para MainActivity
            // NamePreferences(this).storeString("NAME", name) SEM CONSTANTE ("NAME") e SEM INSTANCIA
            // namePreferences.storeString("NAME", name)       SEM CONSTANTE ("NAME")

            namePreferences.storeString(MotivationConstants.KEY.PERSON_NAME, name) // correto com instancia e constante

            // Navega para Main_Activity
            startActivity(Intent(this, MainActivity::class.java))

            // Impede que seja possível voltar a Activity
            finish()
        }
    }

    private fun setListeners(){
        binding.buttonSave.setOnClickListener(this)
    }
}