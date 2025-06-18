package com.luizeduardobrandao.motivationmvvm.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luizeduardobrandao.motivationmvvm.R
import com.luizeduardobrandao.motivationmvvm.databinding.ActivityMainBinding
import com.luizeduardobrandao.motivationmvvm.helper.MotivationConstants
import com.luizeduardobrandao.motivationmvvm.viewmodel.MainViewModel


// MainActivity agora implementa View.OnClickListener para receber todos os cliques via onClick()
class MainActivity : AppCompatActivity() {

    // View Binding para acessar as Views de activity_main.xml sem findViewById
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ativa a renderização edge-to-edge (layout atrás das barras de status e navegação)
        enableEdgeToEdge()

        // Infla o layout e inicializa o binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Define o conteúdo da Activity para a raiz do binding (RelativeLayout com id “main”)
        setContentView(binding.root)

        // Ajusta o padding para não ficar sob as barras de sistema (status/navigation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observers
        setObservers()

        // Listenners
        setListeners()

    }

    private fun setObservers(){
        viewModel.userName.observe(this) { name ->
            binding.textviewName.text = getString(R.string.label_name_user, name)
        }

        viewModel.currentPhrase.observe(this) { phrase ->
            binding.textviewPhrase.text = phrase
        }

        viewModel.currentFilter.observe(this) { filter ->
            // pinta todos de preto
            listOf(binding.imageAllInclusive, binding.imageHappy, binding.imageFunny).forEach {
                it.setColorFilter(ContextCompat.getColor(this, R.color.black))
            }

            // destaca o selecionado
            val view = when (filter){
                MotivationConstants.PHRASEFILTER.HAPPY -> binding.imageHappy
                MotivationConstants.PHRASEFILTER.FUNNY -> binding.imageFunny
                else -> binding.imageAllInclusive
            }
            view.setColorFilter(ContextCompat.getColor(this, R.color.white))

            viewModel.currentLanguage.observe(this) { code ->
                val display = when (code) {
                    "en" -> "English"
                    "fr" -> "Français"
                    "es" -> "Español"
                    else -> "Português"
                }
                binding.textviewCurrentLanguage.apply {
                    text = display
                    paintFlags = paintFlags or android.graphics.Paint.UNDERLINE_TEXT_FLAG
                }
            }
        }
    }

    private fun setListeners(){
        binding.imageAllInclusive.setOnClickListener { viewModel.setFilter(MotivationConstants.PHRASEFILTER.ALL) }
        binding.imageHappy.setOnClickListener        { viewModel.setFilter(MotivationConstants.PHRASEFILTER.HAPPY) }
        binding.imageFunny.setOnClickListener        { viewModel.setFilter(MotivationConstants.PHRASEFILTER.FUNNY) }
        binding.buttonNewPhrase.setOnClickListener   { viewModel.nextPhrase() }
        binding.buttonLanguages.setOnClickListener   { showLanguageDialog() }

        // Carrega primeira frase (caso queira reforçar)
        viewModel.nextPhrase()
    }

    /** Diálogo permanece na Activity, mas apenas dispara alterações no ViewModel */
    private fun showLanguageDialog() {
        val labels = arrayOf("Português", "English", "Français", "Español")
        val codes  = arrayOf("pt", "en", "fr", "es")
        val checked = codes.indexOf(viewModel.currentLanguage.value).takeIf { it>=0 } ?: 0

        AlertDialog.Builder(this)
            .setTitle("Selecione um idioma")
            .setSingleChoiceItems(labels, checked) { dialog, which ->
                viewModel.setLanguage(codes[which])
                dialog.dismiss()
            }
            .show()
    }
}