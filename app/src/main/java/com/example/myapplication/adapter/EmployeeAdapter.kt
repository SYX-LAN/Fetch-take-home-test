package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Employee

class EmployeeAdapter: RecyclerView.Adapter<EmployeeAdapter.EmployeeItemHolder>() {

    private var employeeList = ArrayList<Employee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeItemHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return EmployeeItemHolder(root)
    }

    override fun onBindViewHolder(holder: EmployeeItemHolder, position: Int) {
        holder.bindView(employeeList[position])
    }

    override fun getItemCount() = employeeList.size

    fun setEmployeeList(data: List<Employee>) {
        employeeList.addAll(data)
    }


    inner class EmployeeItemHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bindView(employee: Employee) {
            val name = view.findViewById<TextView>(R.id.employee_name)
            val listId = view.findViewById<TextView>(R.id.list_id)
            val id = view.findViewById<TextView>(R.id.employee_id)
            name.text = employee.name
            listId.text = employee.listId
            id.text = employee.id.toString()
        }
    }

}

