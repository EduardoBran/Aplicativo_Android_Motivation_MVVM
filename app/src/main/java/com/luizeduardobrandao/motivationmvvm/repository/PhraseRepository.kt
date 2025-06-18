package com.luizeduardobrandao.motivationmvvm.repository

import com.luizeduardobrandao.motivationmvvm.helper.MotivationConstants
import kotlin.random.Random

data class Phrase(val description: String, val category: Int, val language: String)

class PhraseRepository {

    private val happy = MotivationConstants.PHRASEFILTER.HAPPY
    private val funny = MotivationConstants.PHRASEFILTER.FUNNY
    private val all = MotivationConstants.PHRASEFILTER.ALL

    private val langPt = MotivationConstants.LANGUAGE.PORTUGUESE
    private val langEn = MotivationConstants.LANGUAGE.ENGLISH
    private val langFr = MotivationConstants.LANGUAGE.FRENCH
    private val langEs = MotivationConstants.LANGUAGE.SPANISH

    private val listPhrases: List<Phrase> = listOf(

        // Português
        Phrase("Não sabendo que era impossível, foi lá e fez.", happy, langPt),
        Phrase("Você não é derrotado quando perde, você é derrotado quando desiste!", happy, langPt),
        Phrase("Quando está mais escuro, vemos mais estrelas!", happy, langPt),
        Phrase("Insanidade é fazer sempre a mesma coisa e esperar um resultado diferente.", happy, langPt),
        Phrase("Não pare quando estiver cansado, pare quando tiver terminado.", happy, langPt),
        Phrase("O que você pode fazer agora que tem o maior impacto sobre o seu sucesso?", happy, langPt),
        Phrase("A melhor maneira de prever o futuro é inventá-lo.", happy, langPt),
        Phrase("Você perde todas as chances que você não aproveita.", happy, langPt),
        Phrase("Fracasso é o condimento que dá sabor ao sucesso.", happy, langPt),
        Phrase(" Enquanto não estivermos comprometidos, haverá hesitação!", happy, langPt),
        Phrase("Se você não sabe onde quer ir, qualquer caminho serve.", happy, langPt),
        Phrase("Se você acredita, faz toda a diferença.", happy, langPt),
        Phrase("Riscos devem ser corridos, porque o maior perigo é não arriscar nada!", happy, langPt),

        Phrase("Se a vida te der limões, aprenda a fazer uma caipirinha.", funny, langPt),
        Phrase("Corra atrás dos seus sonhos. Se não der, volte a dormir.", funny, langPt),
        Phrase("Acordei motivado... depois dormi de novo.", funny, langPt),
        Phrase("Você é único... igual a todo mundo.", funny, langPt),
        Phrase("O importante é não desistir... exceto da dieta, essa pode!", funny, langPt),
        Phrase("Você é capaz de tudo. Principalmente de fazer besteira com confiança.", funny, langPt),
        Phrase("Se tudo der errado hoje, relaxa: amanhã pode piorar.", funny, langPt),
        Phrase("Não tenha medo de fracassar. Tenha medo de não ter wi-fi!", funny, langPt),
        Phrase("Errar é humano. Culpar o colega é estratégia!", funny, langPt),
        Phrase("Grandes conquistas começam com um clique em 'adiar alarme'.", funny, langPt),
        Phrase("Acredite no seu potencial. Mesmo que ele esteja em modo soneca.", funny, langPt),
        Phrase("Acredite nos seus sonhos. Porque acordado tá difícil.", funny, langPt),
        Phrase("Não desista! Se até miojo vira refeição, você também tem chance.", funny, langPt),

        // Inglês

        Phrase("Not knowing it was impossible, he went ahead and did it.", happy, langEn),
        Phrase("You are not defeated when you lose; you are defeated when you give up!", happy, langEn),
        Phrase("When it's darkest, we see the most stars!", happy, langEn),
        Phrase("Insanity is doing the same thing over and over and expecting different results.", happy, langEn),
        Phrase("Don't stop when you're tired; stop when you're done.", happy, langEn),
        Phrase("What can you do right now that will have the greatest impact on your success?", happy, langEn),
        Phrase("The best way to predict the future is to invent it.", happy, langEn),
        Phrase("You miss all the shots you don't take.", happy, langEn),
        Phrase("Failure is the seasoning that gives success its flavor.", happy, langEn),
        Phrase("Until we are committed, there will be hesitation!", happy, langEn),
        Phrase("If you don't know where you want to go, any path will get you there.", happy, langEn),
        Phrase("If you believe, it makes all the difference.", happy, langEn),
        Phrase("Risks must be taken, because the greatest danger is not taking any!", happy, langEn),

        Phrase("If life gives you lemons, learn to make a caipirinha.", funny, langEn),
        Phrase("Chase your dreams. If it doesn't work out, go back to sleep.", funny, langEn),
        Phrase("I woke up motivated... then I went back to sleep.", funny, langEn),
        Phrase("You're one of a kind... just like everyone else.", funny, langEn),
        Phrase("The important thing is not to give up... except for diets; those you can!", funny, langEn),
        Phrase("You are capable of anything. Especially messing up with confidence.", funny, langEn),
        Phrase("If everything goes wrong today, relax: tomorrow could be worse.", funny, langEn),
        Phrase("Don't be afraid to fail. Be afraid of having no Wi-Fi!", funny, langEn),
        Phrase("To err is human. Blaming a coworker is strategy!", funny, langEn),
        Phrase("Great achievements begin with a click on 'snooze alarm'.", funny, langEn),
        Phrase("Believe in your potential. Even if it's in snooze mode.", funny, langEn),
        Phrase("Believe in your dreams. Because being awake is hard.", funny, langEn),
        Phrase("Don't give up! If instant noodles can become a meal, so can you.", funny, langEn),

        // Francês

        Phrase("Ne sachant pas que c'était impossible, il a foncé et l'a fait.", happy, langFr),
        Phrase("Vous n'êtes pas vaincu quand vous perdez ; vous êtes vaincu quand vous abandonnez !", happy, langFr),
        Phrase("Quand il fait le plus sombre, on voit le plus d'étoiles !", happy, langFr),
        Phrase("La folie, c'est de faire la même chose encore et encore et d'attendre des résultats différents.", happy, langFr),
        Phrase("Ne t'arrête pas quand tu es fatigué ; arrête-toi quand tu as terminé.", happy, langFr),
        Phrase("Que peux-tu faire maintenant qui aura le plus grand impact sur ton succès ?", happy, langFr),
        Phrase("La meilleure façon de prédire l'avenir est de l'inventer.", happy, langFr),
        Phrase("On manque tous les tirs qu'on ne tente pas.", happy, langFr),
        Phrase("L'échec est l'assaisonnement qui donne sa saveur au succès.", happy, langFr),
        Phrase("Tant que nous ne sommes pas engagés, il y aura de l'hésitation !", happy, langFr),
        Phrase("Si tu ne sais pas où tu veux aller, n'importe quel chemin te mènera là.", happy, langFr),
        Phrase("Si tu y crois, cela fait toute la différence.", happy, langFr),
        Phrase("Il faut prendre des risques, car le plus grand danger est de n'en prendre aucun !", happy, langFr),

        Phrase("Si la vie te donne des citrons, apprends à faire une caipirinha.", funny, langFr),
        Phrase("Poursuis tes rêves. Si ça ne marche pas, retourne dormir.", funny, langFr),
        Phrase("Je me suis réveillé motivé... puis je me suis rendormi.", funny, langFr),
        Phrase("Tu es unique... tout comme tout le monde.", funny, langFr),
        Phrase("L'important est de ne pas abandonner... sauf les régimes, ceux-là oui !", funny, langFr),
        Phrase("Tu es capable de tout. Surtout de faire des bêtises avec confiance.", funny, langFr),
        Phrase("Si tout tourne mal aujourd'hui, détends-toi : demain peut être pire.", funny, langFr),
        Phrase("N'aie pas peur d'échouer. Aie peur de ne pas avoir de Wi-Fi !", funny, langFr),
        Phrase("L'erreur est humaine. Blâmer un collègue, c'est stratégique !", funny, langFr),
        Phrase("Les grandes réussites commencent par un clic sur 'snooze'.", funny, langFr),
        Phrase("Crois en ton potentiel. Même s'il est en mode snooze.", funny, langFr),
        Phrase("Crois en tes rêves. Parce qu'être réveillé, c'est dur.", funny, langFr),
        Phrase("N'abandonne pas ! Si les nouilles instantanées peuvent devenir un repas, toi aussi.", funny, langFr),

        // Espanhol

    Phrase("Sin saber que era imposible, fue y lo hizo.", happy, langEs),
    Phrase("No estás derrotado cuando pierdes; estás derrotado cuando te rindes.", happy, langEs),
    Phrase("¡Cuando está más oscuro, vemos más estrellas!", happy, langEs),
    Phrase("La locura es hacer siempre lo mismo y esperar resultados diferentes.", happy, langEs),
    Phrase("No te detengas cuando estés cansado; detente cuando hayas terminado.", happy, langEs),
    Phrase("¿Qué puedes hacer ahora que tendrá el mayor impacto en tu éxito?", happy, langEs),
    Phrase("La mejor manera de predecir el futuro es inventarlo.", happy, langEs),
    Phrase("Fallamos todos los tiros que no intentamos.", happy, langEs),
    Phrase("El fracaso es el condimento que le da sabor al éxito.", happy, langEs),
    Phrase("¡Mientras no estemos comprometidos, habrá vacilación!", happy, langEs),
    Phrase("Si no sabes a dónde quieres ir, cualquier camino te llevará allí.", happy, langEs),
    Phrase("Si crees, marca toda la diferencia.", happy, langEs),
    Phrase("Se deben tomar riesgos, porque el mayor peligro es no arriesgar nada.", happy, langEs),

    Phrase("Si la vida te da limones, aprende a hacer una caipirinha.", funny, langEs),
    Phrase("Persigue tus sueños. Si no funciona, vuelve a dormir.", funny, langEs),
    Phrase("Me desperté motivado... luego me volví a dormir.", funny, langEs),
    Phrase("Eres único... igual que todo el mundo.", funny, langEs),
    Phrase("Lo importante es no rendirse... ¡excepto con las dietas; esas sí!", funny, langEs),
    Phrase("Eres capaz de todo. Especialmente de cometer locuras con confianza.", funny, langEs),
    Phrase("Si todo sale mal hoy, relájate: mañana podría ser peor.", funny, langEs),
    Phrase("No tengas miedo de fracasar. ¡Teme quedarte sin Wi-Fi!", funny, langEs),
    Phrase("Errar es humano. ¡Culpar a un compañero es estrategia!", funny, langEs),
    Phrase("Los grandes logros comienzan con un clic en 'posponer alarma'.", funny, langEs),
    Phrase("Cree en tu potencial. Incluso si está en modo posponer.", funny, langEs),
    Phrase("Cree en tus sueños. Porque estar despierto es difícil.", funny, langEs),
    Phrase("¡No te rindas! Si los fideos instantáneos pueden convertirse en una comida, tú también.", funny, langEs)

    )

    // Obtém frase aleatória de acordo com o filtro
    fun getPhrase(value: Int, language: String): String {

        // 0) Tratamento para caso não tenha a língua escolhida no dispositivo
        var langFilter = language.lowercase()
        if (language !in listOf(langPt, langEn, langFr, langEs)){
            langFilter = MotivationConstants.LANGUAGE.PORTUGUESE
        }

        // 1) Filtra a lista completa de frases:
        //    - mantém apenas frases cuja categoria seja igual ao 'value' selecionado
        //    - ou, se 'value' for o código ALL, mantém todas as frases
        val filteredPhrases = listPhrases.filter {
            (it.category == value || value == all) && it.language == langFilter
        }

        // 2) Gera um índice aleatório entre 0 (inclusivo) e filteredPhrases.size (exclusivo)
        //    Usamos Random.nextInt(n) para obter um inteiro aleatório no intervalo [0, n-1]
        val randomIndex = Random.nextInt(filteredPhrases.size)

        // 3) Retorna a descrição da frase que está naquela posição aleatória
        //    filteredPhrases[randomIndex] é um objeto Phrase, e .description é a string que queremos
        return filteredPhrases[randomIndex].description
    }

    fun getFilteredPhrases(category: Int, language: String): List<Phrase> =
        listPhrases.filter {
            (it.category == category || category == all)
                    && it.language == language.lowercase()
        }

}