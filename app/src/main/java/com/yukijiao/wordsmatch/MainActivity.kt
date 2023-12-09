package com.yukijiao.wordsmatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.MultiAutoCompleteTextView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var filename = "words_alpha.txt";
    private var lines = mutableListOf<String>();

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStream: InputStream = assets.open(filename)
        lines = readInputStreamLines(inputStream).toMutableList()

        val spinnerWordsList = findViewById<Spinner>(R.id.spinner_words_list);
        spinnerWordsList.onItemSelectedListener = this

        val btnFilter = findViewById<Button>(R.id.btn_filter_word)
        btnFilter.setOnClickListener{
            val txtRegex = findViewById<EditText>(R.id.txt_regexp)
            filterWords(lines, txtRegex.text.toString())
        }
    }

    private fun filterWords(lines:List<String>, reString:String) {
        val output = findViewById<MultiAutoCompleteTextView>(R.id.output)
        output.text.clear()
        var reStr = reString
        if (reString.startsWith("*")) {
            reStr = ".".plus(reString)
        }
        try {
            val regexp = Regex(reStr)
            for (line in lines) {
                if (regexp.matches(line)) {
                    output.text.append(line+"\n")
                }
            }
        } catch (e : Exception){
            Toast.makeText(this, "RexExp compile failed, please check your input!", Toast.LENGTH_SHORT).show()
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val spinnerWordsList = findViewById<Spinner>(R.id.spinner_words_list);
        filename = spinnerWordsList.selectedItem.toString()
        val inputStream: InputStream = assets.open(filename)
        lines = readInputStreamLines(inputStream).toMutableList()    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}