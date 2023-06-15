package wordsvirtuoso

import java.io.File
import kotlin.random.Random
import kotlin.system.exitProcess

val wordListG = mutableListOf<String>()
val candidatesListG = mutableListOf<String>()

class Words(args: Array<String>) {

    private val words = File(args[0])
    private val candidates = File(args[1])

    private fun checkExist() {
        if (!words.exists()) {
            println("Error: The words file ${words.name} doesn't exist.")
            exitProcess(0)
        }
        if (!candidates.exists()) {
            println("Error: The candidate words file ${candidates.name} doesn't exist.")
            exitProcess(0)

        }
    }

    private fun checkConditions(file: File){
        var count = 0
        for (i in file.readLines()) {
            count += checkWord(i)
        }
        if (count != 0) {
            println("Error: $count invalid words were found in the ${file.name} file.")
        }
    }

    private fun checkWord(input: String): Int {
        return when {
            input.matches("[a-zA-Z]{5}".toRegex()) -> {
                if (input.toSet().size == input.length) {
                    0
                } else {
                    1
                }
            }
            input.length != 5 -> {
                1
            }
            else -> {
                1
            }
        }

    }

    private fun createList(file: File): List<String> {
        val list = mutableListOf<String>()
        for (i in file.readLines()) {
            list.add(i.lowercase())
        }
        return list
    }

    private fun checkContains() {
        val wordList = createList(words)
        val candidatesList = createList(candidates)
        val combineList = (wordList + candidatesList).toSet()
        val num = combineList.size - wordList.size
        if (combineList.size != wordList.size) {
            println("Error: $num candidate words are not included in the ${words.name} file.")
            exitProcess(0)
        }
        wordListG.addAll(wordList)
        candidatesListG.addAll(candidatesList)

    }

    fun checkAll(){
        checkExist()
        checkConditions(words)
        checkConditions(candidates)
        checkContains()
        println("Words Virtuoso")
    }


    }

class Play() {

    private fun chooseRandom(): String{
        val random = Random
        return candidatesListG[random.nextInt(0, candidatesListG.size)]
    }

    private fun checkWord(input: String): Int {
        when {
            input.matches("[a-zA-Z]{5}".toRegex()) -> {
                return if (input.toSet().size == input.length) {
                    1
                } else {
                    println("The input has duplicate letters.")
                    0
                }
            }
            input.length != 5 -> {
                println("The input isn't a 5-letter word.")
                return 0
            }
            else -> {
                println("One or more letters of the input aren't valid.")
                return 0
            }
        }

    }

    private fun listContains(input: String): Int{
        return if (!wordListG.contains(input.lowercase())) {
            println("The input word isn't included in my words list.")
            0
        } else 1
    }

    private fun showGoodLetters(secretWord: String, input: String): String {
        val charSet = secretWord.toSet()
        return input.map { if (it in charSet) it else '_' }.joinToString("")
    }

    fun game() {
        val secretWord = chooseRandom()

        while (true) {
            println()
            println("Input a 5-letter word:")
            val input = readln()
            if (input == "exit") {
                println("\nThe game is over.")
                exitProcess(0)
            }
            if (checkWord(input) != 1 || listContains(input) != 1) continue
            if (secretWord == input.lowercase()) {
                println("\nCorrect!")
                exitProcess(0)
            }
            println(showGoodLetters(secretWord, input))

        }
    }

}

fun main(args: Array<String>) {
    if (args.size == 2) {
        Words(args).checkAll()
    } else {
        println("Error: Wrong number of arguments.")
        exitProcess(0)
    }
    println(Play().game())
}
