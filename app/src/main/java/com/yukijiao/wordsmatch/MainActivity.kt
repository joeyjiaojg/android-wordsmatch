package com.yukijiao.wordsmatch

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.MultiAutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStream: InputStream = assets.open("words_alpha.txt")
        val lines = readInputStreamLines(inputStream)

        val btnFilter = findViewById<Button>(R.id.btn_filter_word)
        btnFilter.setOnClickListener{
            val txtRegex = findViewById<EditText>(R.id.txt_regexp)
            filterWords(lines, txtRegex.text.toString())
        }
    }

    private fun filterWords(lines:List<String>, reString: String) {
        val output = findViewById<MultiAutoCompleteTextView>(R.id.output)
        output.text.clear()
        val regexp = Regex(reString)
        for (line in lines) {
            if (regexp.matches(line)) {
                output.text.append(line+"\n")
            }
        }
    }

    private fun readInputStreamLines(inputStream: InputStream): List<String> {
        val lines = mutableListOf<String>()
        val reader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            val line = reader.readLine() ?: break
            lines.add(line)
        }
        return lines
    }

}