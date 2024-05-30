package com.example.mbtitest

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private val mbtiInterpret = listOf(
        R.string.INFJ,R.string.INFP,R.string.ENFJ,R.string.ENFP,
        R.string.INTJ,R.string.INTP,R.string.ENTJ,R.string.ENTP,
        R.string.ISTJ,R.string.ISFJ,R.string.ESTJ,R.string.ESFJ,
        R.string.ISTP,R.string.ISFP,R.string.ESTP,R.string.ESFP,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val results = intent.getIntegerArrayListExtra("results") ?: arrayListOf()

        val nightModeFlags = //onCreate때 확인 가능함
            getResources().configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        // 선택된 값에 따른 결과 알파벳 할당
        val resultTypes = listOf(
            listOf("E", "I"),
            listOf("N", "S"),
            listOf("T", "F"),
            listOf("J", "P")
        )

        var resultString = ""
        for (i in results.indices) {
            // 선택된 값(1 또는 2)에 따른 알파벳을 결과 문자열에 추가
            resultString += resultTypes[i][results[i] - 1]
        }

        // 결과 TextView 업데이트
        val tvResValue: TextView = findViewById(R.id.tv_resValue)
        tvResValue.text = resultString

        // 알파벳 결과에 따른 이미지 설정
        val ivResImg: ImageView = findViewById(R.id.iv_resImg)
        val imageResource = resources.getIdentifier("ic_${resultString.toLowerCase(Locale.ROOT)}", "drawable", packageName)
        ivResImg.setImageResource(imageResource)

        val ivResInterpret : TextView = findViewById(R.id.iv_resinterpret)
        var mbtiType :Int = 0
        for (i in 0 until mbtiInterpret.size){
            if (getString(mbtiInterpret[i]).substring(0 until 4) == resultString){mbtiType = i;break;}
        }
        ivResInterpret.text = getString(mbtiInterpret[mbtiType])

        ivResImg.setOnClickListener{
            ivResImg.isVisible = false
            ivResInterpret.isVisible = true
        }
        ivResInterpret.setOnClickListener{
            ivResImg.isVisible = true
            ivResInterpret.isVisible = false
        }

        val btnRetry: Button = findViewById(R.id.btn_res_retry)

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
            tvResValue.setTextColor(Color.YELLOW)
            btnRetry.setTextColor(Color.BLACK)
        }

        btnRetry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}