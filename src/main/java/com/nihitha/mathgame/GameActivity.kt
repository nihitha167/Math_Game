package com.nihitha.mathgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var textScore: TextView
    lateinit var textLife:TextView
    lateinit var textTime:TextView

    lateinit var textQuestion:TextView
    lateinit var editTextAnswer:EditText

    lateinit var buttonOk:Button
    lateinit var buttonNext: Button

    var correctAnswer=0
    var userScore=0
    var userLife=3

    lateinit var  timer: CountDownTimer
    private val startTimerInMillis:Long=60000
    var timeLeftInMillis:Long = startTimerInMillis


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        supportActionBar!!.title="Calculate"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textScore = findViewById(R.id.textViewScore)
        textLife = findViewById(R.id.textViewLife)
        textTime = findViewById(R.id.textViewTime)
        textQuestion = findViewById(R.id.textviewQuestion)
        editTextAnswer=findViewById(R.id.editTextAnswer)
        buttonOk=findViewById(R.id.buttonOk)
        buttonNext=findViewById(R.id.buttonNext)

        val operation = intent.getStringExtra("operation")
        when (operation) {
            "addition", "subtraction", "multiplication" -> gameContinue(operation)
            else -> Toast.makeText(applicationContext, "Select one to start the game", Toast.LENGTH_LONG).show()
        }


        buttonOk.setOnClickListener {
            var input = editTextAnswer.text.toString()

            if(input==""){
                Toast.makeText(applicationContext,"please enter your answer or click next button",Toast.LENGTH_LONG).show()
            }
            else{
                pauseTimer()
                val userAnswer = input.toInt()
                if(userAnswer==correctAnswer){
                    userScore=userScore+10
                    textQuestion.text="Congratualations your answer is correct"
                    textScore.text=userScore.toString()

                }
                else{
                    userLife--
                    textQuestion.text="Answer is wrong"
                    textLife.text=userLife.toString()
                }

            }
        }

        buttonNext.setOnClickListener {
            pauseTimer()
            resetTimer()
            editTextAnswer.setText("")
            if(userLife==0){
                Toast.makeText(applicationContext,"GameOver",Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity,ResultActivity::class.java)
                intent.putExtra("score",userScore)
                startActivity(intent)
                finish()
            }
            else{
                gameContinue(operation ?: "")

            }
        }


    }

    fun gameContinue(operation: String) {
        when (operation) {
            "addition" -> generateAdditionQuestion()
            "subtraction" -> generateSubtractionQuestion()
            "multiplication" -> generateMultiplicationQuestion()
        }
        startTimer()
    }

    fun generateAdditionQuestion() {
        val number1 = Random.nextInt(0, 100)
        val number2 = Random.nextInt(0, 100)

        textQuestion.text = "$number1 + $number2"
        correctAnswer = number1 + number2
    }

    fun generateSubtractionQuestion() {
        val number1 = Random.nextInt(0, 100)
        val number2 = Random.nextInt(0, number1) // To ensure the result is positive

        textQuestion.text = "$number1 - $number2"
        correctAnswer = number1 - number2
    }

    fun generateMultiplicationQuestion() {
        val number1 = Random.nextInt(0, 10)
        val number2 = Random.nextInt(0, 10)

        textQuestion.text = "$number1 * $number2"
        correctAnswer = number1 * number2
    }


    fun startTimer(){
        timer=object : CountDownTimer(timeLeftInMillis,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text=userLife.toString()
                textQuestion.text="Sorry your time is up"
            }

        }.start()

    }

    fun updateText(){
        val remainingTime:Int = (timeLeftInMillis/1000).toInt()
        textTime.text= String.format(Locale.getDefault(),"%02d",remainingTime)
    }

    fun pauseTimer(){

        timer.cancel()

    }

    fun resetTimer(){

        timeLeftInMillis=startTimerInMillis
        updateText()

    }
}