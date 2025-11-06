package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // 定義常數代表剪刀、石頭、布
    companion object {
        private const val SCISSOR = 0
        private const val STONE = 1
        private const val PAPER = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得元件
        val edName = findViewById<EditText>(R.id.edName)
        val tvText = findViewById<TextView>(R.id.tvText)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnMora = findViewById<Button>(R.id.btnMora)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvWinner = findViewById<TextView>(R.id.tvWinner)
        val tvMyMora = findViewById<TextView>(R.id.tvMyMora)
        val tvTargetMora = findViewById<TextView>(R.id.tvTargetMora)

        btnMora.setOnClickListener {
            val playerName = edName.text.toString().trim()

            // 檢查是否有輸入姓名
            if (playerName.isEmpty()) {
                tvText.text = "請輸入玩家姓名"
                return@setOnClickListener
            }

            // 檢查是否選擇出拳
            if (radioGroup.checkedRadioButtonId == -1) {
                tvText.text = "請選擇你的出拳！"
                return@setOnClickListener
            }

            // 取得玩家出拳
            val myMora = when (radioGroup.checkedRadioButtonId) {
                R.id.btnScissor -> SCISSOR
                R.id.btnStone -> STONE
                else -> PAPER
            }

            // 電腦出拳
            val targetMora = (0..2).random()

            // 顯示基本資訊
            tvName.text = "名字\n$playerName"
            tvMyMora.text = "我方出拳\n${getMoraString(myMora)}"
            tvTargetMora.text = "電腦出拳\n${getMoraString(targetMora)}"

            // 判斷勝負
            val (winner, message) = decideWinner(playerName, myMora, targetMora)
            tvWinner.text = "勝利者\n$winner"
            tvText.text = message
        }
    }

    /** 將 0,1,2 轉為對應的文字 */
    private fun getMoraString(mora: Int): String = when (mora) {
        SCISSOR -> "剪刀"
        STONE -> "石頭"
        else -> "布"
    }

    /** 回傳 (勝利者, 提示訊息) */
    private fun decideWinner(name: String, myMora: Int, targetMora: Int): Pair<String, String> {
        return when {
            myMora == targetMora ->
                "平手" to "平局，請再試一次！"
            (myMora == SCISSOR && targetMora == PAPER) ||
            (myMora == STONE && targetMora == SCISSOR) ||
            (myMora == PAPER && targetMora == STONE) ->
                name to "恭喜你獲勝了！！！"
            else ->
                "電腦" to "可惜，電腦獲勝了！"
        }
    }
}
