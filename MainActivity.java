package com.example.a112360122;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText edName;
    private TextView tvText, tvName, tvWinner, tvMyMora, tvComMora;
    private RadioButton btnScissor, btnStone, btnPaper;
    private Button btnMora;

    // 常數代表出拳
    private static final int SCISSOR = 0;
    private static final int STONE = 1;
    private static final int PAPER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 取得元件
        edName = findViewById(R.id.edit_name);
        tvText = findViewById(R.id.textView);
        tvName = findViewById(R.id.textView3);
        tvWinner = findViewById(R.id.textView4);
        tvMyMora = findViewById(R.id.textView5);
        tvComMora = findViewById(R.id.textView6);
        btnMora = findViewById(R.id.button);
        btnScissor = findViewById(R.id.btn_scissor);
        btnStone = findViewById(R.id.btn_stone);
        btnPaper = findViewById(R.id.btn_paper);

        // 設定按鈕事件
        btnMora.setOnClickListener(view -> playGame());

        // 系統邊界設定
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /** 主遊戲邏輯 */
    private void playGame() {
        String playerName = edName.getText().toString().trim();

        // 防呆：檢查是否輸入名字
        if (playerName.isEmpty()) {
            tvText.setText("請輸入玩家姓名");
            return;
        }

        // 防呆：檢查是否選擇出拳
        if (!btnScissor.isChecked() && !btnStone.isChecked() && !btnPaper.isChecked()) {
            tvText.setText("請選擇你的出拳！");
            return;
        }

        // 取得玩家出拳
        int myMora = getPlayerMora();
        tvName.setText(String.format("名字\n%s", playerName));
        tvMyMora.setText("我方出拳\n" + getMoraString(myMora));

        // 產生電腦出拳
        int comMora = (int) (Math.random() * 3);
        tvComMora.setText("電腦出拳\n" + getMoraString(comMora));

        // 判斷勝負
        Result result = decideWinner(playerName, myMora, comMora);
        tvWinner.setText("勝利者\n" + result.winner);
        tvText.setText(result.message);
    }

    /** 將按鈕選項轉換成出拳代號 */
    private int getPlayerMora() {
        if (btnScissor.isChecked()) return SCISSOR;
        else if (btnStone.isChecked()) return STONE;
        else return PAPER;
    }

    /** 回傳出拳文字 */
    private String getMoraString(int mora) {
        switch (mora) {
            case SCISSOR:
                return "剪刀";
            case STONE:
                return "石頭";
            default:
                return "布";
        }
    }

    /** 判斷勝負，回傳結果物件 */
    private Result decideWinner(String name, int myMora, int comMora) {
        if (myMora == comMora)
            return new Result("平手", "平局，再試一次！");
        else if ((myMora == SCISSOR && comMora == PAPER) ||
                 (myMora == STONE && comMora == SCISSOR) ||
                 (myMora == PAPER && comMora == STONE))
            return new Result(name, "恭喜你獲勝了！！！");
        else
            return new Result("電腦", "可惜，電腦獲勝了！");
    }

    /** 用於包裝勝負結果 */
    private static class Result {
        String winner;
        String message;

        Result(String winner, String message) {
            this.winner = winner;
            this.message = message;
        }
    }
}
