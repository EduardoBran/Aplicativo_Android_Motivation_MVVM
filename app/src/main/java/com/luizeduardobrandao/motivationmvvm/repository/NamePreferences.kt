package com.luizeduardobrandao.motivationmvvm.repository

import android.content.Context

/**
 * - Classe responsável por salvar e recuperar strings simples
 *   (por exemplo, nome do usuário) usando SharedPreferences.
 */

class NamePreferences(context: Context) {

    // Obtém uma instância de SharedPreferences chamada "MotivationPrefs"
    // MODE_PRIVATE garante que somente este app possa ler/escrever neste arquivo
    private val shared = context
        .getSharedPreferences("MotivationPrefs", Context.MODE_PRIVATE)

    /**
     * - Função para armazenar uma string em SharedPreferences.
     * parametro key   -> Chave única para identificar o valor salvo.
     * parametro value -> Valor que será salvo (ex.: nome do usuário).
     */

    fun storeString(key: String, value: String) {
        // Inicia a edição, coloca a string e aplica as mudanças de forma assíncrona
        shared.edit()
            .putString(key, value)
            .apply()
    }

    /**
     * - Função para recuperar uma string salva em SharedPreferences.
     * parametro key -> Chave usada quando o valor foi salvo.
     * retorna       -> O valor associado à chave, ou "" se não existir.
     */

    fun getStoredString(key: String): String {
        // getString retorna null se a chave não existir; o Elvis (?:) garante uma string vazia
        return shared.getString(key, "") ?: ""
    }
}
