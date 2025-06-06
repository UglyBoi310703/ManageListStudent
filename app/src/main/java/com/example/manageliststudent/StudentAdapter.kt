package com.example.manageliststudent

import com.example.manageliststudent.Student
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.manageliststudent.R

class StudentAdapter(
    private val context: Context,
    private val students: List<Student>,
    private val onEditClick: (Int) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_student, parent, false)

        val student = students[position]
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvId = view.findViewById<TextView>(R.id.tvId)
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)

        tvName.text = student.name
        tvId.text = student.id

        btnEdit.setOnClickListener {
            onEditClick(position)
        }

        return view
    }
}