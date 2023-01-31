package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.EmployeeAdapter
import com.example.myapplication.api.ApiFetcher
import com.example.myapplication.constants.Constants.COMPLETE_TAG
import com.example.myapplication.constants.Constants.ERROR_TAG
import com.example.myapplication.constants.Constants.SUCCESS_TAG
import com.example.myapplication.model.Employee
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private lateinit var employeeRecyclerView: RecyclerView

    private lateinit var mAdapter: EmployeeAdapter

    private lateinit var mLayoutManager: LinearLayoutManager

    private var fetchSuccess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val fetchButton = findViewById<Button>(R.id.test)
        fetchButton.setOnClickListener {
            if (!fetchSuccess) {
                getList()
            }
        }
        employeeRecyclerView = findViewById(R.id.employee_list)
        mAdapter = EmployeeAdapter()
        mLayoutManager = LinearLayoutManager(this)
        employeeRecyclerView.adapter = mAdapter
        employeeRecyclerView.layoutManager = mLayoutManager
    }

    private fun getList() {
        ApiFetcher.getEmployeeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Employee>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(employees: List<Employee>) {
                    Log.i(SUCCESS_TAG, "FETCH DATA SUCCESS")
                    val list = employees.filter {
                                    it.name != null && it.name != ""
                                }.sortedWith(Comparator { e1, e2 ->
                                    if (e1.listId == e2.listId) {
                                        e1.name.compareTo(e2.name)
                                    } else {
                                        e1.listId.compareTo(e2.listId)
                                    }
                                })
                    mAdapter.setEmployeeList(list)
                }

                override fun onError(e: Throwable) {
                    Log.i(ERROR_TAG, "Fetch employees error")
                }

                override fun onComplete() {
                    mAdapter.notifyDataSetChanged()
                    Log.i(COMPLETE_TAG, "Fetch data complete")
                    fetchSuccess = true
                }
            })
    }
}