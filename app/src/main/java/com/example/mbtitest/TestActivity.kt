package com.example.mbtitest

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    val questionnaireResults = QuestionnaireResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this)
        //화면을 터치로 좌우로 움직일 시 새로운 화면 나오는 걸 막음
        viewPager.isUserInputEnabled = false
    }

    fun moveToNextQuestion() {
        Log.d("jblee","viewPager.currentItem = ${viewPager.currentItem}")

        if (viewPager.currentItem==3) {//마지막 페이지일때 결과 페이지로
            Log.d("jblee","result = ${ArrayList(questionnaireResults.results)}")
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("results", ArrayList(questionnaireResults.results))
            startActivity(intent)
        } else {//이전
            val nextItem = viewPager.currentItem + 1
            if (nextItem < viewPager.adapter?.itemCount ?: 0) {
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }
}

class QuestionnaireResults {
    val results = mutableListOf<Int>()

    fun addResponses(responses: List<Int>) {
        val mostFrequent = responses.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequent?.let { results.add(it) }
    }
}