package br.edu.ifpb.pdm.memoryprof

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.memoria.R

class GanhaActivity : AppCompatActivity() {

    private lateinit var botao:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        this.botao = findViewById(R.id.botaoWin)
        this.botao.setOnClickListener({ voltar() })
    }

    fun voltar(){
        finish()
    }
}