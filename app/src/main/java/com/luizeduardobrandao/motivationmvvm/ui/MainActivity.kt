package com.luizeduardobrandao.motivationmvvm.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luizeduardobrandao.motivationmvvm.helper.MotivationConstants
import com.luizeduardobrandao.motivationmvvm.repository.NamePreferences
import com.luizeduardobrandao.motivationmvvm.repository.PhraseRepository
import java.util.Locale
import android.graphics.Paint
import com.luizeduardobrandao.motivationmvvm.repository.Phrase
import com.luizeduardobrandao.motivationmvvm.R
import com.luizeduardobrandao.motivationmvvm.databinding.ActivityMainBinding
import kotlin.random.Random

// MainActivity agora implementa View.OnClickListener para receber todos os cliques via onClick()
class MainActivity : AppCompatActivity(), View.OnClickListener {

    // View Binding para acessar as Views de activity_main.xml sem findViewById
    private lateinit var binding: ActivityMainBinding

    // instancia para poder usar em vários métodos diferentes
    private lateinit var namePreferences: NamePreferences

    // Estado atual do filtro de frases (inicia com valor da constante ALL em MotivationConstants)
    private var filter: Int = MotivationConstants.PHRASEFILTER.ALL

    // Repositório que fornece frases a partir do filtro (nao precisa de lateinit pois nao recebe parâmetro)
    private val phraseRepository = PhraseRepository()

    // Guarda a frase exibida para restaurar após rotação
    private var currentPhrase: String = ""

    // Variável para armazenar o idioma atual
    private var currentLanguage: String = Locale.getDefault().language

    // lista corrente de frases no idioma atual
    private lateinit var currentList: List<Phrase>
    // índice da frase que está sendo exibida
    private var currentIndex: Int = 0

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

        // Cria a instância de SecurityPreferences para ler e escrever NamePreferences
        namePreferences = NamePreferences(this)

        // Chamar updateLanguageLabel()
        updateLanguageLabel()

        // --- Restaura estado em caso de rotação ---
        if (savedInstanceState != null) {

            // 1) Recupera o filtro salvo (ou ALL caso não exista)
            filter = savedInstanceState.getInt("filter", MotivationConstants.PHRASEFILTER.ALL)

            // 2) Recupera a frase salva (ou string vazia caso não exista)
            currentPhrase = savedInstanceState.getString("phrase") ?: ""

            // 3) Reseta a cor de todos os ícones para não selecionado (preto)
            binding.imageAllInclusive.setColorFilter(ContextCompat.getColor(this, R.color.black))
            binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.black))
            binding.imageFunny.setColorFilter(ContextCompat.getColor(this, R.color.black))

            // 4) Destaca apenas o ícone correspondente ao filtro restaurado
            when (filter) {
                MotivationConstants.PHRASEFILTER.ALL   -> highlightFilter(binding.imageAllInclusive)
                MotivationConstants.PHRASEFILTER.HAPPY -> highlightFilter(binding.imageHappy)
                MotivationConstants.PHRASEFILTER.FUNNY -> highlightFilter(binding.imageFunny)
            }

            // 5) Reexibe exatamente a mesma frase que estava na tela
            binding.textviewPhrase.text = currentPhrase

        } else {
            // --- Estado inicial: sem filtro explícito (mas ALL visual) e frase nova ---
            handleFilter(R.id.image_all_inclusive)
            refreshPhrase()
        }

        // Registra os listeners de clique nos botões (delega para onClick(v: View)
        setListeners()

        // Exibe o nome do usuário na saudação
        showUserName()
    }

    // Salva o estado atual (filtro e frase) antes da Activity ser destruída em rotação.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("filter", filter)
        outState.putString("phrase", currentPhrase)
    }

    // Recebe todos os cliques de Views registrados via "setOnClickListener(this)" (sempre apagar o "?" de "View?")
    // Identifica qual View foi clicada e chama a função apropriada.
    override fun onClick(v: View) {
        when (v.id){
            // Se um dos ícones de filtro for clicado, muda o filtro
            R.id.image_all_inclusive,
            R.id.image_happy,
            R.id.image_funny -> handleFilter(v.id)

            // Se o botão “Nova Frase” for clicado, atualiza a frase
            R.id.button_new_phrase -> refreshPhrase()

            R.id.button_languages -> showLanguageDialog()      // novo case
        }
    }

    // Vincula esta Activity como listener de clique para cada view de interesse.
    private fun setListeners(){
        binding.buttonNewPhrase.setOnClickListener (this)
        binding.imageAllInclusive.setOnClickListener (this)
        binding.imageHappy.setOnClickListener (this)
        binding.imageFunny.setOnClickListener (this)
        // Registra clique no botão “Idiomas”
        binding.buttonLanguages.setOnClickListener(this)
    }

    // Lê o nome do usuário de SharedPreferences e atualiza o TextView de saudação.
    private fun showUserName(){
        val name = namePreferences.getStoredString(MotivationConstants.KEY.PERSON_NAME)

        // binding.textviewName.text = name - exibe somente o nome
        binding.textviewName.text = getString(R.string.label_name_user, name) // Usa string formatada do resources: "Olá, %s!"

    }

    // Busca uma nova frase do repositório, passando o filtro atual e atualiza o texto na tela.
    // Também armazena em currentPhrase para restauração futura.
    private fun refreshPhrase() {
        // 1) gera a lista filtrada no idioma atual
        currentList = phraseRepository
            .getFilteredPhrases(filter, currentLanguage)

        // 2) escolhe um índice aleatório dentro dessa lista
        currentIndex = Random.nextInt(currentList.size)

        // 3) exibe a frase desse índice
        currentPhrase = currentList[currentIndex].description
        binding.textviewPhrase.text = currentPhrase
    }

    // Destaca o ícone passado como parâmetro, pintando-o de branco.
    // Usado por handleFilter() para mostrar qual é o filtro ativo.
    private fun highlightFilter(view: ImageView) {
        view.setColorFilter(ContextCompat.getColor(this, R.color.white))
    }

    // Atualiza o filtro interno e o destaque visual nos ícones.
    // Sempre pinta todos de preto antes de destacar o escolhido.
    private fun handleFilter(id: Int) {

        // Primeiro, pinta todos os ícones de preto (estado não selecionado)
        binding.imageAllInclusive.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageFunny.setColorFilter(ContextCompat.getColor(this, R.color.black))

        // Depois, de acordo com o id, atualiza a variável filter e chama highlightFilter()
        when (id) {
            R.id.image_all_inclusive -> {
                filter = MotivationConstants.PHRASEFILTER.ALL
                highlightFilter(binding.imageAllInclusive)
            }
            R.id.image_happy -> {
                filter = MotivationConstants.PHRASEFILTER.HAPPY
                highlightFilter(binding.imageHappy)
            }
            R.id.image_funny -> {
                filter = MotivationConstants.PHRASEFILTER.FUNNY
                highlightFilter(binding.imageFunny)
            }
        }
        // Ao trocar o filtro, já exibe a frase correspondente
        refreshPhrase()
    }

    // Exibe um diálogo com opções de idioma para frase
    // e, ao selecionar, atualiza o idioma usado e recarrega a frase.
    private fun showLanguageDialog() {
        // 1) Labels visíveis para o usuário
        val labels = arrayOf("Português", "English", "Français", "Español")
        // 2) Códigos correspondentes usados internamente
        val codes  = arrayOf("pt", "en", "fr", "es")

        // 3) Busca o índice do idioma atualmente selecionado (ou 0 se não achar)
        val checked = codes
            .indexOf(currentLanguage)
            .takeIf { it >= 0 }        // se for >=0, mantém; senão, vira null
            ?: 0                       // se vier null, usa 0 (Português)

        // 4) Constrói e mostra o AlertDialog de escolha única
        AlertDialog.Builder(this)
            .setTitle("Selecione um idioma") // título do diálogo
            .setSingleChoiceItems(labels, checked) { dialog, which ->
                // 5) Ao clicar em uma opção:
                // 5.1) Atualiza a variável de idioma
                currentLanguage = codes[which]
                // 5.2) Aqui, apenas traduz
                translateCurrentPhrase()
                // 5.3) Atualiza o TextView com o idioma
                updateLanguageLabel()
                // 5.4) Fecha o diálogo
                dialog.dismiss()
            }
            .show()
    }

    // Função para atualizar o rótulo e sublinhá-lo:
    private fun updateLanguageLabel() {
        // Traduzir código para nome legível
        val displayName = when (currentLanguage) {
            "pt" -> "Português"
            "en" -> "English"
            "fr" -> "Français"
            "es" -> "Español"
            else -> "Português"
        }

        // Aplica texto e sublinhado
        binding.textviewCurrentLanguage.apply {
            text = displayName
            paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

    // Refiltra a mesma lista para o novo idioma e mantém o mesmo índice,
    // trocando apenas o texto, sem sortear uma frase nova.
    private fun translateCurrentPhrase() {
        // 1) refiltra para o idioma recém-selecionado
        val newList = phraseRepository
            .getFilteredPhrases(filter, currentLanguage)

        // 2) garante que o índice exista nessa nova lista
        val idx = if (currentIndex < newList.size) currentIndex else 0

        // 3) atribui e exibe o texto traduzido
        currentPhrase = newList[idx].description
        binding.textviewPhrase.text = currentPhrase
    }
}