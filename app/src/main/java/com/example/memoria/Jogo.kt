package com.example.memoria
import android.widget.ImageView
val TRIES = 5;
class Jogo {
    private var cards = arrayOf(
        R.drawable.azul,
        R.drawable.amarelo,
        R.drawable.porco,
        R.drawable.preto,
        R.drawable.verde,
        R.drawable.vermelho, // Adicione as imagens aqui
        R.drawable.azul,
        R.drawable.amarelo,
        R.drawable.porco,
        R.drawable.preto,
        R.drawable.verde,
        R.drawable.vermelho

    );
    private var tries = TRIES;
    private var points = 0;
    private var currentStats = "new game";

    constructor(){
        this.cards.shuffle();
        this.currentStats = "playing";
    }
    fun play (guesses:MutableList<ImageView>): Boolean{
        val guesses1 = guesses[0].tag as Int
        val guesses2 = guesses[1].tag as Int
        if (this.currentStats == "playing"){
            if(guesses1 == guesses2){
                this.points+=1000;
                if (this.points == 8000){
                    this.currentStats = "win";
                }
                return true
            }else{
                this.tries-=1;
                if(this.tries<=0){
                    this.currentStats = "lose";
                }
                return true
            }
        }
        return false
    }
    fun reboot(){
        this.tries = TRIES;
        this.cards.shuffle();
        this.points = 0;
        this.currentStats = "playing"

    }
    fun getBirds():Array<Int>{
        return this.cards.clone();
    }
    fun getPoints():Int{
        return this.points

    }
    fun getTries():Int{
        return this.tries
    }
    fun getStatus():String{
        return this.currentStats
    }
}
