//Função para validar o tamanho dos Tabuleiros!!
fun validaTabuleiro(numLinhas:Int, numColunas:Int): Boolean {
    //Tamanhos Possiveis de Tabuleiro 5|6 6|7 7|8.
    return (numLinhas == 5 && numColunas == 6) ||
            (numLinhas == 6 && numColunas == 7) ||
            (numLinhas == 7 && numColunas == 8)
}
//Função que dependo da coluna da-lhe um numero se a coluna for A a sua posição é 0!!
fun processaColuna ( numColunas:Int , coluna: String?  ): Int? {
    //Usa o codigo ASCII ou seja cada numero equivale a uma letra.
    if (coluna == null  ||coluna.length != 1 || !coluna[0].isLetter()) {
        return null
    }
    val letra = coluna[0]
    var count = 0

    while (count < numColunas) {
        if (letra == 'A' + count) {
            return count
        }
        count++
    }
    return null
}
//Função para validar o nome do Utilizador!!
fun nomeValido ( nome: String):Boolean{
    //Aceita nomes entre 3 e 12 caracteres, sem espaços.
    if (nome.isEmpty() || nome.length !in 3..12) {
        return false
    }
    var count = 0
    while (count < nome.length) {
        if (nome[count] == ' ') {
            return false
        }
        count++
    }
    return true
}
//Função para criar o topo do Tabuleiro
fun criaTopoTabuleiro( numColunas:Int ):String{
    //Canto Esquerdo
    var count = 0
    var resultado = "\u2554"
    //Centro do Tabuleiro
    while(count < numColunas -1){
        resultado += "\u2550\u2550\u2550\u2550"
        count ++
    }
    //Canto Direito
    resultado += "\u2550\u2550\u2550\u2557"
    return resultado
}
//Função para criar a Legenda Horizontal!!
fun criaLegendaHorizontal(numColunas:Int) : String {

    //Variaveis
    val alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var count = 0
    var resultado = "  "

    //Criaçao da legenda usando a variavel alfabeto para por as letras.
    while(count < numColunas ){
        resultado += alfabeto[count]
        resultado += if(count < numColunas -1){
            " | "
        }else{
            "  "
        }
        count ++
    }

    return resultado

}
//Função para criar o Tabuleiro!!
fun criaTabuleiro(tabuleiro: Array<Array<String?>> , mostraLegenda:Boolean = true):String{
    //Variaveis.
    val numLinhas = tabuleiro.size
    val numColunas = if (tabuleiro.isNotEmpty()) tabuleiro[0].size else 0
    //Resultado iniciado com o Topo do Tabuleiro
    var resultado = criaTopoTabuleiro(numColunas) + "\n"


    for (linha in 0 until numLinhas) {
        resultado += "\u2551"
        for (coluna in 0 until numColunas) {
            val possivelBalao = tabuleiro[linha][coluna]
            val espacoVazio = if (possivelBalao != null) " $possivelBalao" else "  "
            resultado += espacoVazio

            if (coluna < numColunas - 1) {
                resultado += " |"
            }
        }
        resultado += " \u2551"

        if (linha < numLinhas - 1) {
            resultado += "\n"
        }
    }

    if (mostraLegenda) {
        var legenda = criaLegendaHorizontal(numColunas)
        resultado += "\n$legenda"
    }

    return resultado
}
//Função para criar o Tabuleiro de Nulls!!
fun criaTabuleiroVazio(numLinhas:Int , numColunas:Int):Array<Array<String?>>{
    val tabuleiro = Array(numLinhas) { Array<String?>(numColunas) { null } }
    return tabuleiro
}
//Função que conta os Baloes em Linha!!
fun contaBaloesLinha(tabuleiro:Array<Array<String?>>, numLinhas:Int):Int{
    var count = 0
    if (numLinhas >= 0 && numLinhas < tabuleiro.size) {
        for (coluna in 0..<tabuleiro[numLinhas].size) {
            if (tabuleiro[numLinhas][coluna] != null) {
                count++
            }
        }
    }
    return count
}
//Função que conta os Baloes em Coluna!!
fun contaBaloesColuna(tabuleiro: Array<Array<String?>>,numColunas: Int):Int{
    var count = 0
    if (numColunas >= 0 && numColunas < tabuleiro[0].size) {
        for (linha in 0..<tabuleiro.size) {
            if (tabuleiro[linha][numColunas] != null) {
                count++
            }
        }
    }
    return count
}
//Funcao para colocar os Baloes!!
fun colocaBalao(tabuleiro: Array<Array<String?>>, numColunas: Int , humano:Boolean):Boolean {
    val balaoVermelho = "\u001B[31m\u03D9\u001B[0m"
    val balaoAzul = "\u001B[34m\u03D9\u001B[0m"

    for (linha in 0 until tabuleiro.size) {
        if (tabuleiro[linha][numColunas] == null) {
            tabuleiro[linha][numColunas] = if (humano) balaoVermelho else balaoAzul
            return true
        }
    }
    return false
}
//Colocaçao do balao pelo computador!!
fun jogadaNormalComputador(tabuleiro: Array<Array<String?>>): Int? {
    for (coluna in 0 until tabuleiro[0].size) {
        if (tabuleiro[0][coluna] == null) {
            return coluna
        }
    }

    for (linha in 1 until tabuleiro.size) {
        for (coluna in 0 until tabuleiro[linha].size) {
            if (tabuleiro[linha][coluna] == null) {
                return coluna
            }
        }
    }
    return null
}
// Funcoes para a Vitoria!!
fun eVitoriaHorizontal(tabuleiro: Array<Array<String?>>):Boolean {
    val numLinhas = tabuleiro.size
    val numColunas = if (numLinhas > 0) tabuleiro[0].size else 0

    for (linha in 0 until numLinhas) {
        var count = 0
        var lastBalao: String? = null

        for (coluna in 0 until numColunas) {
            if (tabuleiro[linha][coluna] == lastBalao && lastBalao != null) {
                count++
            } else {
                count = 1
                lastBalao = tabuleiro[linha][coluna]
            }

            if (count >= 4) {
                return true
            }
        }
    }
    return false
}

fun eVitoriaVertical(tabuleiro: Array<Array<String?>>):Boolean{
    val numColunas = if (tabuleiro.isNotEmpty()) tabuleiro[0].size else 0

    for (coluna in 0 until numColunas) {
        var count = 0
        var lastBalao: String? = null

        for (linha in tabuleiro.indices) {
            if (tabuleiro[linha][coluna] == lastBalao && lastBalao != null) {
                count++
            } else {
                count = 1
                lastBalao = tabuleiro[linha][coluna]
            }

            if (count >= 4) {
                return true
            }
        }
    }
    return false
}

fun eVitoriaDiagonal(tabuleiro:Array<Array<String?>>):Boolean{

    val numLinhas = tabuleiro.size
    val numColunas = if (numLinhas > 0) tabuleiro[0].size else 0

    // Verifica diagonais da esquerda para a direita
    for (linha in 0..(numLinhas - 4)) {
        for (coluna in 0..(numColunas - 4)) {
            val balao = tabuleiro[linha][coluna]
            if (balao != null &&
                balao == tabuleiro[linha + 1][coluna + 1] &&
                balao == tabuleiro[linha + 2][coluna + 2] &&
                balao == tabuleiro[linha + 3][coluna + 3]) {
                return true
            }
        }
    }

    // Verifica diagonais da direita para a esquerda
    for (linha in 0..(numLinhas - 4)) {
        for (coluna in 3..<numColunas) {
            val balao = tabuleiro[linha][coluna]
            if (balao != null &&
                balao == tabuleiro[linha + 1][coluna - 1] &&
                balao == tabuleiro[linha + 2][coluna - 2] &&
                balao == tabuleiro[linha + 3][coluna - 3]) {
                return true
            }
        }
    }

    return false
}

fun ganhouJogo(tabuleiro: Array<Array<String?>>):Boolean{
    return eVitoriaHorizontal(tabuleiro) || eVitoriaVertical(tabuleiro) || eVitoriaDiagonal(tabuleiro)
}

fun eEmpate(tabuleiro:Array<Array<String?>>):Boolean{

    for (linha in tabuleiro) {
        for (espaco in linha) {
            if (espaco == null) {
                return false
            }
        }
    }
    return true
}

fun jogadaExplodirComputador(tabuleiro: Array<Array<String?>>):Pair<Int,Int>{
    return Pair(1,2)
}

fun explodeBalao(tabuleiro:Array<Array<String?>> , coordenadas:Pair<Int,Int>):Boolean{
    return true
}

fun leJogo(nomeFicheiro : String):Pair<String,Array<Array<String?>>>{
    val tabuleiroExemplo = Array(5) { Array<String?>(6) { null } }
    return Pair("a",tabuleiroExemplo)
}

fun gravaJogo(nomeFicheiro: String , tabuleiro:Array<Array<String?>>, nomeJogador:String){
    println("Grava Jogo")

}



fun main() {
    println("\nBem-vindo ao jogo \"4 Baloes em Linha\"!\n" + "\n" + "1. Novo Jogo\n" + "2. Gravar Jogo\n" + "3. Ler Jogo\n" + "0. Sair\n")
    var continuarPrograma = true

    while (continuarPrograma) {
        var escolha: Int? = null

        while (escolha == null || escolha !in 0..3) {
            escolha = readln().toIntOrNull()
            if (escolha == null || escolha !in 0..3) {
                println("Opcao invalida. Por favor, tente novamente.")
            }
        }

        if (escolha == 0) {
            println("A sair...")
            continuarPrograma = false
        } else if (escolha == 1) {
            var linhas: Int? = null
            var colunas: Int? = null
            var continuarJogo = true

            while (linhas == null || linhas !in 5..7) {
                println("Numero de linhas:")
                linhas = readln().toIntOrNull()
                if (linhas == null || linhas !in 5..7) {
                    println("Numero invalido")
                }
            }

            while (colunas == null || colunas !in 6..8) {
                println("Numero de colunas:")
                colunas = readln().toIntOrNull()
                if (colunas == null || colunas < 6) {
                    println("Numero invalido")
                }else if(colunas >= 10){
                    println("Tamanho do tabuleiro invalido")
                }
            }

            if (!validaTabuleiro(linhas, colunas)) {
                println("Tamanho do tabuleiro inválido.")
            } else {
                // Pede o nome do jogador
                println("Nome do jogador 1:")
                var jogador1 = readln()
                while (!nomeValido(jogador1)) {
                    println("Nome de jogador inválido.")
                    jogador1 = readln()
                }

                val tabuleiro = criaTabuleiroVazio(linhas, colunas)

                while (continuarJogo) {
                    println(criaTabuleiro(tabuleiro))
                    println("\n$jogador1: \u001B[31mϙ\u001B[0m\nTabuleiro ${linhas}X${colunas}")

                    var colunaEscolhida: Int? = null
                    var sairDoJogo = false

                    while (colunaEscolhida == null && continuarJogo) {
                        when (colunas) {
                            6 -> println("Coluna? (A..F):")
                            7 -> println("Coluna? (A..G):")
                            8 -> println("Coluna? (A..H):")
                        }
                        val colunaBalao = readln().uppercase()

                        if (colunaBalao == "SAIR") {
                            println("\n" + "1. Novo Jogo\n" + "2. Gravar Jogo\n" + "3. Ler Jogo\n" + "0. Sair\n")
                            continuarJogo = false
                            sairDoJogo = true
                        } else {
                            colunaEscolhida = processaColuna(colunas, colunaBalao)
                            if (colunaEscolhida == null) {
                                println("Coluna invalida")
                            }
                        }
                    }

                    if (!continuarJogo || sairDoJogo) {
                        continuarJogo = false
                    } else {
                        if (colocaBalao(tabuleiro, colunaEscolhida!!, true)) {
                            val letraColuna = 'A' + colunaEscolhida
                            println("Coluna escolhida: $letraColuna")
                        }

                        if (ganhouJogo(tabuleiro)) {
                            println("$jogador1 venceu o jogo!")
                            continuarJogo = false
                        } else if (eEmpate(tabuleiro)) {
                            println("O jogo terminou em empate!")
                            continuarJogo = false
                        } else {
                            println(criaTabuleiro(tabuleiro))
                            println("\nComputador: \u001B[34mϙ\u001B[0m\nTabuleiro ${linhas}X${colunas}")


                            val colunaComputador = jogadaNormalComputador(tabuleiro)
                            if (colunaComputador != null && colocaBalao(tabuleiro, colunaComputador, false)) {
                                val letraColunaComputador = 'A' + colunaComputador
                                println("Computador: $letraColunaComputador")
                            }

                            if (ganhouJogo(tabuleiro)) {
                                println("O computador venceu o jogo!")
                                continuarJogo = false
                            } else if (eEmpate(tabuleiro)) {
                                println("O jogo terminou em empate!")
                                continuarJogo = false
                            }
                        }
                    }
                }
            }
        } else if (escolha == 2) {
            println("POR IMPLEMENTAR")
        } else if (escolha == 3) {
            println("POR IMPLEMENTAR")
        }
    }
}

