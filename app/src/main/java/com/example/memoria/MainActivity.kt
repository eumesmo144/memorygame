package com.example.memoria

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender.OnFinished
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.children
import br.edu.ifpb.pdm.memoryprof.GanhaActivity
import br.edu.ifpb.pdm.memoryprof.LoseActivity

class MainActivity : AppCompatActivity() {
    private lateinit var board:GridLayout;
    private lateinit var progress:ProgressBar;
    private lateinit var points:TextView;
    private lateinit var lifes:TextView;
    private lateinit var botao:Button;
    private val cards = mutableListOf<Carta>()
    private var game = Jogo();
    private var flippedCards = mutableListOf<ImageView>();

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.board = findViewById(R.id.tabuleiro)
        this.lifes = findViewById(R.id.tentativas)
        this.progress = findViewById(R.id.barrinha)
        this.points = findViewById(R.id.pontuacao)
        this.botao = findViewById(R.id.botao)

        this.updateLifes()
        this.updatePoints()
        fillBoard()
        object : CountDownTimer(7000, 70) {
            override fun onTick(millisUntilFinished: Long) {
                progress.progress += 1
            }
            override fun onFinish() {
                flipBoard()
                botao.setOnClickListener({ rebootBoard() })
            }
        }.start()
    }
     override fun onResume(){
         super.onResume()
         rebootBoard()
     }
    fun fillBoard(){
        for (bird in this.game.getBirds()){
            val carta = ImageView(this);
            this.board.addView(carta);
            carta.setImageResource(bird);
            carta.layoutParams.height = 240;
            carta.layoutParams.width = 240;
            carta.tag = bird;
        }
    }
    fun flipBoard(){
        for(card in this.board.children) {
            var carta = card as ImageView
            carta.setImageResource(R.drawable.verso)
            carta.setOnClickListener({ flipCard(carta) })
        }
    }
    fun flipCard(carta: ImageView){
        carta.setImageResource(carta.tag as Int)
        carta.setOnClickListener({})
        this.flippedCards.add(carta)
        if(this.flippedCards.size==2){
            if(!this.game.play(this.flippedCards)){
                val cartas = this.flippedCards.toMutableList()
                object: CountDownTimer(1000, 1000){
                    override fun onTick(millisUntilFinished: Long) {
                    }

                    override fun onFinish() {
                        unflipCards(cartas)
                    }
                }.start()
            }
            this.updatePoints()
            this.updateLifes()
            this.flippedCards.clear()
            this.isEndgame()
        }
    }
    fun unflipCards(cartas:MutableList<ImageView>){
        for(card in cartas){
            card.setImageResource(R.drawable.verso)
            card.setOnClickListener({flipCard(card)})
        }
        }
    fun updatePoints(){
        this.points.setText("Pontuação: ${this.game.getPoints()}")
    }
    fun updateLifes(){
        this.lifes.setText("Temtativas: ${this.game.getTries()}")
    }
    fun isEndgame(){
        if(this.game.getStatus() == "win"){
            val intent = Intent(this,GanhaActivity::class.java)
            startActivity(intent)

        }else if (this.game.getStatus() == "lose"){
            val intent = Intent(this, LoseActivity::class.java)
            startActivity(intent)
        }
    }
    fun rebootBoard(){
        this.game.reboot()
        this.updateLifes()
        this.updatePoints()
        this.progress.progress = 0
        this.board.removeAllViews()
        fillBoard()
        this.botao.setOnClickListener({})

        object: CountDownTimer(7000, 70){
            override fun onTick(milliUntilFinished: Long) {
                progress.progress += 1
            }

            override fun onFinish() {
                flipBoard()
                botao.setOnClickListener({rebootBoard()})
            }
        }.start()
    }

}