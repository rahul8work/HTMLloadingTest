package com.test.presenter

import com.test.service.TaskService
import com.test.view.TaskView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern


class TaskPresenter(private val taskView: TaskView) {

    private val occurrences = HashMap<String, Int>()
    private var taskService: TaskService? = null

    init {

        if (this.taskService == null) {
            this.taskService = TaskService()
        }
    }

    /*
    * Simultaneous request for all 3 tasks
    * */

    fun fetchPages() {
        fetchPage1()
        fetchPage2()
        fetchPage3()
    }

    /*
    * fetching page for question 1
    * using map to get 11th character from the string
    * */
    private fun fetchPage1() {
        taskService!!
                .api
                .fetchAnswer1()
                .map { result -> result[10] } // 10th character is a whitespace
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { taskView.passResult1(it) }
    }

    /*
    * fetching page for question 2
    * using Schedulers.computation() thread for computation
    * */
    private fun fetchPage2() {
        taskService!!
                .api
                .fetchAnswer2()
                .map { t -> iterateThis(t) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { taskView.passResult2(it.toString()) }
    }

    /*
    * fetching page for question 3
    * using Schedulers.computation() thread for computation
    * */
    private fun fetchPage3() {
        taskService!!
                .api
                .fetchAnswer3()
                .map { t -> getOccurrences(t) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { taskView.passResult3() }
    }

    /*
    * iterate over string to get nth character
    * @param - string
    * */
    private fun iterateThis(string: String): StringBuilder {
        val stringBuilder = StringBuilder()
        for (i in 9..string.length step 10) stringBuilder.append(string[i])
        return stringBuilder
    }

    /*
    * iterate over array and count number of occurrences of that word
    * @param - string
    * */
    private fun getOccurrences(string: String): HashMap<String, kotlin.Int> {
        val regex = "\\t|\\n| "
        val pattern = Pattern.compile(regex, Pattern.MULTILINE)
        val words = string.split(pattern)

        for (word in words.listIterator()) {
            var oldCount: Int? = occurrences[word]
            if (oldCount == null) oldCount = 0
            occurrences[word] = oldCount + 1
        }
        return occurrences

    }

    /*
    * returning number of occurrences for @param -string
    * if @param - string not available then returning 0
    * */
    fun fetchResult(string: String) {
        if (occurrences.containsKey(string))
            taskView.showQueryResult(occurrences[string])
        else
            taskView.showQueryResult(0)
    }
}
