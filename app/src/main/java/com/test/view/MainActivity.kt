package com.test.view

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.test.R
import com.test.presenter.TaskPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TaskView {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskPresenter = TaskPresenter(this)

        activity_main_launch.setOnClickListener {
            showLoaders()
            taskPresenter.fetchPages()
        }

        activity_main_submit.setOnClickListener {
            if (!TextUtils.isEmpty(activity_main_input_et.text.toString())) taskPresenter.fetchResult(activity_main_input_et.text.toString())
        }
    }

    /*
    * visibility set to VISIBLE till process is done
    * */
    private fun showLoaders() {
        activity_main_answer_loader1.visibility = View.VISIBLE
        activity_main_answer_loader2.visibility = View.VISIBLE
        activity_main_answer_loader3.visibility = View.VISIBLE
    }

    /*
    * Showing result of 1st question.
    * visibility of loader set to GONE
    * */
    override fun passResult1(result: Char) {
        activity_main_answer_loader1.visibility = View.GONE
        activity_main_answer_tv1.text = result.toString()
    }

    /*
    * Showing result of 2st question.
    * visibility of loader set to GONE
    * */
    override fun passResult2(result: String) {
        activity_main_answer_loader2.visibility = View.GONE
        activity_main_answer_tv2.text = result
    }

    /*
    * enabling edittext and button to perform further operations
    * visibility of loader set to GONE
    * */
    override fun passResult3() {
        activity_main_answer_loader3.visibility = View.GONE
        activity_main_submit.isEnabled = true
        activity_main_input_et.isEnabled = true
    }

    /*
    * showing count of occurrences of word entered in activity_main_input_et
    * */
    override fun showQueryResult(int: Int?) {
        activity_main_answer_tv3.text = activity_main_input_et.text.toString() + " : " + int
    }

}